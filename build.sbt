/*
 * Copyright (c) 2013-2022 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0, and
 * you may not use this file except in compliance with the Apache License
 * Version 2.0.  You may obtain a copy of the Apache License Version 2.0 at
 * http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Apache License Version 2.0 is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the Apache License Version 2.0 for the specific language
 * governing permissions and limitations there under.
 */
import com.typesafe.sbt.packager.docker._
import sbtbuildinfo.BuildInfoPlugin.autoImport.buildInfoPackage

lazy val commonDependencies = Seq(
  // Java
  Dependencies.Libraries.thrift,
  Dependencies.Libraries.jodaTime,
  Dependencies.Libraries.slf4j,
  Dependencies.Libraries.log4jOverSlf4j,
  Dependencies.Libraries.config,
  Dependencies.Libraries.commonsLang3,
  Dependencies.Libraries.httpclient5,
  // Scala
  Dependencies.Libraries.scopt,
  Dependencies.Libraries.pekkoStream,
  Dependencies.Libraries.pekkoHttp,
  Dependencies.Libraries.pekkoSlf4j,
  Dependencies.Libraries.pekkoHttpMetrics,
  Dependencies.Libraries.jnrUnixsocket,
  Dependencies.Libraries.badRows,
  Dependencies.Libraries.collectorPayload,
  Dependencies.Libraries.pureconfig,
  Dependencies.Libraries.trackerCore,
  Dependencies.Libraries.trackerEmitterId,
  Dependencies.Libraries.circeParser,
  // Unit tests
  Dependencies.Libraries.pekkoTestkit,
  Dependencies.Libraries.pekkoHttpTestkit,
  Dependencies.Libraries.pekkoStreamTestkit,
  Dependencies.Libraries.specs2
)

lazy val commonExclusions = Seq(
  "org.apache.tomcat.embed" % "tomcat-embed-core", // exclude for security vulnerabilities introduced by libthrift
  // Avoid duplicate .proto files brought in by akka/pekko and google-cloud-pubsub.
  // We don't need any akka serializers because collector runs in a single JVM.
  "com.typesafe.akka" % "akka-protobuf-v3_2.12",
  "org.apache.pekko" %% "pekko-protobuf-v3"
)

lazy val buildInfoSettings = Seq(
  buildInfoPackage := "com.snowplowanalytics.snowplow.collectors.scalastream.generated",
  buildInfoKeys := Seq[BuildInfoKey](organization, moduleName, name, version, "shortName" -> "osc", scalaVersion)
)

lazy val buildSettings = Seq(
  organization := "com.snowplowanalytics",
  name := "opensnowcat-collector",
  description := "Scala Stream Collector for Snowplow raw events",
  scalaVersion := "2.12.19",
  javacOptions := Seq("-source", "11", "-target", "11"),
  resolvers ++= Dependencies.resolutionRepos
)

lazy val dynVerSettings = Seq(
  ThisBuild / dynverVTagPrefix := false, // Otherwise git tags required to have v-prefix
  ThisBuild / dynverSeparator := "-" // to be compatible with docker
)

lazy val dockerSettings = Seq(
  // Override sbt-snowplow-release's default (eclipse-temurin:11-jre-focal):
  // Ubuntu 20.04 (focal) left standard support in April 2025
  dockerBaseImage := "eclipse-temurin:21-jre-noble"
)

lazy val allSettings = buildSettings ++
  BuildSettings.sbtAssemblySettings ++
  BuildSettings.formatting ++
  Seq(excludeDependencies ++= commonExclusions) ++
  dynVerSettings ++
  dockerSettings ++
  BuildSettings.addExampleConfToTestCp

lazy val root = project
  .in(file("."))
  .settings(buildSettings ++ dynVerSettings)
  .aggregate(core, kafka, stdout, rabbitmq)

lazy val core = project
  .settings(moduleName := "opensnowcat-collector-core")
  .settings(buildSettings ++ BuildSettings.sbtAssemblySettings)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(excludeDependencies ++= commonExclusions)

lazy val kafkaSettings =
  allSettings ++ buildInfoSettings ++ Seq(
    moduleName := "opensnowcat-collector-kafka",
    Docker / packageName := "scala-stream-collector-kafka",
    libraryDependencies ++= Seq(
      Dependencies.Libraries.kafkaClients,
      Dependencies.Libraries.lz4Java,
      Dependencies.Libraries.mskAuth
    )
  )

lazy val kafka = project
  .settings(kafkaSettings)
  .enablePlugins(JavaAppPackaging, SnowplowDockerPlugin, BuildInfoPlugin)
  .dependsOn(core % "test->test;compile->compile")

lazy val stdoutSettings =
  allSettings ++ buildInfoSettings ++ Seq(
    moduleName := "opensnowcat-collector-stdout",
    Docker / packageName := "scala-stream-collector-stdout"
  )

lazy val stdout = project
  .settings(stdoutSettings)
  .enablePlugins(JavaAppPackaging, SnowplowDockerPlugin, BuildInfoPlugin)
  .dependsOn(core % "test->test;compile->compile")

lazy val rabbitmqSettings =
  allSettings ++ buildInfoSettings ++ Seq(
    moduleName := "opensnowcat-collector-rabbitmq",
    Docker / packageName := "scala-stream-collector-rabbitmq-experimental",
    libraryDependencies ++= Seq(Dependencies.Libraries.rabbitMQ)
  )

lazy val rabbitmq = project
  .settings(rabbitmqSettings)
  .enablePlugins(JavaAppPackaging, SnowplowDockerPlugin, BuildInfoPlugin)
  .dependsOn(core % "test->test;compile->compile")
