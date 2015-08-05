package graffiti.corbel.auth

import com.google.gson.JsonParser
import io.corbel.lib.token.TokenInfo
import io.corbel.lib.token.parser.TokenParser
import io.corbel.lib.token.reader.TokenReader
import io.corbel.lib.ws.auth.AuthorizationRulesService
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.mock.MockitoSugar
import spray.http.HttpHeaders._
import spray.http.OAuth2BearerToken
import spray.routing.{AuthorizationFailedRejection, AuthenticationFailedRejection, HttpService}
import spray.testkit.ScalatestRouteTest
import spray.http.StatusCodes._
import org.mockito.Mockito._
import scala.collection.JavaConversions._
import spray.http.MediaTypes._
/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class CorbelAuthTest extends FlatSpec with ScalatestRouteTest with HttpService with MockitoSugar with Matchers with ScalaFutures {
  def actorRefFactory = system // connect the DSL to the test ActorSystem

  val serviceMock = mock[AuthorizationRulesService]
  val tokenParserMock = mock[TokenParser]
  val jsonParser = new JsonParser
  val testAudience = "test"

  "authenticate directive" should "reject when no token is provided" in {
    val route = {
      secure(CorbelAuth(testAudience, serviceMock, tokenParserMock)) {
        get {
          complete("done")
        }
      }
    }

    Get() ~> route ~> check {
      rejection shouldBe a [AuthenticationFailedRejection]
    }

  }

  it should "accept request" in {
    val testToken = "12345"
    val testTokenReader = mock[TokenReader]
    val testRules = Set(jsonParser.parse(
      """
        |{
        | "uri": "/test",
        | "methods": ["GET"],
        | "mediaTypes": ["text/plain"]
        |}
      """.stripMargin).getAsJsonObject)
    when(tokenParserMock.parseAndVerify(testToken)).thenReturn(testTokenReader)
    when(serviceMock.getAuthorizationRules(testToken, testAudience)).thenReturn(testRules)
    val route = {
      secure(CorbelAuth(testAudience, serviceMock, tokenParserMock)) {
        get {
          complete("done")
        }
      }
    }

    Get("/test").withHeaders(Accept(`text/plain`), Authorization(OAuth2BearerToken(testToken))) ~> route ~> check {
      val content = responseAs[String]
      println(content)
      status === OK
      content === "done"
    }

  }

  it should "rejected when uri does not match" in {
    val testToken = "12345"
    val testTokenReader = mock[TokenReader]
    val testRules = Set(jsonParser.parse(
      """
        |{
        | "uri": "/test",
        | "methods": ["GET"],
        | "mediaTypes": ["text/plain"]
        |}
      """.stripMargin).getAsJsonObject)
    when(tokenParserMock.parseAndVerify(testToken)).thenReturn(testTokenReader)
    when(serviceMock.getAuthorizationRules(testToken, testAudience)).thenReturn(testRules)
    val route = {
      secure(CorbelAuth(testAudience, serviceMock, tokenParserMock)) {
        get {
          complete("done")
        }
      }
    }

    Get("/test2").withHeaders(Accept(`text/plain`), Authorization(OAuth2BearerToken(testToken))) ~> route ~> check {
      rejection should be (AuthorizationFailedRejection)
    }

  }

  it should "rejected when method does not match" in {
    val testToken = "12345"
    val testTokenReader = mock[TokenReader]
    val testRules = Set(jsonParser.parse(
      """
        |{
        | "uri": "/test",
        | "methods": ["GET"],
        | "mediaTypes": ["text/plain"]
        |}
      """.stripMargin).getAsJsonObject)
    when(tokenParserMock.parseAndVerify(testToken)).thenReturn(testTokenReader)
    when(serviceMock.getAuthorizationRules(testToken, testAudience)).thenReturn(testRules)
    val route = {
      secure(CorbelAuth(testAudience, serviceMock, tokenParserMock)) {
        get {
          complete("done")
        }
      }
    }

    Put("/test").withHeaders(Accept(`text/plain`), Authorization(OAuth2BearerToken(testToken))) ~> route ~> check {
      rejection should be (AuthorizationFailedRejection)
    }

  }

  it should "rejected when media type does not match" in {
    val testToken = "12345"
    val testTokenReader = mock[TokenReader]
    val testRules = Set(jsonParser.parse(
      """
        |{
        | "uri": "/test",
        | "methods": ["GET"],
        | "mediaTypes": ["text/plain"]
        |}
      """.stripMargin).getAsJsonObject)
    when(tokenParserMock.parseAndVerify(testToken)).thenReturn(testTokenReader)
    when(serviceMock.getAuthorizationRules(testToken, testAudience)).thenReturn(testRules)
    val route = {
      secure(CorbelAuth(testAudience, serviceMock, tokenParserMock)) {
        get {
          complete("done")
        }
      }
    }

    Get("/test").withHeaders(Accept(`application/json`), Authorization(OAuth2BearerToken(testToken))) ~> route ~> check {
      rejection should be (AuthorizationFailedRejection)
    }

  }


  it should "rejected when token type does not match" in {
    val testToken = "12345"
    val testTokenReader = mock[TokenReader]
    val testTokenInfo = mock[TokenInfo]
    when(testTokenReader.getInfo()).thenReturn(testTokenInfo)
    val testRules = Set(jsonParser.parse(
      """
        |{
        | "uri": "/test",
        | "methods": ["GET"],
        | "mediaTypes": ["text/plain"],
        | "tokenType": "user"
        |}
      """.stripMargin).getAsJsonObject)
    when(tokenParserMock.parseAndVerify(testToken)).thenReturn(testTokenReader)
    when(serviceMock.getAuthorizationRules(testToken, testAudience)).thenReturn(testRules)
    val route = {
      secure(CorbelAuth(testAudience, serviceMock, tokenParserMock)) {
        get {
          complete("done")
        }
      }
    }

    Get("/test").withHeaders(Accept(`text/plain`), Authorization(OAuth2BearerToken(testToken))) ~> route ~> check {
      rejection should be (AuthorizationFailedRejection)
    }

  }

}
