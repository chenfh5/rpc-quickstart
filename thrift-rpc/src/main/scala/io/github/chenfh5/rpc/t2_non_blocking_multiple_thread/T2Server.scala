package io.github.chenfh5.rpc.t2_non_blocking_multiple_thread

import io.github.chenfh5.Configuration
import io.github.chenfh5.rpc.{Server, ThriftConstant}
import org.apache.thrift.TProcessorFactory
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.server.TThreadedSelectorServer
import org.apache.thrift.transport.{TFramedTransport, TNonblockingServerSocket}

class T2Server extends Server {

  override val serverType: String = "非堵塞式多线程服务模型"

  override def init(): Unit = {
    val socket = new TNonblockingServerSocket(Configuration.SERVER_PORT_2, Configuration.TIMEOUT_MILLS) // 非堵塞式

    val tArgs = new TThreadedSelectorServer.Args(socket) // 多线程参数设置
    tArgs.protocolFactory(new TCompactProtocol.Factory()) // 传输协议（二进制，压缩，json，debug,. etc），定义了消息是怎样序列化的
    tArgs.transportFactory(new TFramedTransport.Factory()) // 传输方式（阻塞，非阻塞，缓存型，http,. etc），定义了消息是怎样在客户端和服务器端之间通信的
    tArgs.processorFactory(new TProcessorFactory(ThriftConstant.processor)) // 处理器（具体实现类）
    server = new TThreadedSelectorServer(tArgs) // 服务模型（单线程阻塞，非阻塞，半同步，多线程，线程池） server 用于从 transport 接收序列化的消息，根据 protocol 反序列化之，调用用户定义的消息处理器（processor），并序列化消息处理器的响应，然后再将它们写回 transport
  }

}

object T2Server {
  def apply(): T2Server = new T2Server()
}
