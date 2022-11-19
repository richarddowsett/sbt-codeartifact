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

lazy val testSettings: Seq[Setting[_]] = Seq(
  scriptedLaunchOpts := {
    scriptedLaunchOpts.value ++ Seq(
      "-Xmx1024M",
      "-Dplugin.version=" + version.value
    )
  },
  scriptedBufferLog := false
)

lazy val core = project
  .in(file("core"))
  .settings(testSettings)
  .settings(
    publish / skip := true
  )

lazy val `sbt-codeartifact` = project
  .in(file("sbt-codeartifact"))
  .dependsOn(core)
  .settings(testSettings)
  .settings(publishTo := {
    // For accounts created after Feb 2021:
    // val nexus = "https://s01.oss.sonatype.org/"
    val nexus = "https://s01.oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots".at(nexus + "content/repositories/snapshots"))
    else Some("releases".at(nexus + "service/local/staging/deploy/maven2"))
  })

lazy val root = project
  .in(file("."))
  .aggregate(core, `sbt-codeartifact`)
  .settings(
    publish / skip := true
  )
