package io.github.chenfh5.rpc.t4_multiple_processor_threadpool

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.OwnUtils
import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import org.apache.thrift.protocol.{TBinaryProtocol, TMultiplexedProtocol}
import org.apache.thrift.transport.TSocket

class T4Client {

  def process(str: String): Response = {
    val transport = new TSocket(OwnConfig.SERVER_HOST, OwnConfig.SERVER_PORT_4, OwnConfig.TIMEOUT_MILLS)
    transport.open()

    val multiplexedProtocol = new TMultiplexedProtocol(new TBinaryProtocol(transport), "helloService")
    val client = new Hello.Client(multiplexedProtocol)

    val strRandom = scala.util.Random.alphanumeric.filter(_.isLetter).take(10).mkString
    val data = s"hello world chenfh5 strRaw=$str strRandom=$strRandom, now=${OwnUtils.getTimeNow()}"
    val resp = client.sendMessage(new Message(scala.util.Random.nextInt(10), data))
    println(s"this is the t4_multiple_processor_threadpool resp code=${resp.code}, resp msg=${resp.message}")
    resp
  }

}

object T4Client {
  def apply(str: String): Response = new T4Client().process(str)
}
