package graffiti.corbel.auth

import com.google.gson.JsonObject
import io.corbel.lib.token.reader.TokenReader

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
case class AuthorizationInfo(tokenReader: TokenReader, accessRules: Iterable[JsonObject])
