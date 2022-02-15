import { Kafka, Producer} from 'kafkajs';
import config from '../utils/config';
const { KAFKA_CLIENT_ID, KAFKA_BROKERS } = config;

const kafkaConfig = {
  clientId: KAFKA_CLIENT_ID,
  brokers: KAFKA_BROKERS
};

export class KafkaProducer {
  private static instance: KafkaProducer;
  private _producer: Producer = null;
  private _isConnected = false;

  private constructor() {
    const kafka = new Kafka(kafkaConfig);
    this._producer = kafka.producer();
  }

  public static getInstance(): KafkaProducer {
    if (!KafkaProducer.instance) {
      KafkaProducer.instance = new KafkaProducer();
    }
    return KafkaProducer.instance;
  }

  public get isConnected() {
    return this._isConnected;
  }

  async connect(): Promise<void> {
    try {
      await this._producer.connect();
      this._isConnected = true;
    } catch (err) {
      console.error(err);
    }
  }

  get producer() {
    return this._producer;
  }
}