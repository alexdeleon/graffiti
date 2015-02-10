package graffiti.example

import akka.actor.ActorSystem
import graffiti.{SpringApplication, Application}
import graffiti.ioc.{SpringInjector, Injector}
import org.springframework.context.annotation.{Bean, Configuration}

import scala.reflect.ClassTag

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
object ExampleApp extends SpringApplication[ExampleAppIoc]("exampleApp") {
}

@Configuration
class ExampleAppIoc {
  @Bean def system() = ActorSystem.create("exampleApp")
}
