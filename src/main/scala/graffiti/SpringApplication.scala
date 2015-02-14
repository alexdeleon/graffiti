package graffiti

import graffiti.ioc.{Injector, SpringInjector}

import scala.reflect.ClassTag

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class SpringApplication[C: ClassTag](name: String) extends Application(name) {

  override lazy val createInjector: Injector = new SpringInjector[C](config)

}
