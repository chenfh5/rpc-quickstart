package io.github.chenfh5.rpc.t5_non_blocking_half

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.OwnUtils
import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.{TFramedTransport, TSocket}

class T5Client {

  def process(str: String): Response = {
    val transport = new TFramedTransport(new TSocket(OwnConfig.SERVER_HOST, OwnConfig.SERVER_PORT_5, OwnConfig.TIMEOUT_MILLS))
    transport.open()

    val protocol = new TCompactProtocol(transport)
    val client = new Hello.Client(protocol)

    val strRandom = scala.util.Random.alphanumeric.filter(_.isLetter).take(10).mkString
    val data = s"hello world chenfh5 strRaw=$str strRandom=$strRandom, now=${OwnUtils.getTimeNow()}"
    val resp = client.sendMessage(new Message(scala.util.Random.nextInt(10), data))
    println(s"this is the t5_non_blocking_half resp code=${resp.code}, resp msg=${resp.message}")
    resp
  }

}

object T5Client {
  def apply(str: String): Response = new T5Client().process(str)
}
