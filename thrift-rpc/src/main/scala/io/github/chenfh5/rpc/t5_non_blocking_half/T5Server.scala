package io.github.chenfh5.rpc.t5_non_blocking_half

import io.github.chenfh5.Configuration
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.server.THsHaServer
import org.apache.thrift.transport.{TFramedTransport, TNonblockingServerSocket}

class T5Server extends Server {

  override def init(): Unit = {
    // 半同步半异步服务模型
    val socket = new TNonblockingServerSocket(Configuration.SERVER_PORT_5, Configuration.TIMEOUT_MILLS)

    val tArgs = new THsHaServer.Args(socket)
    tArgs.processor(ThriftConstant.processor)
    tArgs.transportFactory(new TFramedTransport.Factory())
    tArgs.protocolFactory(new TCompactProtocol.Factory())
    server = new THsHaServer(tArgs)
  }

  override def start(): Unit = {
    println("this is the 半同步半异步服务模型 starting...")
    super.start()
  }

  override def stop(): Unit = {
    println("this is the 半同步半异步服务模型 shutdown")
    super.stop()
  }

}

object T5Server {
  def apply(): T5Server = new T5Server()
}
