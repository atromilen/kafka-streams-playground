# kafka-streams-playground
> Source code repository that has intended to put in practice Kafka and Kafka Streams knowledge.
> 
> Every commit will represent and increment or new functionality, such as:
>
> - schema-registry plugins configuration
> - a new kafka producer
> - a new kafka consumer
> - a new kafka stream consumer
> - and so on...<br />
> 
> 
> _Developed by [atromilen](https://github.com/atromilen)_

## Prerequisites
1. Java (jdk11) is the base language, so it's required to be installed in order to execute the application. 
2. [Docker](https://docs.docker.com/get-docker/) that allow us to start all the Kafka ecosystem registered in file `docker-compose`.
3. [Make](https://linuxhint.com/install-make-ubuntu/) is required to run the makefile that builds and executes the application.
This may be built into some Unix based OS or can be installed through package managers such as apt-get or be part of development tools like Xcode in mac osx. 
In Windows OS you can install it through some package manager like Scoop or Chocolatey.

## Getting Started
Enter the next command in a terminal to run the project:

```bash
make start-up
```

This command executes the [makefile](makefile) task `start-up` that automates in one single command the next tasks:
- start all Kafka's ecosystem and the Schema Registry instructed in docker-compose file.
- register in the schema-registry such subjects under `src/main/avro` that are registered in file `gradle.build`
- doing a cleaning and build of the project using gradle

## Gradle plugins and dependencies
To next will be described all the plugins and dependencies, and also the tasks registered in the file gradle.build.

### Plugins
- `com.github.imflog.kafka-schema-registry-gradle-plugin:` allow to perform tasks related to the Schema Registry, such as:
  - registry schemas
  - download schemas
  - test schemas compatibility
  - configure registered subjects

    Plugin configuration can be putted in the entry `schemaRegistry { ... }` and the documentation and DSL examples can be found [here](https://github.com/ImFlog/schema-registry-plugin).

- `com.github.davidmc24.gradle.plugin.avro-base:` Plugin that allow to generate Java code starting from AVRO schemas (src/main/avro). 
Using plugin `avro-base` instead avro, allows us to customize the output destination for the generated classes (`src/main/java/{package_from_namespace}` in our case).<br/>

    Documentation about the plugin can be found [here](https://github.com/davidmc24/gradle-avro-plugin).
    NOTE: this plugin needs to be accompanied by the dependency `implementation "org.apache.avro:avro:1.11.0"`

### Dependencies
- `org.apache.kafka:kafka-clients:` needed for get access to Kafka client configs (ProducerConfig and ConsumerConfig).
- `io.confluent:kafka-avro-serializer:7.0.1:` provide the AVRO (De)Serializers
