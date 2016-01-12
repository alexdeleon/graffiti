package graffiti.mixin

import graffiti.{Application, Context, Service}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
trait ServiceInjection extends Application {

  abstract override def createContext: Context = {
    val context = super.createContext
    context.withService(services(context))
  }

  def services(context: Context): Service = Service.compose(context.injector.getInstances[Service]:_*)

}
