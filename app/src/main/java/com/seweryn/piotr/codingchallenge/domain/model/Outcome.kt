package com.seweryn.piotr.codingchallenge.domain.model

sealed interface Outcome<VALUE> {
  data class Success<RESULT>(val value: RESULT) : Outcome<RESULT>
  data class Failure<RESULT>(val error: Throwable, val value: RESULT? = null) : Outcome<RESULT>

  suspend fun <RESULT> suspendExecute(
    onSuccess: suspend (VALUE) -> RESULT,
    onFailure: suspend (Throwable, VALUE?) -> RESULT,
  ): RESULT = when (this) {
    is Failure -> onFailure(error, value)
    is Success -> onSuccess(value)
  }

  fun <RESULT> execute(
    onSuccess: (VALUE) -> RESULT,
    onFailure: (Throwable, VALUE?) -> RESULT,
  ): RESULT = when (this) {
    is Failure -> onFailure(error, value)
    is Success -> onSuccess(value)
  }
}