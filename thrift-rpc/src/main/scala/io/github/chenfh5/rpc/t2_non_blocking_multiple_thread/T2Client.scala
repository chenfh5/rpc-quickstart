package io.github.chenfh5.rpc.t2_non_blocking_multiple_thread

import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import io.github.chenfh5.{Configuration, OwnUtils}
import org.apache.thrift.async.{AsyncMethodCallback, TAsyncClientManager}
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.{TFramedTransport, TNonblockingSocket, TSocket}

import scala.io.StdIn

class T2Client {

  def process(str: String): Response = {
    if (scala.util.Random.nextInt(10) % 2 == 0) sync(str) else async(str)
  }

  def sync(str: String): Response = {
    val transport = new TFramedTransport(new TSocket(Configuration.SERVER_HOST, Configuration.SERVER_PORT_2, Configuration.TIMEOUT_MILLS))
    transport.open()

    val protocol = new TCompactProtocol(transport)
    val client = new Hello.Client(protocol)

    val strRandom = scala.util.Random.alphanumeric.filter(_.isLetter).take(10).mkString
    val data = s"hello world chenfh5 strRaw=$str strRandom=$strRandom, now=${OwnUtils.getTimeNow()}"
    val resp = client.sendMessage(new Message(scala.util.Random.nextInt(10), data)) // client send message to the server, and the server receive it(message from client) and return response after deal with the it
    println(s"this is the t2_non_blocking_multiple_thread_sync resp code=${resp.code}, resp msg=${resp.message}")
    resp
  }

  def async(str: String): Response = {
    val asyncClientManager = new TAsyncClientManager()
    val transport = new TNonblockingSocket(Configuration.SERVER_HOST, Configuration.SERVER_PORT_2, Configuration.TIMEOUT_MILLS)

    val protocolFactory = new TCompactProtocol.Factory()
    val client = new Hello.AsyncClient(protocolFactory, asyncClientManager, transport)

    val data = s"hello world chenfh5 ${OwnUtils.getTimeNow()}"
    var resp: Response = null
    client.sendMessage(new Message(scala.util.Random.nextInt(10), data), new AsyncMethodCallback[Response] {
      override def onComplete(response: Response): Unit = {
        println(s"this is the blocking_threadpool_sync async res=$response")
        resp = response
      }

      override def onError(exception: Exception): Unit = {
        println(s"this is the t2_non_blocking_multiple_thread_async async error=$exception")
      }
    })
    println("start sleep here to wait async")
    StdIn.readLine()
    Thread.sleep(2000)
    println("end sleep here")
    resp
  }

}

object T2Client {
  def apply(str: String): Response = new T2Client().process(str)
}
