package graffiti

import spray.routing.{Directives, Route}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
abstract class Service(path: Option[String] = None) extends (() => Route) with Directives {

  def this(path: String) = this(Some(path))

  private var serviceRoute: Route = reject()

  override def apply =  path match {
    case None => serviceRoute
    case Some(p) => pathPrefix(p) { serviceRoute }
  }

  def route(route: Route): Unit = serviceRoute = route ~ serviceRoute

  def ~(next: Service) = Service.compose(this, next)
}

object Service {
  def compose(services: Service*): Service = new Service() {
    route(services.map(_.apply).reduceLeft(_ ~ _))
  }
}