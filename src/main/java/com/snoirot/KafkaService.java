package com.snoirot;

import io.smallrye.mutiny.Multi;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaService {

  public static final Logger LOGGER = Logger.getLogger(KafkaService.class);

  @Outgoing("demo-kafka-out")
  public Multi<Message<String>> produceData() {
    return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
        .map(tick -> Message.of("SmallRye Hello " + tick));
  }

  @Incoming("demo-kafka-in")
  public void consumeData(String message) {
    LOGGER.infov("Message received {0}", message);
  }
}
