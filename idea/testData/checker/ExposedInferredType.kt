private class A

open class B<T>(val x: T)

class C(<error descr="[EXPOSED_PARAMETER_TYPE] Function effective visibility 'public' should be the same or less permissive than its parameter type A effective visibility 'private'">x: A</error>): <error descr="[EXPOSED_SUPER_CLASS] Subclass effective visibility 'public' should be the same or less permissive than its supertype argument A effective visibility 'private'">B<A>(x)</error>

private class D {
    class E
}

fun <error descr="[EXPOSED_FUNCTION_RETURN_TYPE] Function effective visibility 'public' should be the same or less permissive than its return type A effective visibility 'private'">create</error>() = A()

fun <error descr="[EXPOSED_FUNCTION_RETURN_TYPE] Function effective visibility 'public' should be the same or less permissive than its return type argument A effective visibility 'private'">create</error>(<error descr="[EXPOSED_PARAMETER_TYPE] Function effective visibility 'public' should be the same or less permissive than its parameter type A effective visibility 'private'">a: A</error>) = B(a)

val <error descr="[EXPOSED_PROPERTY_TYPE] Property effective visibility 'public' should be the same or less permissive than its type A effective visibility 'private'">x</error> = create()

val <error descr="[EXPOSED_PROPERTY_TYPE] Property effective visibility 'public' should be the same or less permissive than its type argument A effective visibility 'private'">y</error> = create(x)

val <error descr="[EXPOSED_PROPERTY_TYPE] Property effective visibility 'public' should be the same or less permissive than its type argument containing declaration D effective visibility 'private'">z</error>: B<D.E>? = null
