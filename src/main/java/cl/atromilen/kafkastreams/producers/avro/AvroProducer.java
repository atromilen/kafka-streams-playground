package cl.atromilen.kafkastreams.producers.avro;

import cl.atromilen.kafkastreams.avro.MovieAvro;
import cl.atromilen.kafkastreams.configs.BrokerConfig;
import cl.atromilen.kafkastreams.producers.BaseProducer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AvroProducer extends BaseProducer<String, MovieAvro> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroProducer.class);

    public AvroProducer() {
        super(StringSerializer.class, KafkaAvroSerializer.class);
    }

    private static List<MovieAvro> createRecords(){
        final var theGodfather = MovieAvro.newBuilder()
                .setName("The Godfather")
                .setDirector("Francisc Ford Coppola")
                .setCast(List.of("Marlon Brando as Vito Corleone",
                        "Al Pacino as Michael Corleone",
                        "James Caan as Sonny Corleone",
                        "Robert Duvall as Tom Hagen")
                ).build();

        final var pulpFiction = MovieAvro.newBuilder()
                .setName("Pulp Fiction")
                .setDirector("Quentin Tarantino")
                .setCast(List.of("Samuel L. Jackson as Jules Winnfield",
                        "John Travolta as Vincent Vega",
                        "Uma Thurman as Mia Wallace",
                        "Bruce Willis as Butch Coolidge")
                ).build();

        final var fightClub = MovieAvro.newBuilder()
                .setName("Fight Club")
                .setDirector("David Fincher")
                .setCast(List.of("Edward Norton as The Narrator with no name",
                        "Brad Pitt as Tyler Durden",
                        "Helena Bonham as Marla Singer",
                        "Bruce Willis as Butch Coolidge")
                ).build();

        return List.of(theGodfather, pulpFiction, fightClub);
    }

    public static void main(String[] args) {
        String topic = BrokerConfig.getTopicAvroMovies();
        AvroProducer avroProducer = new AvroProducer();
        LOGGER.info("Sending records to topic {}", topic);
        avroProducer.send(topic, createRecords());
        LOGGER.info("Records sent. Finishing producer.");
    }
}
