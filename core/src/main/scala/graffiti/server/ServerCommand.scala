package graffiti.server

import akka.actor.ActorSystem
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import graffiti.cli.Command
import graffiti.{Context, ServiceActor}
import grizzled.slf4j.Logging
import net.elehack.argparse4s.ExecutionContext
import spray.can.Http

import scala.concurrent.duration._

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class ServerCommand(implicit cxt: Context) extends Command("server", Some("Run HTTP server")) with Logging {

  val p = option[Option[Int]]('p').metavar("PORT").help("Binding port number")
  val i = option[Option[String]]('i').metavar("INTERFACE").help("Binding interface (e.g. 0.0.0.0)")

  override def run()(implicit exc: ExecutionContext): Unit =  {
    val interface = i.get getOrElse cxt.config.getString("http.interface")
    val port = p.get getOrElse cxt.config.getInt("http.port")
    info(s"Starting HTTP server on $interface:$port")
    implicit val system = cxt.injector.getInstance[ActorSystem]
    implicit val timeout = Timeout(5 seconds)
    IO(Http) ? Http.Bind(system.actorOf(ServiceActor.props(cxt.service), "graffiti.service"),
      interface = interface,
      port = port)
  }
}
