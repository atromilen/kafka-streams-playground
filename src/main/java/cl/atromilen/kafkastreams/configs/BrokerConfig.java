package cl.atromilen.kafkastreams.configs;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class BrokerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerConfig.class);
    public final static String SCHEMA_REGISTRY_URL = "schema.registry.url";
    public final static String TOPIC_AVRO_MOVIES = "topic.avro.movie";
    private static final Properties props = new Properties();

    private BrokerConfig() {
        // for singleton
    }

    public static Properties getProps(){
        if (props.isEmpty()) {
            try(InputStream input = BrokerConfig.class.getResourceAsStream("/system.properties")) {
                props.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return props;
    }

    public static String getBootstrapServer(){
        return getProps().getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
    }

    public static String getSchemaRegistryURL(){
        return getProps().getProperty(SCHEMA_REGISTRY_URL);
    }

    public static String getTopicAvroMovies(){
        return getProps().getProperty(TOPIC_AVRO_MOVIES);
    }

}
