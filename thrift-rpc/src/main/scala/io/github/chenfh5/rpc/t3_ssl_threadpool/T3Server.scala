package io.github.chenfh5.rpc.t3_ssl_threadpool

import io.github.chenfh5.Configuration
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.server.TThreadPoolServer
import org.apache.thrift.transport.TSSLTransportFactory
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters

class T3Server extends Server {

  override def init(): Unit = {
    // 通讯层采用SSL安全认证
    val tSSLTransportParameters = new TSSLTransportParameters()
    tSSLTransportParameters.setKeyStore("ssl/thrift_ssl_test.keystore", "thrift")
    val socket = TSSLTransportFactory.getServerSocket(Configuration.SERVER_PORT_3, Configuration.TIMEOUT_MILLS, null, tSSLTransportParameters)

    val tArgs = new TThreadPoolServer.Args(socket) // 线程池参数设置
    tArgs.processor(ThriftConstant.processor) // protocol and transport are using default
    server = new TThreadPoolServer(tArgs)
  }


  override def start(): Unit = {
    println("this is the 通讯层采用SSL安全认证 starting...")
    super.start()
  }

  override def stop(): Unit = {
    println("this is the 通讯层采用SSL安全认证 shutdown")
    super.stop()
  }

}

object T3Server {
  def apply(): T3Server = new T3Server()
}
