object O {
    val f = {}
}

fun box(): String {
    val javaClass = O.f.javaClass

    val enclosingMethod = javaClass.getEnclosingMethod()
    if (enclosingMethod != null) return "method: $enclosingMethod"

    val enclosingConstructor = javaClass.getEnclosingConstructor()
    if (enclosingConstructor == null) return "no enclosing constructor"

    val enclosingClass = javaClass.getEnclosingClass()
    if (enclosingClass?.getName() != "O") return "enclosing class: $enclosingClass"

    val declaringClass = javaClass.getDeclaringClass()
    if (declaringClass != null) return "anonymous function has a declaring class: $declaringClass"

    return "OK"
}
