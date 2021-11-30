package com.snoirot;

import static org.awaitility.Awaitility.await;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@QuarkusTestResource(value = KafkaTestResource.class, restrictToAnnotatedClass = true)
class KafkaServiceTest {

  @InjectSpy
  KafkaService kafkaService;

  @Test
  void testKafkaService() {

    var startTime = System.currentTimeMillis();
    await().atMost(Duration.ofSeconds(10)).until(() -> System.currentTimeMillis() - startTime > 9000);

    Mockito.verify(kafkaService, Mockito.atLeastOnce()).consumeData(Mockito.anyString());
  }
}