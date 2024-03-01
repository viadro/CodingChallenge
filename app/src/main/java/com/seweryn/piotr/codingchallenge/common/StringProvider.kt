package com.seweryn.piotr.codingchallenge.common

import android.content.Context
import androidx.annotation.StringRes

interface StringProvider {
  fun getString(@StringRes resId: Int): String
}

class StringProviderImpl(
  private val context: Context,
) : StringProvider {
  override fun getString(resId: Int): String =
    context.getString(resId)

}