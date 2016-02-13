// See also comments to KT-10390

fun foo(arg: Any?) {
    arg?.hashCode()
}

fun bar(arg: Any?): Any? = arg?.hashCode()

class Consume<T>(val x: T, val f: (T) -> Unit)

class Transform<T>(val x: T, val f: (T) -> T)

class Produce<T>(val x: T, val f: () -> T)

val c: Consume<Int?> = Consume(null) { y -> foo(y) }

val cc = Consume(null) { y -> foo(<!DEBUG_INFO_CONSTANT!>y<!>) }

val t: Transform<Int?> = <!TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS!>Transform<!>(null) { y -> bar(<!DEBUG_INFO_CONSTANT!>y<!>) }

val tt = <!TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS!>Transform<!>(null) { y -> bar(<!DEBUG_INFO_CONSTANT!>y<!>) }

val p: Produce<Int?> = Produce(null) { 42 }

val pp = Produce(null) { 42 }