import sbt.Project.projectToRef

lazy val clients = Seq(frontend)
lazy val scalaV = "2.11.7"

libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.5.1" % "test" cross CrossVersion.full

lazy val backend = (project in file("backend")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd, gzip),
  resolvers ++= Seq(
    "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  ),
  libraryDependencies ++= Seq(
    "com.vmunier" %% "play-scalajs-scripts" % "0.3.0",
    "org.webjars" %% "webjars-play" % "2.4.0-1",
    ws,
    specs2 % Test,
    "com.lihaoyi" %% "scalatags" % "0.5.3",
    "com.lihaoyi" %% "upickle" % "0.3.6",
    "com.lihaoyi" %% "pprint" % "0.3.6",
    "com.github.japgolly.scalacss" %% "core" % "0.3.1",
    "com.github.japgolly.scalacss" %%% "ext-scalatags" % "0.3.1",
    "com.typesafe.slick" %% "slick" % "3.0.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.google.identitytoolkit" % "gitkitclient" % "1.2.3",
    "org.xerial" % "sqlite-jdbc" % "3.7.2",
    "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0",
    "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.0",
    "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.0"
  ),
  // Heroku specific
  herokuAppName in Compile := "your-heroku-app-name",
  herokuSkipSubProjects in Compile := false
).enablePlugins(PlayScala).
  aggregate(clients.map(projectToRef): _*).
  dependsOn(sharedJvm)

lazy val frontend = (project in file("frontend")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  sourceMapsDirectories += sharedJs.base / "..",
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.2",
    "com.lihaoyi" %%% "scalatags" % "0.5.3",
    "com.lihaoyi" %%% "upickle" % "0.3.6",
    "com.lihaoyi" %%% "pprint" % "0.3.6"
  ),
  jsDependencies := Seq(
    "org.webjars" % "react" % "0.13.3" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
    "org.webjars" % "jquery" % "2.1.4" / "jquery/2.1.4/jquery.js" minified "jquery/2.1.4/jquery.min.js" commonJSName "jQuery"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSPlay).
  jsSettings(sourceMapsBase := baseDirectory.value / "..")

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project backend", _: State)) compose (onLoad in Global).value

// for Eclipse users
EclipseKeys.skipParents in ThisBuild := false

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

version in ThisBuild := "0.2.7"
