/**
 * Created by Alexander Onbysh 06.12.2019
 */

import java.util.Properties

import org.apache.kafka.clients.producer._

object CustomClassForKeyDemo extends App {
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, (new InstEmpSerializer).getClass.getName)
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[InstEmp, String](props)
  val TOPIC = "task8"

  for (i <- 1 to 20) {
    Thread.sleep(50)
    val key = InstEmp(i, i * 2)
    producer.send(new ProducerRecord(TOPIC, key, s"Message ${key.toString}"))
  }

  producer.close()
}