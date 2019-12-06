/**
 * Created by Alexander Onbysh 06.12.2019
 */

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.ProducerRecord

import scala.jdk.CollectionConverters._


object Application extends App with LazyLogging {
  val INPUT_TOPIC = "input"
  val OUTPUT_TOPIC = "output"

  val consumer = Resources.getConsumer()
  val producer = Resources.getProducer()

  try {
    consumer.subscribe(List(INPUT_TOPIC).asJava)
    while (true) {
      val records = consumer.poll(100)
      for (record <- records.asScala) {
        logger.info(s"Got message: <${record.value()}>")
        record
          .value()
          .split(" ")
          .groupMap(identity)(identity)
          .transform((_, value) => value.length)
          .foreach {
            case (key, value) => {
              logger.info(s"Send message: < $key -> $value>")
              producer.send(new ProducerRecord(OUTPUT_TOPIC, key, value.toString))
            }
          }
      }
    }
  } catch {
    case e: Exception => e.printStackTrace()
  }
  finally {
    consumer.close()
  }
}
