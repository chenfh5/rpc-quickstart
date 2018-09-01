package io.github.chenfh5.rpc

import io.github.chenfh5.rpc.thrift.HelloServiceImpl
import io.github.chenfh5.rpc.thrift.autogen.Hello

object ThriftConstant {
  // common processor for different service model
  val processor = new Hello.Processor(new HelloServiceImpl())

}
