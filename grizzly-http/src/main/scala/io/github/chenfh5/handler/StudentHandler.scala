package io.github.chenfh5.handler

import org.apache.commons.lang3.StringUtils
import org.glassfish.grizzly.http.server.{Request, Response}
import org.slf4j.LoggerFactory

class StudentHandler extends HandlerTrait {
  private val LOG = LoggerFactory.getLogger(getClass)

  // https://www.scala-lang.org/api/2.12.6/scala/collection/concurrent/TrieMap.html
  private val globalMap = scala.collection.concurrent.TrieMap[Long, String]() // 1L->张三

  override def doGet(request: Request, response: Response): Unit = {
    LOG.debug("this is the StudentHandler doGet")
    val id = request.getParameter("id")
    if (StringUtils.isNoneBlank(id)) { // return single
      LOG.info(s"this is the StudentHandler doGet id=$id")
      id.split(',').foreach { oneId =>
        val res = globalMap.get(oneId.toLong)
        res match {
          case Some(v) => response.getWriter.write(s"id=$oneId, name=$v\n")
          case None => response.getWriter.write(s"id=$oneId, name not exists\n")
        }
      }
    } else {
      LOG.info(s"this is the StudentHandler doGet whole")
      response.getWriter.write(globalMap.toList.sortWith(_._1 < _._1).toString) // return whole
    }
    response.finish()
  }

  override def doPost(request: Request, response: Response): Unit = {
    LOG.debug("this is the StudentHandler doPost")
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats

    val postBodyStr = scala.io.Source.fromInputStream(request.getInputStream).mkString
    val postBodyMap = parse(postBodyStr).extract[Map[Long, String]]
    LOG.info(s"this is the StudentHandler doPost postBodyMap=$postBodyMap")
    val rawSize = globalMap.size
    postBodyMap.foreach { onePair =>
      val (id, name) = (onePair._1, onePair._2)
      globalMap.put(id, name) // TODO: replace the old value directly is well enough?
    }
    response.getWriter.write(s"add ${globalMap.size - rawSize} student success")
    response.finish()
  }

  override def doDelete(request: Request, response: Response): Unit = {
    LOG.debug("this is the StudentHandler doDelete")
    val id = request.getParameter("id")
    if (StringUtils.isNoneBlank(id)) { // return single
      LOG.info(s"this is the StudentHandler doDelete id=$id")
      id.split(',').foreach { oneId =>
        val res = globalMap.remove(oneId.toLong)
        res match {
          case Some(v) => response.getWriter.write(s"(id=$oneId, name=$v) remove success\n")
          case None => response.getWriter.write(s"id=$oneId not exists\n")
        }
      }
    } else response.getWriter.write("Please input the delete id") // return whole
    response.finish()
  }

}

object StudentHandler {
  def apply(): StudentHandler = new StudentHandler()
}
