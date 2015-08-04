package graffiti.corbel.auth

import scala.concurrent.{ExecutionContext, Future}
/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class TokenAuthenticator(auth: CorbelAuth)(implicit executionContext: ExecutionContext) extends (Option[String] => Future[Option[AuthorizationInfo]]) {

  def apply(token: Option[String]): Future[Option[AuthorizationInfo]] = Future {
    token flatMap auth
  }

}
