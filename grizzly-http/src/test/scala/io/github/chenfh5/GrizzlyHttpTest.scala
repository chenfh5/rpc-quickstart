package io.github.chenfh5

import io.github.chenfh5.OwnConfigReader.OwnConfig
import io.github.chenfh5.g1_normal.{G1Client, G1Server}
import io.github.chenfh5.g2_auth.{G2Client, G2Server}
import org.slf4j.LoggerFactory
import org.testng.Assert
import org.testng.annotations.{AfterClass, BeforeClass, Test}

class GrizzlyHttpTest {
  private val LOG = LoggerFactory.getLogger(getClass)

  @BeforeClass
  def setUp(): Unit = {
    LOG.info("this is the test begin={}", OwnUtils.getTimeNow())
  }

  @AfterClass
  def shut(): Unit = {
    LOG.info("this is the test   end={}", OwnUtils.getTimeNow())
  }

  @Test(enabled = true, priority = 1)
  def testG1Server(): Unit = {
    val g1Server = G1Server()
    println(s"this is the testG1Server NEED_AUTH=${OwnConfigReader.OwnConfig.NEED_AUTH}")
    println(s"this is the testG1Server _AUTH64=${Integer.toHexString(OwnConfigReader.OwnConfig._AUTH64.hashCode())}") // 3ac7bd7a
    println(s"this is the testG1Server NEED_AUTH Address=${Integer.toHexString(OwnConfigReader.OwnConfig.NEED_AUTH.hashCode())}") // address=4d5
    (0 to 1).toList.par.foreach {
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        g1Server.init()
        g1Server.start()
        Thread.sleep(5000) // server persistent manually
      case row if row % 2 == 1 =>
        Thread.sleep(1000) // wait for server bootstrap
        println(s"thread id=${Thread.currentThread().getId}")
        testG1Client()
        g1Server.stop()
    }
  }

  @Test(enabled = true, priority = 1)
  def testG2Server(): Unit = {
    OwnConfigReader.OwnConfig.NEED_AUTH = true // Modify Config Manually for UT Only
    val g2Server = G2Server()
    println(s"this is the testG2Server NEED_AUTH=${OwnConfigReader.OwnConfig.NEED_AUTH}")
    println(s"this is the testG2Server _AUTH64=${Integer.toHexString(OwnConfigReader.OwnConfig._AUTH64.hashCode())}") // 3ac7bd7a, same with testG1Server
    println(s"this is the testG2Server NEED_AUTH Address=${Integer.toHexString(OwnConfigReader.OwnConfig.NEED_AUTH.hashCode())}") // address=4cf, not equal testG1Server

    (0 to 1).toList.par.foreach {
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        g2Server.init()
        g2Server.start()
        Thread.sleep(5000) // server persistent manually
      case row if row % 2 == 1 =>
        Thread.sleep(1000) // wait for server bootstrap
        println(s"thread id=${Thread.currentThread().getId}")
        testG2ClientFake()
        testG2Client()
        g2Server.stop()
    }
  }

  def testG1Client(): Unit = {
    val g1Client = G1Client()
    // 1
    val resp1 = g1Client.getNow()
    println(resp1.body)
    Assert.assertEquals(resp1.code, 200)

    // 2
    val postBody =
      """
        {
          "11": "Federer",
          "12": "Nadal",
          "13": "Kilian",
          "14": "Messi",
          "15": "Muller",
          "16": "Beckham"
        }
      """
    val resp2 = g1Client.addStudents(postBody)
    println(resp2.body)
    Assert.assertEquals(resp2.code, 200)

    // 3
    val resp3 = g1Client.getSomeStudent(List(11, 13, 15, 17, 19))
    println(resp3.body)
    Assert.assertEquals(resp3.code, 200)

    // 4
    val resp4 = g1Client.getWholeStudent()
    println(resp4.body)
    Assert.assertEquals(resp4.code, 200)

    // 5
    val resp5 = g1Client.deleteSomeStudent(List(9, 12, 14, 23))
    println(resp5.body)
    Assert.assertEquals(resp5.code, 200)
  }

  def testG2ClientFake(): Unit = {
    val g2Client = G2Client()
    val resp1 = g2Client.getNow("chenfh5:grizzly")
    println(resp1.body)
    Assert.assertEquals(resp1.code, 401)
  }

  def testG2Client(): Unit = {
    val g2Client = G2Client()
    val resp1 = g2Client.getNow(OwnConfig._AUTH64)
    println(resp1.body)
    Assert.assertEquals(resp1.code, 200)
  }

  def testCurl(): Unit = {
    val url1 = "http://localhost:8086/time" // get
    val url2 = "http://localhost:8086/student?id=13,14,15,16,9" // get with parameters
  }

}
