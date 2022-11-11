inThisBuild(
  List(
    organization := "io.github.richarddowsett",
    homepage := Some(url("https://github.com/richarddowsett/sbt-codeartifact")),
    licenses := Seq("MIT" -> url("https://choosealicense.com/licenses/mit/")),
    developers := List(
      Developer(
        "richarddowsett",
        "Richard Dowsett","a@b.com",
        url("https://github.com/richarddowsett")
      )
    )
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

lazy val `sbt-codeartifact` = project
  .in(file("sbt-codeartifact"))
  .dependsOn(core)
  .settings(testSettings)

lazy val root = project
  .in(file("."))
  .aggregate(core, `sbt-codeartifact`)
  .settings(
    publish / skip := true
  )
