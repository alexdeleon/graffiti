package graffiti.corbel.auth

import com.google.gson.JsonObject
import io.corbel.lib.token.TokenInfo
import io.corbel.lib.token.parser.TokenParser
import io.corbel.lib.token.reader.TokenReader
import shapeless.HNil
import spray.http.HttpMethods
import spray.routing.directives.BasicDirectives._
import spray.routing.directives.HeaderDirectives._
import spray.routing.directives.RouteDirectives._
import spray.routing.{AuthorizationFailedRejection, Directive0}

import scala.collection.JavaConversions._

/**
 * @author Alberto J. Rubio
 */
trait AuthorizationDirectives {

  val TOKEN_PREFIX = "Bearer"

  def extractToken(value: String): Option[String] = {
    if (value.startsWith(TOKEN_PREFIX)) {
      Some(value.substring(TOKEN_PREFIX.length))
    }
    else None
  }

  def extractAuthorizationInfo(parser: TokenParser, token: String): Option[AuthorizationInfo] = {
    try
      val tokenReader: TokenReader = parser.parseAndVerify(token)
      //TODO: Work in progress, we need token access rules
      //val accessRules: Set[JsonObject] = authorizationRulesService.getAuthorizationRules(token, audience)
      //if (accessRules != null && accessRules.nonEmpty) {
      Some(new AuthorizationInfo(tokenReader, Set.empty))
      //else None
    catch {
      case _: Exception => None
    }
  }

  def matchesMethod(method: String, rule: JsonObject): Boolean = {
    rule.get("methods") match {
      case methods: Any => methods.getAsJsonArray.foreach(json => {
        if (method.equals(json.getAsString)) {
          return true
        }
      })
    }
    false
  }

  def matchesMediaTypes(accepts: Array[String], rule: JsonObject): Boolean = {
    rule.get("mediaTypes") match {
      case mediaTypes: Any => mediaTypes.getAsJsonArray.foreach(json => {
        if (accepts.contains(json.getAsString)) {
          return true
        }
      })
    }
    false
  }

  def matchesUriPath(uri: String, rule: JsonObject): Boolean = {
    rule.get("uri") match {
      case json: Any => uri.matches(json.getAsString)
      case _ => false
    }
  }

  def matchesTokenType(token: TokenInfo, rule: JsonObject): Boolean = {
    rule.get("tokenType") match {
      case tokenType: Any => tokenType.getAsString match {
        case "user" => token.getUserId != null
        case _ => false
      }
      case _ => true
    }
  }

  def authorize(parser: TokenParser): Directive0 = {
    extract(_.request.method).flatMap[HNil] {
      case HttpMethods.OPTIONS => pass
      case _ ⇒ optionalHeaderValueByName("Authorization") flatMap {
        header => header flatMap {
          authHeader => extractToken(authHeader)
        } match {
          case Some(token) => extractAuthorizationInfo(parser, token) match {
            case Some(authorizationInfo) => authorizationInfo.accessRules.foreach(rule => {
              val method = matchesMethod(extract(_.request.method.toString()).flatMap[HNil].toString(), rule)
              val uri = matchesUriPath(extract(_.request.uri.path.toString()).flatMap[HNil].toString(), rule)
              val token = matchesTokenType(authorizationInfo.tokenReader.getInfo, rule)
              val mediatype = matchesMediaTypes(headerValueByName("Accept").flatMap[HNil].toString().split(","), rule)
            })
          }
        }
        reject(AuthorizationFailedRejection)
      }
    }
  }
}

case class AuthorizationInfo(tokenReader: TokenReader, accessRules: Set[JsonObject])