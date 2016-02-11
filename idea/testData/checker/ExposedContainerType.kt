class A {
    internal companion object {
        class B {
            class C
            interface D
            companion object {}
        }
    }
}

fun <error descr="[EXPOSED_RECEIVER_TYPE] Member effective visibility 'public' should be the same or less permissive than its receiver type containing declaration Companion effective visibility 'internal'">A.Companion.B.C</error>.foo() {}

interface E : <error descr="[EXPOSED_SUPER_INTERFACE] Sub-interface effective visibility 'public' should be the same or less permissive than its supertype containing declaration Companion effective visibility 'internal'">A.Companion.B.D</error>

val <error descr="[EXPOSED_PROPERTY_TYPE] Property effective visibility 'public' should be the same or less permissive than its type containing declaration Companion effective visibility 'internal'">x</error> = A.Companion.B

class F<<error descr="[EXPOSED_TYPE_PARAMETER_BOUND] Generic effective visibility 'public' should be the same or less permissive than its parameter bound type containing declaration Companion effective visibility 'internal'">T : A.Companion.B</error>>(val x: T)



