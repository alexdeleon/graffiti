package graffiti

import spray.http.OAuth2BearerToken

import scala.concurrent.Future

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
package object oauth {
  type TokenAuthenticator[U] = Option[String] => Future[Option[U]]
}
