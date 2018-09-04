package io.github.chenfh5.rpc

import org.apache.thrift.server.TServer
import org.slf4j.LoggerFactory

trait Server {
  private val LOG = LoggerFactory.getLogger(getClass)

  val serverType: String
  var server: TServer = _

  def init()

  def start(): Unit = {
    LOG.info("this is the {} starting...", serverType)
    server.serve() // block here until server.stop() call
  }

  def stop(): Unit = {
    LOG.info("this is the {} stopping...", serverType)
    if (server != null) server.stop()
    LOG.info("this is the {} shutdown!!!", serverType)
  }

}
