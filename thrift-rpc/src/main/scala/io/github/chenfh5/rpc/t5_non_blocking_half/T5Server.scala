package io.github.chenfh5.rpc.t5_non_blocking_half

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.server.THsHaServer
import org.apache.thrift.transport.{TFramedTransport, TNonblockingServerSocket}

class T5Server extends Server {

  override val serverType: String = "半同步半异步服务模型"

  override def init(): Unit = {
    val socket = new TNonblockingServerSocket(OwnConfig.SERVER_PORT_5, OwnConfig.TIMEOUT_MILLS)

    val tArgs = new THsHaServer.Args(socket)
    tArgs.processor(ThriftConstant.processor)
    tArgs.transportFactory(new TFramedTransport.Factory())
    tArgs.protocolFactory(new TCompactProtocol.Factory())
    server = new THsHaServer(tArgs)
  }

}

object T5Server {
  def apply(): T5Server = new T5Server()
}
