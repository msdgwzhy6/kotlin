package foo

// NOTE THIS FILE IS AUTO-GENERATED by the generateTestDataForReservedWords.kt. DO NOT EDIT!

class TestClass {
    fun foo(const: String) {
    assertEquals("123", const)
    testRenamed("const", { const })
}

    fun test() {
        foo("123")
    }
}

fun box(): String {
    TestClass().test()

    return "OK"
}