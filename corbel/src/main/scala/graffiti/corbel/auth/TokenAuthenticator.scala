package graffiti.corbel.auth

import io.corbel.lib.token.TokenInfo
import io.corbel.lib.token.parser.TokenParser
import io.corbel.lib.token.reader.TokenReader

import scala.concurrent.{ExecutionContext, Future}
/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class TokenAuthenticator(implicit parser: TokenParser, executionContext: ExecutionContext) extends (Option[String] => Future[Option[AuthorizationInfo]]) with CorbelAuth {

  def apply(token: Option[String]): Future[Option[AuthorizationInfo]] = Future {
    token flatMap extractAuthorizationInfo
  }

}
