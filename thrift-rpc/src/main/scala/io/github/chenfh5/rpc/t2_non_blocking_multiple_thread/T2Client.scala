package io.github.chenfh5.rpc.t2_non_blocking_multiple_thread

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.OwnUtils
import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import org.apache.thrift.async.{AsyncMethodCallback, TAsyncClientManager}
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.{TFramedTransport, TNonblockingSocket, TSocket, TTransport}

class T2Client {

  def process(str: String): Response = {
    async(str)
    //    if (scala.util.Random.nextInt(10) % 2 == 0) sync(str) else async(str)
  }

  def sync(str: String): Response = {
    val transport = new TFramedTransport(new TSocket(OwnConfig.SERVER_HOST, OwnConfig.SERVER_PORT_2, OwnConfig.TIMEOUT_MILLS))
    transport.open()

    val protocol = new TCompactProtocol(transport)
    val client = new Hello.Client(protocol)

    val strRandom = scala.util.Random.alphanumeric.filter(_.isLetter).take(10).mkString
    val data = s"hello world chenfh5 strRaw=$str strRandom=$strRandom, now=${OwnUtils.getTimeNow()}"
    val resp = client.sendMessage(new Message(scala.util.Random.nextInt(10), data)) // client send message to the server, and the server receive it(message from client) and return response after deal with the it
    println(s"this is the t2_non_blocking_multiple_thread_sync resp code=${resp.code}, resp msg=${resp.message}")
    cleanup(transport)
    resp
  }

  def async(str: String): Response = {
    val asyncClientManager = new TAsyncClientManager()
    val transport = new TNonblockingSocket(OwnConfig.SERVER_HOST, OwnConfig.SERVER_PORT_2, OwnConfig.TIMEOUT_MILLS)

    val protocolFactory = new TCompactProtocol.Factory()
    val client = new Hello.AsyncClient(protocolFactory, asyncClientManager, transport)

    val data = s"hello world chenfh5 ${OwnUtils.getTimeNow()}"
    var resp: Response = null
    client.sendMessage(new Message(scala.util.Random.nextInt(10), data), new AsyncMethodCallback[Response] {
      override def onComplete(response: Response): Unit = {
        println(s"this is the t2_non_blocking_multiple_thread_async res=$response")
        resp = response
        cleanup(transport)
        asyncClientManager.stop()
      }

      override def onError(exception: Exception): Unit = {
        println(s"this is the t2_non_blocking_multiple_thread_async error=$exception")
        cleanup(transport)
        asyncClientManager.stop()
      }
    })
    println("start sleep here to wait async")
    //    StdIn.readLine() // comment here in case of UT
    Thread.sleep(2000)
    println("end sleep here")
    resp
  }

  def cleanup(transport: TTransport): Unit = transport.close()

}

object T2Client {
  def apply(str: String): Response = new T2Client().process(str)
}
