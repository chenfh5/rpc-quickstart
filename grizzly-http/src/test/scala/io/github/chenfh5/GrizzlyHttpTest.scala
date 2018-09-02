package io.github.chenfh5

import io.github.chenfh5.g1_normal.{G1Client, G1Server}
import io.github.chenfh5.g2_auth.G2Server
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

  @Test(enabled = true)
  def testG1Server(): Unit = {
    val g1Server = G1Server()
    g1Server.init()
    g1Server.start()
    Thread.sleep(30 * 60 * 1000)
  }

  @Test(enabled = true)
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

  @Test(enabled = false)
  def testCurl(): Unit = {
    val url1 = "http://localhost:8086/time" // get
    val url2 = "http://localhost:8086/student?id=13,14,15,16,9"
  }

  @Test(enabled = true)
  def testG2Server(): Unit = {
    val g2Server = G2Server()
    g2Server.init()
    g2Server.start()
    Thread.sleep(30 * 60 * 1000)
  }

}
