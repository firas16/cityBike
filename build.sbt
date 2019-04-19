name := "cityBike"

version := "1.1"

scalaVersion := "2.11.8"


val sparkVersion = "2.3.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5"
libraryDependencies ++= Seq(
  "io.argonaut" %% "argonaut" % "6.2.2"
)

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"