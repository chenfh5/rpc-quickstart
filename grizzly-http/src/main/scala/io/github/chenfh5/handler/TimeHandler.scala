package io.github.chenfh5.handler

import java.nio.charset.StandardCharsets

import io.github.chenfh5.OwnUtils
import org.glassfish.grizzly.http.server.{Request, Response}
import org.slf4j.LoggerFactory

class TimeHandler extends HandlerTrait {
  private val LOG = LoggerFactory.getLogger(getClass)

  override def doGet(request: Request, response: Response): Unit = {
    LOG.debug("this is the timeHandler")
    response.setCharacterEncoding(StandardCharsets.UTF_8.toString)
    response.setContentType("text/plain")
    response.getWriter.write(s"TimeHandler telling your that now is ${OwnUtils.getTimeNow()}")
    response.finish()
  }

  override def doPost(request: Request, response: Response): Unit = ???

  override def doDelete(request: Request, response: Response): Unit = ???
}

object TimeHandler {
  def apply(): TimeHandler = new TimeHandler()
}
