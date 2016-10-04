organization in ThisBuild := "com.devialab"

scalaVersion in ThisBuild := "2.11.5"

name := "graffiti"

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases")
)

credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / ".credentials")

//publish settings
publishMavenStyle in ThisBuild  := true

publishTo in ThisBuild  := {
  val artifactory = "http://artifacts.devialab.com/artifactory/"
  if (isSnapshot.value)
    Some("snapshots" at artifactory + "devialab-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
  else
    Some("releases"  at artifactory + "devialab-release-local")
}


lazy val core = project
lazy val corbel = project.dependsOn(core)
lazy val spring = project.dependsOn(core)