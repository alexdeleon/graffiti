package graffiti.corbel.auth

import com.google.gson.JsonObject
import grizzled.slf4j.Logging
import io.corbel.lib.token.parser.TokenParser
import io.corbel.lib.token.reader.TokenReader
import io.corbel.lib.ws.auth.AuthorizationRulesService

import scala.collection.JavaConverters._

/**
 * @author Alberto J. Rubio
 */
class DefaultCorbelAuth(audience: String, authorizationRulesService: AuthorizationRulesService, tokenParser: TokenParser)
  extends  (String  => Option[AuthorizationInfo]) with Logging {

  def apply(token: String): Option[AuthorizationInfo] = {
    try {
      val tokenReader: TokenReader = tokenParser.parseAndVerify(token)
      authorizationRulesService.getAuthorizationRules(token, audience) match {
        case accessRules: java.util.Set[JsonObject] if !accessRules.isEmpty => Some(AuthorizationInfo(tokenReader, accessRules.asScala))
        case _ => None
      }
    }
    catch {
      case ex: Exception => {
        warn(s"Failed authentication: ${ex.getMessage}")
        None
      }
    }
  }
}



object CorbelAuth {
  def apply(audience: String, authorizationRulesService: AuthorizationRulesService, tokenParser: TokenParser): CorbelAuth =
    new DefaultCorbelAuth(audience, authorizationRulesService, tokenParser)
}

