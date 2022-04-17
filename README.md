# kafka-streams-poc
> This project has **Java** as base language and is built with **Gradle**. The project execution is through **Docker Compose**.<br />
This repository is intended to follow the examples in the O'reilly book Kafka _"Streams in Action, 2nd Edition (MEAP)"_

### Required software (pre-requisites)
1. [Docker](https://docs.docker.com/get-docker/) to be able to run the Kafka ecosystem container. 
2. For Schema Registry testing using the REST API in terminal, you will need to install:
   1. [curl](https://curl.se/) to execute request to REST API for Schema Registry. It should be installed 
   on Windows 10+ and Mac (in Linux, you can use a package manager to install it).
   2. [JQ](https://stedolan.github.io/jq/) that is a JSON command-line processor used in the example commands.

### Project execution
In order to start the Kafka Broker and its ecosystem, open a new terminal in the root project folder and run the next command:

```bash
docker-compose up -d
```

This will start Zookeeper, Kafka and the Schema Registry in background (detached mode).

## About Gradle configuration
All the plugins, dependencies and tasks registered in the gradle.build will be detailed in here for documentation purposes.

### Plugins
- `com.github.imflog.kafka-schema-registry-gradle-plugin:` allow to perform tasks related to the Schema Registry, such as:
  - registry schemas
  - download schemas
  - test schemas compatibility
  - configure registered subjects

    Plugin configuration can be putted in the entry `schemaRegistry { ... }` and the documentation and DSL examples can be found [here](https://github.com/ImFlog/schema-registry-plugin).

- `com.github.davidmc24.gradle.plugin.avro:` Plugin that allow to generate Java code starting from AVRO schemas (src/main/avro/). The output for the generated classes is `build/generated-main-avro-java/{package_from_namespace}` and we can copy this auto-generated code and paste in our project.<br/>

    Documentation about the plugin can be found [here](https://github.com/davidmc24/gradle-avro-plugin).
    NOTE: this plugin needs to be accompanied by the dependency `implementation "org.apache.avro:avro:1.11.0"`
    
## Command line tests
It's possible interact with the Kafka ecosystem using the terminal. Some examples will be detailed in the following sections.

### Testing the broker: Create a topic, produce message and send them to a Kafka topic and consume these using terminal
1. Open a new terminal window and run the **docker-compose exec broker bash** command to get a shell on the broker container 
2. Once the container shell is opened, execute the next command in order to create a new topic:
```bash
kafka-topics --create --topic first-topic\
 --bootstrap-server localhost:9092\
 --replication-factor 1\
 --partitions 1
```
3. In the same shell where you created the topic, start a new kafka producer using the next command:
```bash
kafka-console-producer --topic first-topic\
 --broker-list localhost:9092\
 --property parse.key=true\
 --property key.separator=":"
```
4. In the same window, send 3 new messages to the first-topic (Note: it's necessary use the __colon__ to differentiate the keys and values for each record)
```bash
key:my first message
key:is something
key:very simple
```
5. Now it's time to consume the recently created messages in first-topic. Open a new terminal and start a new container bash with the command **docker-compose exec broker bash**. Now
Once the container shell is opened, insert the next command:
```bash
kafka-console-consumer --topic first-topic\
 --bootstrap-server localhost:9092\
 --from-beginning\
 --property print.key=true\
 --property key.separator="-"
```
6. You should see the next output in the console

```
key-my first message
key-is something
key-very simple
```

### Testing the Schema Registry through the REST API
1. In order to add an AVRO schema to the Schema Registry, run the next command:
```bash
jq '. | {schema: tojson}' src/main/avro/avenger.avsc | \
curl -s -X POST http://localhost:8081/subjects/avro-avengers-value/versions\
-H "Content-Type: application/vnd.schemaregistry.v1+json" \
-d @-  \
| jq
```

2. Now you can list all the subjects of registered schemas in the Schema Registry:
```bash
curl -s "http://localhost:8081/subjects" | jq
```

3. Try to get versions history for a given schema (avro-avengers)
```bash
curl -s "http://localhost:8081/subjects/avro-avengers-value/versions" | jq
```

4. Now is time to get the definition for a specific version of a schema
```bash
curl -s "http://localhost:8081/subjects/avro-avengers-value/versions/1"\
| jq '.'
```

5. If you don't care about the previous versions of the schema, and you want to see the latest version, you can execute:
```bash
curl -s "http://localhost:8081/subjects/avro-avengers-value/
  versions/latest" | jq '.'
```
