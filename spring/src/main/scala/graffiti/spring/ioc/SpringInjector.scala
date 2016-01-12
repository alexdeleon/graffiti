package graffiti.spring.ioc

import com.typesafe.config.Config
import graffiti.ioc.Injector
import org.springframework.beans.factory.config.BeanDefinitionHolder
import org.springframework.context.annotation.{Bean, Configuration, AnnotationConfigApplicationContext}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.env.{AbstractEnvironment, ConfigurableEnvironment, MutablePropertySources, PropertySource}

import scala.collection.JavaConversions._
import scala.reflect.{ClassTag, classTag}
import scala.util.{Success, Try}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class SpringInjector[C: ClassTag](config: Config) extends Injector {
  import SpringInjector.Implicits._

  private val springContext = new AnnotationConfigApplicationContext()
  springContext.register(classOf[PropertySourcesPlaceholderConfiguration])
  springContext.register(classTag[C].runtimeClass)
  springContext.getEnvironment.getPropertySources.addFirst(config)
  springContext.refresh()

  override def getInstance[T: ClassTag]: T =
    springContext.getBean(classTag[T].runtimeClass.asInstanceOf[Class[T]])

  override def getInstance[T: ClassTag](name: String): T =
    springContext.getBean(name, classTag[T].runtimeClass.asInstanceOf[Class[T]])

  override def getInstances[T: ClassTag]: Seq[T] =
    springContext.getBeansOfType(classTag[T].runtimeClass.asInstanceOf[Class[T]]).values().toSeq
}

object SpringInjector {

  object Implicits
  {
    implicit def configToPropertySource(config: Config): PropertySource[Config] =
      new PropertySource("Graffiti application config", config) {
        override def getProperty(name: String): AnyRef =
          Try(config.hasPath(name)) match {
            case Success(true) => config.getAnyRef (name)
            case _ => null
          }
      }
  }
}

@Configuration
class PropertySourcesPlaceholderConfiguration
object PropertySourcesPlaceholderConfiguration {
  @Bean
  def myPropertySourcesPlaceholderConfigurer : PropertySourcesPlaceholderConfigurer = {
    val placeholderConfigurer = new PropertySourcesPlaceholderConfigurer()
    placeholderConfigurer.setNullValue("@null")
    placeholderConfigurer.setLocalOverride(true)
    placeholderConfigurer
  }
}
