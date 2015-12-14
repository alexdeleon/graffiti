package graffiti

import akka.actor.ActorSystem
import graffiti.boilerplate.BoilerplateService
import graffiti.ioc.Injector
import graffiti.mixin.ServiceInjection

import scala.reflect.{ClassTag, classTag}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class BasicApplication(val name: String) extends Application(name) with ServiceInjection {

  lazy val system: ActorSystem = ActorSystem(name)

  override def createInjector: Injector = BasicInjector(this)

  def service: Service = BoilerplateService()

}

object BasicInjector {
  def apply(app: BasicApplication) = new Injector {

    override def getInstance[T: ClassTag]: T = classTag[T].runtimeClass match {
      case c if c == classOf[ActorSystem] => app.system.asInstanceOf[T]
      case c if c == classOf[Service] => app.service.asInstanceOf[T]
      case _ => throw new IllegalArgumentException("Basic injector only supports injection of ActorSystem and Services")
    }

    override def getInstance[T: ClassTag](name: String): T = throw new UnsupportedOperationException

    override def getInstances[T: ClassTag]: Seq[T] = Option(getInstance[T]).map(Seq(_)).orNull

  }
}