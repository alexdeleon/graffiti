package graffiti.server

import akka.actor.ActorSystem
import akka.io.IO
import akka.util.Timeout
import graffiti.{ServiceActor, Context}
import graffiti.cli.{ProgramArguments, Command}
import akka.pattern.ask
import spray.can.Http
import scala.concurrent.duration._

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class ServerCommand extends Command("server") {

  override val description = Some("Run HTTP server")

  def apply(context: Context): Unit = {
    val interface =  context.config.getString("http.interface")
    val port = context.config.getInt("http.port")
    info(s"Starting HTTP server on $interface:$port")
    implicit val system = context.injector.getInstance[ActorSystem]
    implicit val timeout = Timeout(5 seconds)
    IO(Http) ? Http.Bind(system.actorOf(ServiceActor.props(context.service)),
      interface = interface,
      port = port)
  }
}
