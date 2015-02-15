package graffiti.boilerplate

import graffiti.Service

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class BoilerplateService extends Service {

  route{
    complete("Welcome to Graffiti")
  }

}
object BoilerplateService {
  def apply() = new BoilerplateService
}