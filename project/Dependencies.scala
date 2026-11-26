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
import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "Snowplow Analytics Maven repo".at("http://maven.snplow.com/releases/").withAllowInsecureProtocol(true),
    // For uaParser utils
    "user-agent-parser repo".at("https://clojars.org/repo/")
  )

  object V {
    // Java
    val kafka       = "3.9.2"
    val lz4Java     = "1.11.4"
    val mskAuth     = "2.2.0"
    val jodaTime    = "2.12.7"
    val slf4j       = "2.0.16"
    val config      = "1.4.3"
    val rabbitMQ    = "5.35.0"
    val thrift      = "0.24.0"
    val jnrUnixsock = "0.38.22"
    val commonsLang = "3.18.0"
    val httpclient5 = "5.6.5"

    // Scala
    val collectorPayload = "0.0.0"
    val tracker          = "1.0.1"
    val scopt            = "4.1.0"
    val pureconfig       = "0.17.7"
    val badRows          = "2.2.1"
    val circeParser      = "0.14.17"
    val pekko            = "1.0.3"
    val pekkoHttp        = "1.0.1"
    val pekkoHttpMetrics = "1.0.1"


    // Scala (test only)
    val specs2         = "4.20.8"
  }

  object Libraries {
    // Java
    val thrift  = "org.apache.thrift"          % "libthrift"            % V.thrift
    val kafkaClients = ("org.apache.kafka"  % "kafka-clients"         % V.kafka)
      .exclude("org.lz4", "lz4-java")
    val lz4Java = "at.yawk.lz4"             % "lz4-java"              % V.lz4Java
    val mskAuth =
      "software.amazon.msk" % "aws-msk-iam-auth" % V.mskAuth % Runtime // Enables AWS MSK IAM authentication https://github.com/snowplow/stream-collector/pull/214

    val jodaTime       = "joda-time"                % "joda-time"        % V.jodaTime
    val slf4j          = "org.slf4j"                % "slf4j-simple"     % V.slf4j
    val log4jOverSlf4j = "org.slf4j"                % "log4j-over-slf4j" % V.slf4j
    val config         = "com.typesafe"             % "config"           % V.config
    val jnrUnixsocket  = "com.github.jnr"           % "jnr-unixsocket"   % V.jnrUnixsock
    val rabbitMQ       = "com.rabbitmq"             % "amqp-client"      % V.rabbitMQ
    val commonsLang3   = "org.apache.commons"       % "commons-lang3"    % V.commonsLang
    val httpclient5    = "org.apache.httpcomponents.client5" % "httpclient5" % V.httpclient5

    // Scala
    val collectorPayload = "com.snowplowanalytics"  % "collector-payload-1"               % V.collectorPayload
    val badRows          = "com.snowplowanalytics" %% "snowplow-badrows"                  % V.badRows
    val trackerCore      = "com.snowplowanalytics" %% "snowplow-scala-tracker-core"       % V.tracker
    val trackerEmitterId = "com.snowplowanalytics" %% "snowplow-scala-tracker-emitter-id" % V.tracker
    val scopt            = "com.github.scopt"      %% "scopt"                             % V.scopt
    val pureconfig       = "com.github.pureconfig" %% "pureconfig"                        % V.pureconfig
    val circeParser      = "io.circe"              %% "circe-parser"                      % V.circeParser
    val pekkoHttp        = "org.apache.pekko"      %% "pekko-http"                        % V.pekkoHttp
    val pekkoStream      = "org.apache.pekko"      %% "pekko-stream"                      % V.pekko
    val pekkoSlf4j       = "org.apache.pekko"      %% "pekko-slf4j"                       % V.pekko
    val pekkoHttpMetrics = "fr.davit"              %% "pekko-http-metrics-datadog"        % V.pekkoHttpMetrics


    // Scala (test only)
    val specs2             = "org.specs2"        %% "specs2-core"                % V.specs2         % Test
    val pekkoTestkit       = "org.apache.pekko"  %% "pekko-testkit"              % V.pekko          % Test
    val pekkoHttpTestkit   = "org.apache.pekko"  %% "pekko-http-testkit"         % V.pekkoHttp      % Test
    val pekkoStreamTestkit = "org.apache.pekko" %% "pekko-stream-testkit"        % V.pekko          % Test
  }
}
