/**
 * Created by Alexander Onbysh 06.12.2019
 */

import java.time.LocalDateTime
import java.util.Properties

import org.apache.kafka.clients.producer._

class DisplayPartitionID extends Callback {
  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit =
    println(s"Partition id: ${metadata.partition()}")
}

object DisplayPartitionID {
  def apply(): DisplayPartitionID = new DisplayPartitionID()

}

object PartitionerDemo extends App {
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, (new PartitionerWithPriority).getClass.getName)
  props.put("FirstPriorityKeys", "first_message,last_message")

  val producer = new KafkaProducer[String, String](props)
  val TOPIC = "task7"

  producer.send(new ProducerRecord(TOPIC, "first_message", s"Start: ${LocalDateTime.now()}"), DisplayPartitionID())
  for (i <- 1 to 20) {
    Thread.sleep(50)
    producer.send(new ProducerRecord(TOPIC, s"key$i", s"Message ${LocalDateTime.now()}"), DisplayPartitionID())
  }
  producer.send(new ProducerRecord(TOPIC, "last_message", s"End: ${LocalDateTime.now()}"), DisplayPartitionID())


  //  Hardcoded partition
  producer.send(new ProducerRecord(TOPIC, 0, "last_message", s"Hardcoded partition"), DisplayPartitionID())
  producer.send(new ProducerRecord(TOPIC, 1, "last_message", s"Hardcoded partition"), DisplayPartitionID())

  producer.close()
}