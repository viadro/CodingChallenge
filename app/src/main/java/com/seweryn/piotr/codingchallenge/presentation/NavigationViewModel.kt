package com.seweryn.piotr.codingchallenge.presentation

import com.seweryn.piotr.codingchallenge.common.ChannelFlow

interface NavigationViewModel<ACTION> {
  val navAction: ChannelFlow<ACTION>

  suspend fun ACTION.emit() =
    navAction.emit(this)
}