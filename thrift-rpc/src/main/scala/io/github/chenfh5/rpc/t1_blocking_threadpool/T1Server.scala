package io.github.chenfh5.rpc.t1_blocking_threadpool

import io.github.chenfh5.Configuration
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.server.TThreadPoolServer
import org.apache.thrift.transport.TServerSocket

class T1Server extends Server {

  override def init(): Unit = {
    // 堵塞式线程池服务模型
    val socket = new TServerSocket(Configuration.SERVER_PORT_1, Configuration.TIMEOUT_MILLS) // 堵塞式

    val tArgs = new TThreadPoolServer.Args(socket) // 线程池参数设置
    tArgs.processor(ThriftConstant.processor) // protocol and transport are using default
    server = new TThreadPoolServer(tArgs)
  }

  override def start(): Unit = {
    println("this is the 堵塞式线程池服务模型 starting...")
    super.start()
  }

  override def stop(): Unit = {
    println("this is the 堵塞式线程池服务模型 shutdown")
    super.stop()
  }

}

object T1Server {
  def apply(): T1Server = new T1Server()
}
