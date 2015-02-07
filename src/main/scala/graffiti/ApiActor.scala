package graffiti

import akka.actor.Actor.Receive
import akka.actor.Props
import spray.routing.{HttpServiceActor, Route}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
trait ApiActor extends HttpServiceActor {
  val route: Route
  override def receive: Receive = runRoute(route)
}

object ApiActor {
  def apply(r: Route): Props = Props(new ApiActor {
    override val route: Route = r
  })
  def apply(service: Service): Props = ApiActor(service())
}
