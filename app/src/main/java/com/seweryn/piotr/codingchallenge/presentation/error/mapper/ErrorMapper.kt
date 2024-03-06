package com.seweryn.piotr.codingchallenge.presentation.error.mapper

import com.seweryn.piotr.codingchallenge.R
import com.seweryn.piotr.codingchallenge.common.Mapper
import com.seweryn.piotr.codingchallenge.common.StringProvider
import com.seweryn.piotr.codingchallenge.network.exception.NetworkConnectionException
import com.seweryn.piotr.codingchallenge.presentation.error.model.ErrorData
import javax.inject.Inject

class ErrorMapper @Inject constructor(
  private val stringProvider: StringProvider,
) :
  Mapper<Throwable, ErrorData> {

  override fun invoke(error: Throwable): ErrorData =
    ErrorData(
      message = stringProvider.getString(
        when (error) {
          is NetworkConnectionException -> R.string.error_message_no_connection
          else -> R.string.error_message_generic
        }
      )
    )

}