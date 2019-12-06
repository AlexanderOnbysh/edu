/**
 * Created by Alexander Onbysh 06.12.2019
 */

import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import scala.collection.JavaConverters._

object KafkaConsumerApp extends App {
  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group")

  val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)

  try {
    consumer.subscribe(List("task9").asJava)
    while (true) {
      val records = consumer.poll(10)
      for (record <- records.asScala) {
        println("Topic: " + record.topic() +
          ",Key: " + record.key() +
          ",Value: " + record.value() +
          ", Offset: " + record.offset() +
          ", Partition: " + record.partition())
      }
    }
  } catch {
    case e: Exception => e.printStackTrace()
  }
  finally {
    consumer.close()
  }

}
