package io.github.chenfh5.g2_auth

import io.github.chenfh5.OwnConfigReader.OwnConfig
import scalaj.http.{Http, HttpResponse}

class G2Client {

  // time
  def getNow(auth: String): HttpResponse[String] = {
    val resp = Http(url = "http://%s:%s/%s".format(OwnConfig.SERVER_HOST, OwnConfig.HTTP_SERVER_PORT_2, "time")).header("Authorization", auth).asString // default Method is `GET`
    resp
  }

}

object G2Client {
  def apply(): G2Client = new G2Client()
}
