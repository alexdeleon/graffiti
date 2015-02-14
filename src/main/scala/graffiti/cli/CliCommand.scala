package graffiti.cli

import net.elehack.argparse4s.{Subcommand}
import net.sourceforge.argparse4j.inf.{Subparser, Subparsers}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
abstract class CliCommand(val name: String,  desc: Option[String] = None, help: Option[String] = None) extends Subcommand {
  override def description: String = desc.getOrElse(null)

  override def addParser(sub: Subparsers): Subparser = {
    val parser = super.addParser(sub)
    help orElse desc map (parser.help(_))
    parser
  }
}
