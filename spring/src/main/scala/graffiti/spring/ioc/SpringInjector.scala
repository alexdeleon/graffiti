package graffiti.spring.ioc

import com.typesafe.config.Config
import graffiti.ioc.Injector
import org.springframework.beans.factory.config.BeanDefinitionHolder
import org.springframework.context.annotation.{Bean, Configuration, AnnotationConfigApplicationContext}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.env.{AbstractEnvironment, ConfigurableEnvironment, MutablePropertySources, PropertySource}

import scala.collection.JavaConversions._
import scala.reflect.{ClassTag, classTag}

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
class SpringInjector[C: ClassTag](config: Config) extends Injector {
  import SpringInjector.Implicits._

  private val springContext = new AnnotationConfigApplicationContext()
  springContext.setEnvironment(config)
  springContext.register(classOf[PropertySourcesPlaceholderConfiguration])
  springContext.register(classTag[C].runtimeClass)
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
          if (config.hasPath(name)) config.getAnyRef(name) else null
      }

    implicit def configToEnvironment(config: Config): ConfigurableEnvironment =
      new AbstractEnvironment {
        override def customizePropertySources(propertySources: MutablePropertySources): Unit = {
          propertySources.addLast(config)
        }
      }
  }
}

@Configuration
class PropertySourcesPlaceholderConfiguration {
  @Bean
  def myPropertySourcesPlaceholderConfigurer : PropertySourcesPlaceholderConfigurer = {
    val placeholderConfigurer = new PropertySourcesPlaceholderConfigurer()
    placeholderConfigurer.setNullValue("@null")
    placeholderConfigurer.setLocalOverride(true)
    placeholderConfigurer
  }
}
