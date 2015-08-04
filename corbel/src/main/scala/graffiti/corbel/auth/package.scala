package graffiti.corbel

import graffiti.oauth.OAuth
import spray.routing._

import scala.concurrent.ExecutionContext
import spray.routing.Directives._
/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
package object auth {

  type CorbelAuth = String => Option[AuthorizationInfo]

  def secure(secureMagnent: SecureMagnent): Directive0 = secureMagnent.directive

  class SecureMagnent(auth: CorbelAuth)(implicit executionContext: ExecutionContext) {
    val directive: Directive0 =  {
      val factory = new CorbelAuthFactory(auth)
      authenticate(OAuth.apply(factory.TokenAuthenticator(executionContext))).flatMap {
        case authorizationInfo: AuthorizationInfo => authorize(factory.AccessRuleAuthorization(authorizationInfo))
        case _ => reject
      }
    }
  }

  object SecureMagnent {
    implicit def fromCorbelAuth(auth: CorbelAuth)(implicit executionContext: ExecutionContext): SecureMagnent =
      new SecureMagnent(auth)
  }

  implicit def asFactory(auth: CorbelAuth): CorbelAuthFactory = new CorbelAuthFactory(auth)

  class CorbelAuthFactory(auth: CorbelAuth) {
    object TokenAuthenticator {
      def apply(implicit executionContext: ExecutionContext) = new TokenAuthenticator(auth)
    }

    object AccessRuleAuthorization {
      def apply(authorizationInfo: AuthorizationInfo) = new AccessRuleAuthorization(authorizationInfo)
    }
  }
}
