package graffiti.cli

import graffiti.Context
import net.elehack.argparse4s.ExecutionContext
import org.mockito.Mockito._
import org.mockito.{ Matchers => MockitoMatchers }
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, FlatSpec}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class CliTest extends FlatSpec with Matchers with MockitoSugar {

  val appName = "test"
  val context = mock[Context]

  "Cli" should
    "configure application name" in {
      val cli = Cli(appName, context)
      cli.name should be (appName)
    }
    it should "run command" in {
      val subcomand = spy(new EmptyCommand("sc"))
      Cli(appName, context, subcomand).run(Array("sc"))
      verify(subcomand).run()(MockitoMatchers.any(classOf[ExecutionContext]))
    }
}


