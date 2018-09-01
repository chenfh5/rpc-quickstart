package io.github.chenfh5.rpc

import org.apache.thrift.server.TServer

trait Server {

  var server: TServer = _

  def init()

  def start(): Unit = server.serve() // block here until server.stop() call

  def stop(): Unit = server.stop()

}
