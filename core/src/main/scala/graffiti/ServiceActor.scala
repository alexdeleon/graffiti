package graffiti

import akka.actor.Props
import spray.routing.{HttpServiceActor, Route}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
trait ServiceActor extends HttpServiceActor {
  val route: Route
  override def receive: Receive = runRoute(route)
}

object ServiceActor {
  def props(r: Route): Props = Props(new ServiceActor {
    override val route: Route = r
  })
  def props(service: Service): Props = props(service())
}
