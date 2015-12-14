package graffiti.banner

import java.io._


import org.apache.commons.io.IOUtils

import scala.Option

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
object Banner {

  def print(writer: PrintWriter, classLoader: ClassLoader = ClassLoader.getSystemClassLoader): Unit = {
    bannerFromClassPath(classLoader).foreach(printTo(writer))
  }

  def bannerFromClassPath(classLoader: ClassLoader) : Option[InputStream] =
    Option(classLoader.getResourceAsStream(bannerFilePath))

  private def printTo(writer: Writer) = (in: InputStream) => IOUtils.copy(in, writer)

  private val bannerFilePath = "/banner.txt"

}
