organization in ThisBuild := "com.devialab"

scalaVersion in ThisBuild := "2.11.5"

name := "graffiti"

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases")
)

credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / ".credentials")

//publish settings
publishMavenStyle := true

publishTo := {
  val artifactory = "http://artifacts.devialab.com/artifactory/"
  if (isSnapshot.value)
    Some("snapshots" at artifactory + "devialab-snapshot;build.timestamp=" + new java.util.Date().getTime)
  else
    Some("releases"  at artifactory + "devialab-release")
}
publishMavenStyle in ThisBuild := true


lazy val core = project
lazy val corbel = project.dependsOn(core)
lazy val spring = project.dependsOn(core)