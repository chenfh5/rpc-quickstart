package io.github.chenfh5.g1_normal

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.Server
import io.github.chenfh5.handler.{StudentHandler, TimeHandler}
import org.glassfish.grizzly.http.server.NetworkListener
import org.slf4j.LoggerFactory

class G1Server extends Server {
  private val LOG = LoggerFactory.getLogger(getClass)

  override def init(): Unit = {
    LOG.info(s"G1Server host=${OwnConfig.SERVER_HOST}, port=${OwnConfig.HTTP_SERVER_PORT_1}")
    server.addListener(new NetworkListener("chenfh5 grizzly http server", OwnConfig.SERVER_HOST, OwnConfig.HTTP_SERVER_PORT_1))
    addHandler()
  }

  def addHandler(): Unit = {
    server.getServerConfiguration.addHttpHandler(TimeHandler(), "/" + "time")
    server.getServerConfiguration.addHttpHandler(StudentHandler(), "/" + "student")
  }

}

object G1Server {
  def apply(): G1Server = new G1Server()
}
