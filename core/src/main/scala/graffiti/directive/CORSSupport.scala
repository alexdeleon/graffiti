package graffiti.directive

import spray.http.HttpHeaders._
import spray.http.HttpMethods._
import spray.http._
import spray.routing._

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
trait CORSSupport extends Directives {

  private def allowOriginHeader(origin: Option[HttpOrigin]) = `Access-Control-Allow-Origin`(origin.map(o => SomeOrigins(Seq(o))).getOrElse(AllOrigins))
  private val optionsCorsHeaders = List(
    `Access-Control-Allow-Headers`("Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent"),
    `Access-Control-Max-Age`(1728000))

  def cors[T]: Directive0 = mapRequestContext { ctx => ctx.withRouteResponseHandling({
    //It is an option requeset for a resource that responds to some other method
    case Rejected(x) if ctx.request.method.equals(HttpMethods.OPTIONS) && x.exists(_.isInstanceOf[MethodRejection]) => {
      val allowedMethods: List[HttpMethod] = x.filter(_.isInstanceOf[MethodRejection]).map(rejection => {
        rejection.asInstanceOf[MethodRejection].supported
      })
      ctx.complete(HttpResponse().withHeaders(
        `Access-Control-Allow-Credentials`(true) :: `Access-Control-Allow-Methods`(OPTIONS, allowedMethods: _*) :: allowOriginHeader(requestOrigin(ctx)) ::
          optionsCorsHeaders
      ))
    }
  }).withHttpResponseHeadersMapped { headers =>
    `Access-Control-Allow-Credentials`(true) :: allowOriginHeader(requestOrigin(ctx)) :: headers
  }
  }

  def requestOrigin(implicit ctx: RequestContext) = ctx.request.header[Origin].map(_.value).map(HttpOrigin(_))
}


