package graffiti

import com.typesafe.config.{Config, ConfigFactory}
import graffiti.cli.Cli
import graffiti.ioc.Injector
import graffiti.server.ServerCommand

import scala.reflect.{ClassTag, classTag}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
abstract class Application(appName: String) {

  lazy val config: Config = ConfigFactory.load() withFallback ConfigFactory.load("graffiti")
  lazy val context: Context = Context(config, createInjector)

  private var commands = List(new ServerCommand())

  def createInjector: Injector = throw new IllegalStateException("You must mixin any of an Injector trait")

  def command(name: String)(f: Context => Unit) = {}

  final def main(args: Array[String]) {
    new Cli(appName, commands:_*).run(args, context)
  }

}
