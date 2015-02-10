package graffiti.cli

import graffiti.Context
import scopt.OptionParser

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class Cli(appName: String, appCommands: Command*) {

  def run(args: Array[String], context: Context) = {
    val parser = new OptionParser[ProgramArguments](appName) {
      for(c <- appCommands) {
        val definition = cmd(c.name) action { (_, args) => args.copy(command = Some(c)) }
        c.description.map(definition.text(_))
      }
    }
    parser.parse(args, ProgramArguments()) match {
      case Some(ProgramArguments(Some(command), params)) => command(context)
      case Some(ProgramArguments(None,_)) => {
        parser.reportError("You must specify a command")
        parser.showUsage
      }
      case None => {
        parser.showUsage
        System.exit(1)
      }
    }
  }
}

case class ProgramArguments(command:Option[Command] = None, parameters: Option[Map[String,Any]] = None)