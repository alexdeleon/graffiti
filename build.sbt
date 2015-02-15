organization := "com.github.alexdeleon"

name := "graffiti"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.5"

resolvers ++= Seq(
  "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/"
)

val SprayVersion = "1.3.2"
val AkkaVersion = "2.3.6"
val SpringVersion = "4.1.4.RELEASE"

libraryDependencies ++= Seq(
  "io.spray"            %%  "spray-can"     % SprayVersion,
  "io.spray"            %%  "spray-routing" % SprayVersion,
  "com.typesafe.akka"   %%  "akka-actor"    % AkkaVersion,
  "net.ceedubs"         %%  "ficus"         % "1.1.2",
  "net.elehack.argparse4s" % "argparse4s_2.10"  % "0.2.2",
  "org.clapper"         %%  "grizzled-slf4j" % "1.0.2",
  "org.springframework" %   "spring-context" % SpringVersion,
  "ch.qos.logback"      % "logback-classic" % "1.1.2",
  "org.scalatest"       %% "scalatest"      % "2.2.4"      % "test",
  "com.typesafe.akka"   %%  "akka-testkit"  % AkkaVersion  % "test",
  "io.spray"            %%  "spray-testkit" % SprayVersion % "test"
)

