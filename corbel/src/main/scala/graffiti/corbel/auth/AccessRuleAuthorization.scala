package graffiti.corbel.auth

import com.google.gson.JsonObject
import spray.http.HttpRequest
import spray.routing.RequestContext

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class AccessRuleAuthorization(authorizationInfo: AuthorizationInfo) extends (RequestContext => Boolean)
  with AccessRulesMatchers {

  def apply(requestContext: RequestContext): Boolean = {
    implicit val request = requestContext.request
    authorizationInfo.accessRules.exists(validate)
  }

  def validate(rule: JsonObject)(implicit request: HttpRequest): Boolean = {
    matchesMethod(request.method.name, rule) &&
    matchesUriPath(request.uri.path.toString(), rule) &&
    matchesTokenType(authorizationInfo.tokenReader.getInfo, rule) &&
    matchesMediaTypes(request.acceptedMediaRanges.map(_.value), rule)
  }

}