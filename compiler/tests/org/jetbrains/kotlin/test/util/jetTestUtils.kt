/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.test.util

import com.intellij.openapi.util.io.FileUtil
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.intellij.util.SmartFMap
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid
import java.io.File

fun String.trimTrailingWhitespacesAndAddNewlineAtEOF(): String =
        this.split('\n').map { it.trimEnd() }.joinToString(separator = "\n").let {
            result -> if (result.endsWith("\n")) result else result + "\n"
        }

fun CodeInsightTestFixture.configureWithExtraFileAbs(path: String, vararg extraNameParts: String) {
    configureWithExtraFile(path, *extraNameParts, relativePaths = false)
}

fun CodeInsightTestFixture.configureWithExtraFile(path: String, vararg extraNameParts: String = arrayOf(".Data"), relativePaths: Boolean = false) {
    fun String.toFile(): File = if (relativePaths) File(testDataPath, this) else File(this)

    val noExtensionPath = FileUtil.getNameWithoutExtension(path)
    val extensions = arrayOf("kt", "java")
    val extraPaths: List<String> = extraNameParts
            .flatMap { extensions.map { ext -> "$noExtensionPath$it.$ext" } }
            .filter { it.toFile().exists() }

    configureByFiles(*(listOf(path) + extraPaths).toTypedArray())
}

fun PsiFile.findElementByCommentPrefix(commentText: String): PsiElement? =
        findElementsByCommentPrefix(commentText).keys.singleOrNull()

fun PsiFile.findElementsByCommentPrefix(prefix: String): Map<PsiElement, String> {
    var result = SmartFMap.emptyMap<PsiElement, String>()
    accept(
            object : KtTreeVisitorVoid() {
                override fun visitComment(comment: PsiComment) {
                    val commentText = comment.text
                    if (commentText.startsWith(prefix)) {
                        val parent = comment.parent
                        val elementToAdd = when (parent) {
                            is KtDeclaration -> parent
                            is PsiMember -> parent
                            else -> PsiTreeUtil.skipSiblingsForward(
                                    comment,
                                    PsiWhiteSpace::class.java, PsiComment::class.java, KtPackageDirective::class.java
                            )
                        } as? PsiElement ?: return

                        result = result.plus(elementToAdd, commentText.substring(prefix.length).trim())
                    }
                }
            }
    )
    return result
}