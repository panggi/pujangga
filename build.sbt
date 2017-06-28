name := """pujangga"""
organization := "com.panggi"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  "org.deeplearning4j" % "deeplearning4j-core" % "0.8.0",
  "org.deeplearning4j" % "deeplearning4j-nlp" % "0.8.0",
  "org.nd4j" % "nd4j-native-platform" % "0.8.0"
)

unmanagedJars in Compile += file("libs/InaNLP.jar")
unmanagedJars in Compile += file("libs/ipostagger.jar")
