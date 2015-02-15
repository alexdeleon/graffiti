package graffiti.cli

import net.elehack.argparse4s.ExecutionContext

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
private[cli] class EmptyCommand(name: String, desc: Option[String] = None) extends Command(name, desc) {
  override def run()(implicit exc: ExecutionContext): Unit = {}
}
