package io.github.chenfh5.rpc.t4_multiple_processor_threadpool

import io.github.chenfh5.Configuration
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.TMultiplexedProcessor
import org.apache.thrift.server.TThreadPoolServer
import org.apache.thrift.transport.TServerSocket

class T4Server extends Server {

  override def init(): Unit = {
    // 多处理器服务模型
    val socket = new TServerSocket(Configuration.SERVER_PORT_4, Configuration.TIMEOUT_MILLS)

    val tArgs = new TThreadPoolServer.Args(socket)
    val processor = new TMultiplexedProcessor()
    processor.registerProcessor("helloService", ThriftConstant.processor)
    tArgs.processor(processor)
    server = new TThreadPoolServer(tArgs)
  }

  override def start(): Unit = {
    println("this is the 多处理器服务模型 starting...")
    super.start()
  }

  override def stop(): Unit = {
    println("this is the 多处理器服务模型 shutdown")
    super.stop()
  }

}

object T4Server {
  def apply(): T4Server = new T4Server()

}
