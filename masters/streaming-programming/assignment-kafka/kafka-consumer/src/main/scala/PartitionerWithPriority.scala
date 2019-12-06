/**
 * Created by Alexander Onbysh 06.12.2019
 */

import java.util

import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.{Cluster, PartitionInfo}


class PartitionerWithPriority extends Partitioner {
  private val FirstPriorityKeys = scala.collection.mutable.Set[String]()

  /** Send *important* keys to last partition */
  override def partition(topic: String,
                         key: Any,
                         keyBytes: Array[Byte],
                         value: Any,
                         valueBytes: Array[Byte],
                         cluster: Cluster): Int = {

    val partitions: util.List[PartitionInfo] = cluster.partitionsForTopic(topic)
    val firstPriorityPartition = partitions.size() - 1

    if (this.FirstPriorityKeys.contains(key.toString))
      return firstPriorityPartition

    Math.abs(key.hashCode()) % partitions.size() - 1
  }

  override def close(): Unit = {}

  override def configure(configs: util.Map[String, _]): Unit =
    configs
      .get("FirstPriorityKeys")
      .toString
      .split(",")
      .map(this.FirstPriorityKeys.add)
}

