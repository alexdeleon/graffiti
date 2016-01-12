package graffiti.cli

import net.elehack.argparse4s.{ExecutionContext, MasterCommand, Subcommand}
import net.sourceforge.argparse4j.inf.ArgumentParserException

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
object Cli{

  def apply(appName: String, appCommands: Command*) = new MasterCommand {

    override def subcommands: Seq[Subcommand] = appCommands

    override def name: String = appName

    override def run()(implicit exc: ExecutionContext): Unit = {
      subcommand match {
        case Some(cmd) => cmd.run()
        case None => printHelpAndExit()
      }
    }

    override def run(args: Seq[String]): Unit = {
      try {
        super.run(args)
      } catch {
        case e: ArgumentParserException => {
          println(e.getMessage)
          printHelpAndExit()
        }
      }
    }

    private def printHelpAndExit() = {
      parser.printHelp()
      System.exit(5)
    }
  }
}