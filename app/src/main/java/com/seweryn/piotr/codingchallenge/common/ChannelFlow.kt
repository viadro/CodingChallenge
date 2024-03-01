package com.seweryn.piotr.codingchallenge.common

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow

class ChannelFlow<T> : Flow<T> {

  private val channel = Channel<T>()
  private val channelAsFlow = channel.receiveAsFlow()

  override suspend fun collect(collector: FlowCollector<T>) {
    channelAsFlow.collect(collector)
  }

  suspend fun emit(value: T) {
    channel.send(value)
  }

}