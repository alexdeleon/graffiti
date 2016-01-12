name := "graffiti-corbel"

val SprayVersion = "1.3.2"
val AkkaVersion = "2.3.6"

libraryDependencies ++= Seq(
  "io.corbel.lib" % "token" % "0.7.0",
  "io.corbel.lib" % "ws" % "0.16.0-SNAPSHOT",
  "org.scalatest"       %% "scalatest"      % "2.2.4"      % "test",
  "com.typesafe.akka"   %%  "akka-testkit"  % AkkaVersion  % "test",
  "io.spray"            %%  "spray-testkit" % SprayVersion % "test"
)