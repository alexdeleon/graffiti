package graffiti

import akka.actor.ActorSystem
import graffiti.boilerplate.BoilerplateService
import graffiti.ioc.Injector

import scala.reflect.{ClassTag, classTag}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class BasicApplication(name: String) extends Application(name) {

  def system: ActorSystem = ActorSystem(name)
  def service: Service = BoilerplateService()

  override def createInjector: Injector = BasicInjector(this)

}

object BasicInjector {
  def apply(app: BasicApplication) = new Injector {

    override def getInstance[T: ClassTag]: T = classTag[T].runtimeClass match {
      case _:Class[ActorSystem] => app.system.asInstanceOf[T]
      case _:Class[Service] => app.service.asInstanceOf[T]
      case _ => throw new IllegalArgumentException("Basic injector only supports injection of ActorSystem and Services")
    }

    override def getInstance[T: ClassTag](name: String): T = throw new UnsupportedOperationException

    override def getInstances[T: ClassTag]: Seq[T] = throw new UnsupportedOperationException

  }
}