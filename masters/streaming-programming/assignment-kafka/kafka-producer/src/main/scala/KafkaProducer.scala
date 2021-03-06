/**
 * Created by Alexander Onbysh 06.12.2019
 */

import java.time.LocalDateTime
import java.util.Properties

import org.apache.kafka.clients.producer._

class CompletionCallback extends Callback {
  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit =
    println(s"Complete ${metadata.timestamp()}")
}

object KafkaProducer extends App {
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val TOPIC = "t2"

  producer.send(new ProducerRecord(TOPIC, "key", s"Start: ${LocalDateTime.now()}"))
  for (_ <- 1 to 10)
    producer.send(new ProducerRecord(TOPIC, "key", s"Message ${LocalDateTime.now()}"))
  producer.send(new ProducerRecord(TOPIC, "key", s"End: ${LocalDateTime.now()}"), new CompletionCallback())

  producer.close()
}