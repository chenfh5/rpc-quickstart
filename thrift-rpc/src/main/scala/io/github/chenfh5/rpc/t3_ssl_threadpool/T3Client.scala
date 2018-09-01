package io.github.chenfh5.rpc.t3_ssl_threadpool

import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import io.github.chenfh5.{Configuration, OwnUtils}
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.transport.TSSLTransportFactory
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters

class T3Client {

  def process(str: String): Response = {
    val tSSLTransportParameters = new TSSLTransportParameters()
    tSSLTransportParameters.setTrustStore("ssl/thrift_ssl_test.truststore", "thrift2")

    val transport = TSSLTransportFactory.getClientSocket(Configuration.SERVER_HOST, Configuration.SERVER_PORT_3, Configuration.TIMEOUT_MILLS, tSSLTransportParameters)
    val protocol = new TBinaryProtocol(transport)
    val client = new Hello.Client(protocol)

    val strRandom = scala.util.Random.alphanumeric.filter(_.isLetter).take(10).mkString
    val data = s"hello world chenfh5 strRaw=$str strRandom=$strRandom, now=${OwnUtils.getTimeNow()}"
    val resp = client.sendMessage(new Message(scala.util.Random.nextInt(10), data))
    println(s"this is the t3_ssl_threadpool resp code=${resp.code}, resp msg=${resp.message}")
    resp
  }

}

object T3Client {
  def apply(str: String): Response = new T3Client().process(str)
}
