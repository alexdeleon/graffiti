organization in ThisBuild := "com.github.alexdeleon"

version in ThisBuild := "0.0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.5"

name := "graffiti"

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  "Alexander De Leon OSS Maven Repo (Snapshots)" at "http://maven.alexdeleon.name/snapshot",
  "Alexander De Leon OSS Maven Repo (Release)" at "http://maven.alexdeleon.name/release"
)

isSnapshot := true

publishTo := Some("S3 Snapshots)" at "s3://maven.alexdeleon.name/snapshot")

lazy val root = (project in file(".")).enablePlugins(UniversalDeployPlugin)

lazy val core = project
lazy val spring = project.dependsOn(core)
