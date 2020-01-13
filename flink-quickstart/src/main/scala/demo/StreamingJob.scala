package demo

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._

import java.util.Properties

import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
// import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08
// import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
// import io.streamnative.connectors.pulsar.PulsarSourceBuilder
import org.apache.flink.streaming.connectors.pulsar.FlinkPulsarSource
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.typeinfo.TypeInformation



object StreamingJob {
  // private val ZOOKEEPER_HOST = "zookeeper:2181"
  // private val KAFKA_BROKER = "kafka:9092"
  // private val TRANSACTION_GROUP = "transaction"

  def main(args : Array[String]){
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //  how much time to consume message?
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.enableCheckpointing(1000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    
    // props.setProperty("service.url", "pulsar://standalone:6650")
    // // props.setProperty("admin.url", "http://...")
    // props.setProperty("partitionDiscoveryIntervalMillis", "5000")
    // props.setProperty("startingOffsets", "earliest")
    // props.setProperty("topic", "test")
    
    
    val props = new Properties()
    props.setProperty("service.url", "pulsar://standalone:6650")
    props.setProperty("admin.url", "http://standalone:8080")
    props.setProperty("partitionDiscoveryIntervalMillis", "5000")
    props.setProperty("startingOffsets", "earliest")
    props.setProperty("topic", "test")
    val source = new FlinkPulsarSource(props)
// you don't need to provide a type information to addSource since FlinkPulsarSource is ResultTypeQueryable
    // val dataStream = env.addSource(source)(null)
    val transaction = env.addSource(source)(null);

    //topic id is test ，schema is json
    // implicit val typeInfo = TypeInformation.of(classOf[org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode])
    // val kafkaConsumer = new FlinkKafkaConsumer("test", new JSONKeyValueDeserializationSchema(true), kafkaProps)
   
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