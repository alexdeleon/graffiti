package graffiti.example

import akka.actor.ActorSystem
import graffiti.cli.CliCommand
import graffiti.{Context, SpringApplication}
import net.elehack.argparse4s.ExecutionContext
import org.springframework.context.annotation.{Bean, Configuration}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
object ExampleApp extends SpringApplication[ExampleAppIoc]("exampleApp") {

  command("cli") {
    println("This is the cli")
  }

  command(new CliCommand(
    name = "cli2",
    desc = Some("the second cli")) {
    val verbose = flag('v', "verbose").
      help("emit verbose output")
    def run()(implicit ec: ExecutionContext) = {
      println(verbose.get)
    }
  })

}

@Configuration
class ExampleAppIoc {
  @Bean def system() = ActorSystem.create("exampleApp")
}
