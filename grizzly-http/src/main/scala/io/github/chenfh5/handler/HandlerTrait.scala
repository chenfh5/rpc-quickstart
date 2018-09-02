package io.github.chenfh5.handler

import org.glassfish.grizzly.http.Method
import org.glassfish.grizzly.http.server.{HttpHandler, Request, Response}

trait HandlerTrait extends HttpHandler {

  override def service(request: Request, response: Response): Unit = {
    val method = request.getMethod
    method match {
      case Method.GET => doGet(request, response)
      case Method.POST => doPost(request, response)
      case Method.DELETE => doDelete(request, response)
      case _ => response.getWriter.write(s"Http Method $method not supported yet!"); response.finish()
    }
  }

  def doGet(request: Request, response: Response): Unit

  def doPost(request: Request, response: Response): Unit

  def doDelete(request: Request, response: Response): Unit
}
