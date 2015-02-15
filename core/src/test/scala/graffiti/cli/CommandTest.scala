package graffiti.cli

import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class CommandTest extends FlatSpec with Matchers with MockitoSugar {

  val commandName = "test"

  "Command" should
    "configure its name" in {
      val command = new EmptyCommand(commandName)
      command.name should be (commandName)
      command.description should be (null)
    }
    it should "configure its description" in {
      val desc = "description command"
      val command = new EmptyCommand(commandName, Some(desc))
      command.description should be (desc)
    }
}
