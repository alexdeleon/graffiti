package graffiti

import spray.routing.{RequestContext, Directives, HttpService, Route}

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

  def route(r: Route): Unit = serviceRoute = r ~ serviceRoute
  
}
