organization in ThisBuild := "com.github.alexdeleon"

version in ThisBuild := "0.0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.5"

name := "graffiti"

resolvers in ThisBuild ++= Seq(
  "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/"
)

lazy val core = project
lazy val spring = project.dependsOn(core)
