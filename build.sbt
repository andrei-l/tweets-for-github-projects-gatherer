import sbt.Keys._

val springBootStarterVersion = "1.3.3.RELEASE"
val jacksonVersion = "2.6.3"
val springSecurityVersion = "2.0.9.RELEASE"
val springVersion = "4.2.5.RELEASE"


val springBootStarterDependency = "org.springframework.boot" % "spring-boot-starter" % springBootStarterVersion
val springWebDependency = "org.springframework" % "spring-web" % springVersion

val scalaJacksonDependency = "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion

val scalaTestDependency = "org.scalatest" %% "scalatest" % "2.2.4" % Test
val mockitoDependency = "org.mockito" % "mockito-core" % "1.10.19" % Test
val hamcrestMatcherDependency = "org.hamcrest" % "hamcrest-core" % "1.3" % Test


scalaVersion := "2.11.8"

name := "tweets-for-github-projects-gatherer"
version := "1.0"
organization := "com.github.tweets"

mappings in(Compile, packageBin) ~= {
  _.filter(_._1.getName != "application.properties")
}
oneJarSettings

libraryDependencies ++= Seq(
  springWebDependency,
  springBootStarterDependency,
  scalaJacksonDependency,

  scalaTestDependency,
  mockitoDependency,
  hamcrestMatcherDependency
)




