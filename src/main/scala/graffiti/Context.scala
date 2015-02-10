package graffiti

import com.typesafe.config.Config
import graffiti.boilerplate.BoilerplateService
import graffiti.ioc.Injector

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
case class Context(config: Config, injector: Injector, service: Service = BoilerplateService()) {
  def withConfig(c: Config) = copy(config=c)
  def withInjector(i: Injector) = copy(injector=i)
  def withService(s: Service) = copy(service=s)
}