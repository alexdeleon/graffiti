package graffiti.ioc

import scala.reflect.ClassTag

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
trait Injector {
  def getInstance[T: ClassTag]: T
  def getInstance[T: ClassTag](name: String): T
  def getInstances[T: ClassTag]: Seq[T]
}
