package io.github.chenfh5

import java.util.Properties

object OwnConfigReader {

  private val properties = {
    val properties = new Properties()
    properties.load(getClass.getResourceAsStream("/config/variable.properties"))
    properties
  }

  object OwnConfig {
    val SERVER_HOST = getAsStr("SERVER_HOST")

    val SERVER_PORT_1 = getAsInt("SERVER_PORT_1")
    val SERVER_PORT_2 = getAsInt("SERVER_PORT_2")
    val SERVER_PORT_3 = getAsInt("SERVER_PORT_3")
    val SERVER_PORT_4 = getAsInt("SERVER_PORT_4")
    val SERVER_PORT_5 = getAsInt("SERVER_PORT_5")

    val HTTP_SERVER_PORT_1 = getAsInt("HTTP_SERVER_PORT_1")
    val HTTP_SERVER_PORT_2 = getAsInt("HTTP_SERVER_PORT_2")
    val NEED_AUTH = getAsBoolean("NEED_AUTH")
    val AUTH = getAsStr("_AUTH")

    val TIMEOUT_MILLS = getAsInt("SERVER_HOST")
  }

  private def getAsInt(str: String): Int = properties.getProperty(str).toInt

  private def getAsBoolean(str: String): Boolean = properties.getProperty(str).toBoolean

  private def getAsStr(str: String): String = properties.getProperty(str)

}
