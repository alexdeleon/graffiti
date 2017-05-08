name := "graffiti-spring"

val SpringVersion = "4.3.3.RELEASE"

libraryDependencies ++= Seq(
  "org.springframework" %   "spring-context" % SpringVersion,
  "org.scalatest"       %% "scalatest" % "2.2.4" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
)