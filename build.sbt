enablePlugins(SbtPlugin)

inThisBuild(
  List(
    organization := "io.github.richarddowsett",
    homepage := Some(url("https://github.com/richarddowsett/sbt-codeartifact")),
    licenses := Seq("MIT" -> url("https://choosealicense.com/licenses/mit/")),
    developers := List(
      Developer(
        "richarddowsett",
        "Richard Dowsett",
        "richarddowsett@gmail.com",
        url("https://github.com/richarddowsett")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/richarddowsett/sbt-codeartifact/"),
        "scm:https://github.com/richarddowsett/sbt-codeartifact.git"
      )
    ),
    pomIncludeRepository := { _ => false },
    publishMavenStyle := true,
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")
  )
)

lazy val `sbt-codeartifact` = project
  .in(file("."))
  .settings(publishTo := {
    // For accounts created after Feb 2021:
    // val nexus = "https://s01.oss.sonatype.org/"
    val nexus = "https://s01.oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots".at(nexus + "content/repositories/snapshots"))
    else Some("releases".at(nexus + "service/local/staging/deploy/maven2"))
  })
  .settings {
    addCommandAlias(
      name = "validate",
      value =
        "clean; scalafmtCheckAll; scalafmtSbtCheck; undeclaredCompileDependenciesTest; unusedCompileDependenciesTest; test:test"
    )
  }
  .settings(
    libraryDependencies ++= Seq(
      "software.amazon.awssdk" % "codeartifact" % "2.17.103",
      "com.lihaoyi" %% "requests" % "0.6.9",
      "com.lihaoyi" %% "os-lib" % "0.7.8",
      "com.lihaoyi" %% "geny" % "0.6.10",
      "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
      "org.scala-sbt" %% "collections" % "1.6.1",
      "org.scala-sbt" %% "core-macros" % "1.6.1",
      "org.scala-sbt" %% "librarymanagement-core" % "1.6.1",
      "org.scala-sbt" %% "librarymanagement-ivy" % "1.6.1",
      "org.scala-sbt" %% "io" % "1.6.0",
      "org.scala-sbt" %% "main" % "1.6.1",
      "org.scala-sbt" %% "main-settings" % "1.6.1",
      "org.scala-sbt" % "sbt" % "1.6.1",
      "org.scala-sbt" %% "task-system" % "1.6.1",
      "org.scala-sbt" %% "util-logging" % "1.6.1",
      "org.scala-sbt" %% "util-position" % "1.6.1",
      "software.amazon.awssdk" % "utils" % "2.17.103",
      "com.lihaoyi" %% "utest" % "0.7.10" % Test
    ),
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
