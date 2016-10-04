organization in ThisBuild := "com.devialab"

scalaVersion in ThisBuild := "2.11.5"

name := "graffiti"

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases")
)

credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo in ThisBuild := Some("Artifactory Realm" at "http://artifacts.devialab.com/artifactory/devialab-snapshot-local;build.timestamp=" + new java.util.Date().getTime)

publishMavenStyle in ThisBuild := true


lazy val core = project
lazy val corbel = project.dependsOn(core)
lazy val spring = project.dependsOn(core)