package io.github.chenfh5

import io.github.chenfh5.rpc.t1_blocking_threadpool.{T1Client, T1Server}
import io.github.chenfh5.rpc.t2_non_blocking_multiple_thread.{T2Client, T2Server}
import io.github.chenfh5.rpc.t3_ssl_threadpool.{T3Client, T3Server}
import io.github.chenfh5.rpc.t4_multiple_processor_threadpool.{T4Client, T4Server}
import io.github.chenfh5.rpc.t5_non_blocking_half.{T5Client, T5Server}
import org.slf4j.LoggerFactory
import org.testng.Assert
import org.testng.annotations.{AfterClass, BeforeClass, Test}

class ThriftRpcTest {
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
  def testServer1(): Unit = {
    val server = T1Server()
    (0 to 1).toList.par.foreach { // TODO: should use the testng parallel
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        server.init()
        server.start()
      case row if row % 2 == 1 =>
        Thread.sleep(1000)
        println(s"thread id=${Thread.currentThread().getId}")
        val resp = T1Client("testClient1")
        Assert.assertTrue(resp.message.data.contains("testClient1"))
        server.stop()
      case _ => None
    }
  }

  @Test(enabled = true, priority = 1)
  def testServer2(): Unit = {
    val server = T2Server()
    (0 to 1).toList.par.foreach {
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        server.init()
        server.start()
      case row if row % 2 == 1 =>
        Thread.sleep(1000)
        println(s"thread id=${Thread.currentThread().getId}")
        val resp = T2Client("testClient2")
        println("testServer2 async done")
        println(s"testClient2 resp=$resp")
        Assert.assertNotNull(resp)
        server.stop()
        println("testServer2 async UT done")
      case _ => None
    }
  }

  @Test(enabled = true, priority = 1)
  def testServer3(): Unit = {
    val server = T3Server()
    (0 to 1).toList.par.foreach {
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        server.init()
        server.start()
      case row if row % 2 == 1 =>
        Thread.sleep(1000)
        println(s"thread id=${Thread.currentThread().getId}")
        val resp = T3Client("testClient3")
        Assert.assertNotNull(resp)
        Assert.assertTrue(resp.message.data.contains("testClient3"))
        server.stop()
      case _ => None
    }
  }

  @Test(enabled = true, priority = 1)
  def testServer4(): Unit = {
    val server = T4Server()
    (0 to 1).toList.par.foreach {
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        server.init()
        server.start()
      case row if row % 2 == 1 =>
        Thread.sleep(1000)
        println(s"thread id=${Thread.currentThread().getId}")
        val resp = T4Client("testClient4")
        Assert.assertNotNull(resp)
        Assert.assertTrue(resp.message.data.contains("testClient4"))
        server.stop()
      case _ => None
    }
  }

  @Test(enabled = true, priority = 1)
  def testServer5(): Unit = {
    val server = T5Server()
    (0 to 1).toList.par.foreach {
      case row if row % 2 == 0 =>
        println(s"thread id=${Thread.currentThread().getId}")
        server.init()
        server.start()
      case row if row % 2 == 1 =>
        Thread.sleep(1000)
        println(s"thread id=${Thread.currentThread().getId}")
        val resp = T5Client("testClient5")
        Assert.assertNotNull(resp)
        Assert.assertTrue(resp.message.data.contains("testClient5"))
        server.stop()
      case _ => None
    }
  }

}
