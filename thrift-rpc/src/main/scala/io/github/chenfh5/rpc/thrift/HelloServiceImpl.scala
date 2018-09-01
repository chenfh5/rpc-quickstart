package io.github.chenfh5.rpc.thrift

import io.github.chenfh5.rpc.thrift.autogen.{Hello, Message, Response}
import org.slf4j.LoggerFactory

class HelloServiceImpl extends Hello.Iface {
  private val LOG = LoggerFactory.getLogger(getClass)

  override def helloString(param: String): String = {
    LOG.info("this is the HelloServiceImpl helloString param={}", param)
    param
  }

  override def helloInt(param: Int): Int = {
    LOG.info("this is the HelloServiceImpl helloInt param={}", param)
    Thread.sleep(2000)
    param
  }

  override def helloBoolean(param: Boolean): Boolean = {
    LOG.info("this is the HelloServiceImpl helloBoolean param={}", param)
    Thread.sleep(2000)
    param
  }

  override def helloVoid(): Unit = {
    println("this is the HelloServiceImpl helloVoid calling")
  }

  override def sendMessage(message: Message): Response = {
    LOG.info("this is the HelloServiceImpl sendMessage message={}", message)
    val resp = new Response(0, message)
    resp
  }

}
