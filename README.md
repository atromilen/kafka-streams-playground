# kafka-streams-poc
>Repo to follow code related to book "Kafka Streams in Action, 2nd Edition (MEAP yet)".

#### Run Kafka broker
Open a new terminal in the root project folder and run the next command 

```bash
docker-compose up
```

This will start zookeeper and kafka.

#### Test broker container: Create a topic, produce message and consume these using terminal
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

Thats all! you have created a topic, produced some records for that topic and started a consumer to see the created records in the topic. Good job!
