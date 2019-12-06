import org.apache.kafka.common.serialization.Serializer

/**
 * Created by Alexander Onbysh 06.12.2019
 */
class InstEmpSerializer extends Serializer[InstEmp] {
  override def serialize(topic: String, data: InstEmp): Array[Byte] =
    data.toString.getBytes
}
