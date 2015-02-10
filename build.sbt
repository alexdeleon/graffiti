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
  "io.spray"            %%  "spray-testkit" % SprayVersion  % "test",
  "com.typesafe.akka"   %%  "akka-actor"    % AkkaVersion,
  "com.typesafe.akka"   %%  "akka-testkit"  % AkkaVersion  % "test",
  "net.ceedubs"         %%  "ficus"         % "1.1.2",
  "com.github.scopt"    %%  "scopt"         % "3.3.0",
  "org.clapper"         %% "grizzled-slf4j" % "1.0.2",
  "org.springframework" % "spring-context"  % SpringVersion
)

