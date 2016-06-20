name := "finance"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
   "org.apache.httpcomponents" % "httpclient" % "4.5.2",
   "org.json" % "json" % "20160212"
)

play.Project.playJavaSettings
