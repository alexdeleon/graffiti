package graffiti.cli

import graffiti.Context
import grizzled.slf4j.Logging

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
abstract class Command(val name: String) extends (Context => Unit) with Logging{

  val description: Option[String] = None
}
