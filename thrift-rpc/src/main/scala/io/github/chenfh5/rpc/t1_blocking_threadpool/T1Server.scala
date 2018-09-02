package io.github.chenfh5.rpc.t1_blocking_threadpool

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.server.TThreadPoolServer
import org.apache.thrift.transport.TServerSocket

class T1Server extends Server {

  override val serverType: String = "堵塞式线程池服务模型"

  override def init(): Unit = {
    // 堵塞式线程池服务模型
    val socket = new TServerSocket(OwnConfig.SERVER_PORT_1, OwnConfig.TIMEOUT_MILLS) // 堵塞式

    val tArgs = new TThreadPoolServer.Args(socket) // 线程池参数设置
    tArgs.processor(ThriftConstant.processor) // protocol and transport are using default
    server = new TThreadPoolServer(tArgs)
  }

}

object T1Server {
  def apply(): T1Server = new T1Server()
}
