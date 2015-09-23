name := "graffiti-core"

val SprayVersion = "1.3.2"
val AkkaVersion = "2.3.6"

libraryDependencies ++= Seq(
  "io.spray"            %%  "spray-can"     % SprayVersion,
  "io.spray"            %%  "spray-routing" % SprayVersion,
  "com.typesafe.akka"   %%  "akka-actor"    % AkkaVersion,
  "net.ceedubs"         %%  "ficus"         % "1.1.2",
  "net.elehack.argparse4s" % "argparse4s_2.10"  % "0.2.2",
  "org.clapper"         %%  "grizzled-slf4j" % "1.0.2",
  "ch.qos.logback"      %  "logback-classic" % "1.1.2",
  "commons-io"           %  "commons-io"      % "1.3.2",
  "org.scalatest"       %% "scalatest"      % "2.2.4"      % "test",
  "com.typesafe.akka"   %%  "akka-testkit"  % AkkaVersion  % "test",
  "io.spray"            %%  "spray-testkit" % SprayVersion % "test"
)