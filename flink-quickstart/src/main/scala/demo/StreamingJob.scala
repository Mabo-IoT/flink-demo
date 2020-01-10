package demo

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._

import java.util.Properties

import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
// import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.typeinfo.TypeInformation



object StreamingJob {
  private val ZOOKEEPER_HOST = "zookeeper:2181"
  private val KAFKA_BROKER = "kafka:9092"
  private val TRANSACTION_GROUP = "transaction"

  def main(args : Array[String]){
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //  how much time to consume message?
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.enableCheckpointing(1000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

    // configure Kafka consumer
    val kafkaProps = new Properties()
    kafkaProps.setProperty("zookeeper.connect", ZOOKEEPER_HOST)
    kafkaProps.setProperty("bootstrap.servers", KAFKA_BROKER)
    kafkaProps.setProperty("group.id", TRANSACTION_GROUP)

    //topic id is test ，schema is json
    implicit val typeInfo = TypeInformation.of(classOf[org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode])
    val kafkaConsumer = new FlinkKafkaConsumer("test", new JSONKeyValueDeserializationSchema(true), kafkaProps)
    
    val transaction = env
      .addSource(
        kafkaConsumer
      )
   
    // 1. calculate status kpi
    transaction.print()
    // val status = transaction.get("measurement")
    // status.print()
    // transaction.print()

    env.execute()

  }
}

// class KafkaSchema {


// }