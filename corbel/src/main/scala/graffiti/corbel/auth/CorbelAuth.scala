package graffiti.corbel.auth

import com.google.gson.{JsonPrimitive, JsonArray, JsonElement, JsonObject}
import io.corbel.lib.token.TokenInfo
import io.corbel.lib.token.parser.TokenParser
import io.corbel.lib.token.reader.TokenReader

import CorbelAuth._

/**
 * @author Alberto J. Rubio
 */
trait CorbelAuth {

  implicit def JsonArrayWrapper(jsonArray: JsonArray) = new {
    def containsAny(elements: Seq[JsonElement]): Boolean = elements.exists(jsonArray.contains(_))
  }

  implicit def stringAsJsonPrimitive(string: String): JsonPrimitive = new JsonPrimitive(string)

  def extractAuthorizationInfo(token: String)(implicit parser: TokenParser): Option[AuthorizationInfo] = {
    try {
      val tokenReader: TokenReader = parser.parseAndVerify(token)
      //TODO: Work in progress, we need token access rules
      //val accessRules: Set[JsonObject] = authorizationRulesService.getAuthorizationRules(token, audience)
      //if (accessRules != null && accessRules.nonEmpty) {
      Some(new AuthorizationInfo(tokenReader, Set.empty))
    }
    catch {
      case _: Exception => None
    }
  }

  def matchesMethod(method: String, rule: JsonObject): Boolean = rule.get(Methods) match {
      case methods: JsonArray => methods.contains(method)
      case _ => false
    }


  def matchesMediaTypes(accepts: Seq[String], rule: JsonObject): Boolean = rule.get(MediaTypes) match {
      case mediaTypes: JsonArray => mediaTypes.containsAny(accepts.map(stringAsJsonPrimitive))
      case _ => false
    }

  def matchesUriPath(uri: String, rule: JsonObject): Boolean = rule.get(uri) match {
      case uriRegex: JsonPrimitive => uri.matches(uriRegex.getAsString)
      case _ => false
    }

  def matchesTokenType(token: TokenInfo, rule: JsonObject): Boolean = rule.get(TokenType) match {
      case tokenType: JsonPrimitive => tokenType.getAsString match {
        case User => token.getUserId != null
        case _ => false
      }
      case _ => true
    }

}

private object CorbelAuth {
  val TokenType = "tokenType"
  val User = "user"
  val Uri = "uri"
  val MediaTypes = "mediaTypes"
  val Methods = "methods"
}

case class AuthorizationInfo(tokenReader: TokenReader, accessRules: Set[JsonObject])