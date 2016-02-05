// See also comments to KT-10390

fun foo(arg: Any?) {
    arg?.hashCode()
}
class Sample<T>(val x: T, val f: (T) -> Unit)
val ok:   Sample<Int?> = Sample(null) { y -> } 

// y should be Int? and not Nothing? here
val fail: Sample<Int?> = Sample(null) { y -> foo(y) }