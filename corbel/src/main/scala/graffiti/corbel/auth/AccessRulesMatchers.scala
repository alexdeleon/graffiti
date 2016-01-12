package graffiti.corbel.auth

import com.google.gson.{JsonArray, JsonElement, JsonObject, JsonPrimitive}
import io.corbel.lib.token.TokenInfo

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
trait AccessRulesMatchers {

  import JsonRuleHelper._

  def matchesMethod(method: String, rule: JsonObject): Boolean = rule.get(Methods) match {
    case methods: JsonArray => methods.contains(method)
    case _ => false
  }


  def matchesMediaTypes(accepts: Seq[String], rule: JsonObject): Boolean = rule.get(MediaTypes) match {
    case mediaTypes: JsonArray => mediaTypes.containsAny(accepts.map(stringAsJsonPrimitive))
    case _ => false
  }

  def matchesUriPath(uri: String, rule: JsonObject): Boolean = rule.get(Uri) match {
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

private object JsonRuleHelper {
  val TokenType = "tokenType"
  val User = "user"
  val Uri = "uri"
  val MediaTypes = "mediaTypes"
  val Methods = "methods"

  implicit class JsonArrayWrapper(val jsonArray: JsonArray) extends AnyVal {
    def containsAny(elements: Seq[JsonElement]): Boolean = elements.exists(jsonArray.contains)
  }

  implicit def stringAsJsonPrimitive(string: String): JsonPrimitive = new JsonPrimitive(string)
}