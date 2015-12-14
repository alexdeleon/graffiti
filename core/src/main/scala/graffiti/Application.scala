package graffiti

import com.typesafe.config.{Config, ConfigFactory}
import graffiti.cli.{Cli, Command}
import graffiti.ioc.Injector
import graffiti.server.ServerCommand
import net.elehack.argparse4s.ExecutionContext

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
abstract class Application(appName: String) {

  implicit lazy val config: Config = ConfigFactory.load() withFallback ConfigFactory.load("graffiti")
  implicit lazy val context: Context = createContext

  private var commands: List[Command] = List(new ServerCommand)

  protected def createInjector: Injector = throw new IllegalStateException("You must mixin one of the Injector traits")
  protected def createContext: Context = Context(config, createInjector)

  def command(name: String)(f:  => Unit): Unit = command(new Command(name) {
    Ensuring(name != null, "Commands must have a name")
    override def run()(implicit ec: ExecutionContext): Unit = f
  })

  def command(cliCommand: Command): Unit = commands ::= cliCommand

  final def main(args: Array[String]) {
    Cli(appName, commands:_*).run(args)
  }

}
