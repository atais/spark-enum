name := "spark-enum"

version := "0.1"

scalaVersion := "2.12.11"

//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.5"
libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % "2.4.5_0.14.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % Test
