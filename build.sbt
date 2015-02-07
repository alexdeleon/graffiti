organization := "com.github.alexdeleon"

name := "graffiti"

version := "1.0"

scalaVersion := "2.11.5"

val SprayVersion = "1.3.2"
val AkkaVersion = "2.3.6"

libraryDependencies ++= Seq(
  "io.spray"            %%  "spray-can"     % SprayVersion,
  "io.spray"            %%  "spray-routing" % SprayVersion,
  "io.spray"            %%  "spray-testkit" % SprayVersion  % "test",
  "com.typesafe.akka"   %%  "akka-actor"    % AkkaVersion,
  "com.typesafe.akka"   %%  "akka-testkit"  % AkkaVersion  % "test"
)

