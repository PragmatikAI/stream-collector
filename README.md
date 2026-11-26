# Scala Stream Collector (Pragmatik fork)
[![License][license-image]][license]

## Welcome

A cloud-agnostic Snowplow event collector, maintained as a downstream fork of the
[OpenSnowcat Collector](https://github.com/opensnowcat/opensnowcat-collector) (itself the
Apache 2.0 continuation of the Snowplow Scala Stream Collector following the 2023/2024
license changes).

This fork is deliberately opinionated:

- **Sinks**: [Apache Kafka](https://kafka.apache.org/) (primary), [RabbitMQ](https://www.rabbitmq.com/) (experimental), and stdout. The AWS (Kinesis, SQS), GCP (Pub/Sub), and NSQ sinks are removed, as is the Kafka sink's optional SQS failover.
- **Telemetry is off by default** (`telemetry.disable = true`); set `disable = false` to opt in.
- Docker images are published to `pragmatikai/scala-stream-collector-{kafka,stdout,rabbitmq-experimental}`.

To pull in upstream security updates: `git fetch opensnowcat && git merge opensnowcat/main`,
then re-resolve the strip-down (conflicts in removed modules are always resolved by deletion).

## Security

See [SECURITY.md](SECURITY.md). Please do not create a public GitHub issue for
potential security problems.


## License

This project is licensed under the [Apache License, Version 2.0][license] (the "License"); you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Copyright
Copyright OpenSnowcat Contributors. See [NOTICE](NOTICE.txt) for details.


## Trademark

OpenSnowcat includes certain Apache-licensed Snowplow code from Snowplow Ltd. and other source code. Snowplow Ltd. is not the source of that other source code. SNOWPLOW is a registered trademark of Snowplow Ltd.


[license-image]: http://img.shields.io/badge/license-Apache--2-blue.svg?style=flat
[license]: http://www.apache.org/licenses/LICENSE-2.0
