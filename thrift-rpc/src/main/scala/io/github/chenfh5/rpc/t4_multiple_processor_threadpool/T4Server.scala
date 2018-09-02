package io.github.chenfh5.rpc.t4_multiple_processor_threadpool

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.TMultiplexedProcessor
import org.apache.thrift.server.TThreadPoolServer
import org.apache.thrift.transport.TServerSocket

class T4Server extends Server {

  override val serverType: String = "多处理器服务模型模型"

  override def init(): Unit = {
    val socket = new TServerSocket(OwnConfig.SERVER_PORT_4, OwnConfig.TIMEOUT_MILLS)

    val tArgs = new TThreadPoolServer.Args(socket)
    val processor = new TMultiplexedProcessor()
    processor.registerProcessor("helloService", ThriftConstant.processor)
    tArgs.processor(processor)
    server = new TThreadPoolServer(tArgs)
  }

}

object T4Server {
  def apply(): T4Server = new T4Server()
}
