package graffiti

import spray.routing.{RequestContext, Directives, HttpServiceActor, Route}


/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class Api(routes: Route*) extends Service {
  route(routes.reduceLeft(_ ~ _))
}

object Api {
  def apply(routes: Route*): Api = new Api(routes:_*)
}