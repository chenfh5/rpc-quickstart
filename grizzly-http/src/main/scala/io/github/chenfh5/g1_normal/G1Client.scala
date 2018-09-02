package io.github.chenfh5.g1_normal

import io.github.chenfh5.Configuration
import scalaj.http.{Http, HttpResponse}

class G1Client {

  // time
  def getNow(): HttpResponse[String] = {
    val resp = Http(url = "http://%s:%s/%s".format(Configuration.SERVER_HOST, Configuration.HTTP_SERVER_PORT_1, "time")).asString // default Method is `GET`
    resp
  }

  // student
  def addStudents(json: String): HttpResponse[String] = {
    val resp = Http(url = "http://%s:%s/%s".format(Configuration.SERVER_HOST, Configuration.HTTP_SERVER_PORT_1, "student")).postData(json).method("POST").asString
    resp
  }

  def getSomeStudent(id: List[Long]): HttpResponse[String] = {
    val resp = Http(url = "http://%s:%s/%s".format(Configuration.SERVER_HOST, Configuration.HTTP_SERVER_PORT_1, "student")).param("id", id.mkString(",")).asString
    resp
  }

  def getWholeStudent(): HttpResponse[String] = {
    val resp = Http(url = "http://%s:%s/%s".format(Configuration.SERVER_HOST, Configuration.HTTP_SERVER_PORT_1, "student")).asString
    resp
  }

  def deleteSomeStudent(id: List[Long]): HttpResponse[String] = {
    val resp = Http(url = "http://%s:%s/%s".format(Configuration.SERVER_HOST, Configuration.HTTP_SERVER_PORT_1, "student")).param("id", id.mkString(",")).method("DELETE").asString
    resp
  }

}

object G1Client {
  def apply(): G1Client = new G1Client()
}
