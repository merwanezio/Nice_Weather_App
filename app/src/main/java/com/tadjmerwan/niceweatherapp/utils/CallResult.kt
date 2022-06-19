package com.tadjmerwan.niceweatherapp.utils


inline fun <reified X> CallResult<*, *>.getAs() = when (this) {
    is CallResult.Success -> value as? X
    is CallResult.Failure -> error as? X
}

fun <V : Any?> CallResult<V, *>.success(f: (V) -> Unit) = fold(f, {})

fun <E : Exception> CallResult<*, E>.failure(f: (E) -> Unit) = fold({}, f)

infix fun <V : Any?, E : Exception> CallResult<V, E>.or(fallback: V) = when (this) {
    is CallResult.Success -> this
    else -> CallResult.Success(fallback)
}

inline infix fun <V : Any?, E : Exception> CallResult<V, E>.getOrElse(fallback: (E) -> V): V {
    return when (this) {
        is CallResult.Success -> value
        is CallResult.Failure -> fallback(error)
    }
}

fun <V : Any?, E : Exception> CallResult<V, E>.getOrNull(): V? {
    return when (this) {
        is CallResult.Success -> value
        is CallResult.Failure -> null
    }
}

inline fun <V : Any?, U : Any?, E : Exception> CallResult<V, E>.map(transform: (V) -> U): CallResult<U, E> =
    try {
        when (this) {
            is CallResult.Success -> CallResult.Success(transform(value))
            is CallResult.Failure -> CallResult.Failure(error)
        }
    } catch (ex: Exception) {
        CallResult.error(ex as E)
    }

inline fun <V : Any?, U : Any?, E : Exception> CallResult<V, E>.flatMap(transform: (V) -> CallResult<U, E>): CallResult<U, E> =
    try {
        when (this) {
            is CallResult.Success -> transform(value)
            is CallResult.Failure -> CallResult.Failure(error)
        }
    } catch (ex: Exception) {
        CallResult.error(ex as E)
    }

fun <V : Any?, E : Exception, E2 : Exception> CallResult<V, E>.mapError(transform: (E) -> E2) =
    when (this) {
        is CallResult.Success -> CallResult.Success(value)
        is CallResult.Failure -> CallResult.Failure(transform(error))
    }

fun <V : Any?, E : Exception, E2 : Exception> CallResult<V, E>.flatMapError(transform: (E) -> CallResult<V, E2>) =
    when (this) {
        is CallResult.Success -> CallResult.Success(value)
        is CallResult.Failure -> transform(error)
    }

inline fun <V : Any?, E : Exception> CallResult<V, E>.onError(f: (E) -> Unit) = when (this) {
    is CallResult.Success -> CallResult.Success(value)
    is CallResult.Failure -> {
        f(error)
        this
    }
}

fun <V : Any?, E : Exception> CallResult<V, E>.any(predicate: (V) -> Boolean): Boolean = try {
    when (this) {
        is CallResult.Success -> predicate(value)
        is CallResult.Failure -> false
    }
} catch (ex: Exception) {
    false
}

fun <V : Any?, U : Any?> CallResult<V, *>.fanout(other: () -> CallResult<U, *>): CallResult<Pair<V, U>, *> =
    flatMap { outer -> other().map { outer to it } }

fun <V : Any?, E : Exception> List<CallResult<V, E>>.lift(): CallResult<List<V>, E> =
    fold(CallResult.success(mutableListOf<V>()) as CallResult<MutableList<V>, E>) { acc, result ->
        acc.flatMap { combine ->
            result.map { combine.apply { add(it) } }
        }
    }

sealed class CallResult<out V : Any?, out E : Exception> {

    open operator fun component1(): V? = null
    open operator fun component2(): E? = null

    inline fun <X> fold(success: (V) -> X, failure: (E) -> X): X = when (this) {
        is Success -> success(this.value)
        is Failure -> failure(this.error)
    }

    abstract fun get(): V

    class Success<out V : Any?>(val value: V) : CallResult<V, Nothing>() {
        override fun component1(): V? = value

        override fun get(): V = value

        override fun toString() = "[Success: $value]"

        override fun hashCode(): Int = value.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Success<*> && value == other.value
        }
    }

    class Failure<out E : Exception>(val error: E) : CallResult<Nothing, E>() {
        override fun component2(): E? = error

        override fun get() = throw error

        fun getException(): E = error

        override fun toString() = "[Failure: $error]"

        override fun hashCode(): Int = error.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Failure<*> && error == other.error
        }
    }

    companion object {
        // Factory methods
        fun <E : Exception> error(ex: E) = Failure(ex)

        fun <V : Any?> success(v: V) = Success(v)

        fun <V : Any?> of(
            value: V?,
            fail: (() -> Exception) = { Exception() }
        ): CallResult<V, Exception> =
            value?.let { success(it) } ?: error(fail())

        fun <V : Any?, E : Exception> of(f: () -> V): CallResult<V, E> = try {
            success(f())
        } catch (ex: Exception) {
            error(ex as E)
        }
    }

}

