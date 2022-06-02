package cl.atromilen.kafkastreams.producers;

import cl.atromilen.kafkastreams.configs.BrokerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseProducer<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProducer.class);
    private final Class<?> keySerializer;
    private final Class<?> valueSerializer;
    private Map<String, Object> configs = new HashMap<>();

    public BaseProducer(Class<?> keySerializer, Class<?> valueSerializer) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    public void send(final String topicName, List<V> records){
        LOGGER.info("Starting to produce records{}", records);
        try(final KafkaProducer<K, V> producer = new KafkaProducer<>(producerConfig())) {
            records.forEach(movie -> producer.send(new ProducerRecord<>(topicName, movie)));
        }
    }

    private Map<String, Object> producerConfig(){
        final Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BrokerConfig.getBootstrapServer());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        producerProps.put(BrokerConfig.SCHEMA_REGISTRY_URL, BrokerConfig.getSchemaRegistryURL());

        return producerProps;
    }

}
