package graffiti.spring.ioc

import com.typesafe.config.{ConfigFactory, Config}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, FlatSpec}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}
import scala.collection.JavaConversions._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class SpringInjectorTest extends FlatSpec with Matchers {

  "SpringInjector" should "read properties from Config" in {
    val configMock = ConfigFactory.parseMap(Map("test.value" -> "test1"))

    val injector = new SpringInjector[TestIoC](configMock)

    val bean = injector.getInstance[TestObject]
    bean.value should be ("test1")

  }
  it should "read default null value" in {
    val configMock = ConfigFactory.parseMap(Map.empty[String, Object])

    val injector = new SpringInjector[TestIoC](configMock)

    val bean = injector.getInstance[TestObject]
    bean.value should be (null)

  }

}

@Configuration
class TestIoC {
  @Bean def testObject(@Value("${test.value:@null}") value: String) = TestObject(value)
}

case class TestObject(value: String)
