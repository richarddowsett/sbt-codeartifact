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
  .settings(
    Seq(
      scalacOptions -= "-Xfatal-warnings",
      pluginCrossBuild / sbtVersion := {
        scalaBinaryVersion.value match {
          case "2.12" => "1.2.8" // set minimum sbt version
        }
      }
    ),
    libraryDependencies ++= Seq(
      "software.amazon.awssdk" % "sso" % "2.17.103",
      "software.amazon.awssdk" % "codeartifact" % "2.17.103",
      "software.amazon.awssdk" % "sts" % "2.17.103",
      "com.lihaoyi" %% "requests" % "0.6.9",
      "com.lihaoyi" %% "os-lib" % "0.7.8",
      "com.lihaoyi" %% "utest" % "0.7.10" % Test
    ),
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
