package graffiti.example

import akka.actor.ActorSystem
import graffiti.cli.Command
import graffiti.{BasicApplication, Context, SpringApplication}
import net.elehack.argparse4s.ExecutionContext
import org.springframework.context.annotation.{Bean, Configuration}

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
