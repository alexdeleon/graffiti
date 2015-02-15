package graffiti.example

import graffiti.BasicApplication
import graffiti.cli.Command
import net.elehack.argparse4s.ExecutionContext

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
object ExampleApp extends BasicApplication("exampleApp") {

  command("cli") {
    println("This is the cli")
  }

  command(new Command(
    name = "cli2",
    desc = Some("the second cli")) {
    val verbose = flag('v', "verbose").
      help("emit verbose output")
    def run()(implicit ec: ExecutionContext) = {
      println(verbose.get)
    }
  })

}
