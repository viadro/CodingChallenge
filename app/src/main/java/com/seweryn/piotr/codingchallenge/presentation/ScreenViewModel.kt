package com.seweryn.piotr.codingchallenge.presentation

import kotlinx.coroutines.flow.StateFlow

interface ScreenViewModel<DATA> {
  val state: StateFlow<DATA>
}