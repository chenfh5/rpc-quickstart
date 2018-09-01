package io.github.chenfh5.rpc.t1_blocking_threadpool

import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import io.github.chenfh5.{Configuration, OwnUtils}
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.transport.TSocket

class T1Client {

  def process(str: String): Response = {
    val transport = new TSocket(Configuration.SERVER_HOST, Configuration.SERVER_PORT_1, Configuration.TIMEOUT_MILLS)
    transport.open()

    val protocol = new TBinaryProtocol(transport) // default is binary protocol
    val client = new Hello.Client(protocol)

    val strRandom = scala.util.Random.alphanumeric.filter(_.isLetter).take(10).mkString
    val data = s"hello world chenfh5 strRaw=$str strRandom=$strRandom, now=${OwnUtils.getTimeNow()}"
    val resp = client.sendMessage(new Message(scala.util.Random.nextInt(10), data)) // client send message to the server, and the server receive it(message from client) and return response after deal with the it
    println(s"this is the t1_blocking_threadpool resp code=${resp.code}, resp msg=${resp.message}")
    resp
  }

}

object T1Client {
  def apply(str: String): Response = new T1Client().process(str)
}
