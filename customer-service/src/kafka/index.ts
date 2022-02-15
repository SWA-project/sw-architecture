import { EachMessagePayload, Kafka } from 'kafkajs';
import { KafkaProducer } from './KafkaProducer';
import config from '../utils/config';
import { eachMessageHandler } from './consumerImplementation';
const { KAFKA_CLIENT_ID, KAFKA_BROKERS } = config;

const kafkaConfig = {
  clientId: KAFKA_CLIENT_ID,
  brokers: KAFKA_BROKERS
};

const subscribeTopics = ['orders'];


const connect = async () => {
  const kafka = new Kafka(kafkaConfig);

  const producer = KafkaProducer.getInstance();
  await producer.connect();
  console.log('Producer connected');
  
  const consumer = kafka.consumer({ groupId: 'customer-group'});

  await consumer.connect();
  for (const topic of subscribeTopics) {
    await consumer.subscribe({ topic });
  }
  console.log('Consumer connected');
  await consumer.run({
    eachMessage: async (messagePayLoad: EachMessagePayload) => {
      await eachMessageHandler(messagePayLoad);
    },
  });
};



export default {
  connect,
  
};