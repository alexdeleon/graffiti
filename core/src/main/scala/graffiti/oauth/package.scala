package graffiti

import scala.concurrent.Future

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
package object oauth {
  type TokenAuthenticator[U] = Option[String] => Future[Option[U]]
}
