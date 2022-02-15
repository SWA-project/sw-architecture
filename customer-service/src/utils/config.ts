import 'dotenv/config';

const DATABASE_URL: string = process.env.DATABASE_URL || '';
const PORT: string = process.env.PORT || '3000';
const KAFKA_BROKERS_STRING: string = process.env.KAFKA_BROKERS || '';
const KAFKA_CLIENT_ID: string =
  process.env.KAFKA_CLIENT_ID || 'customer-service';

const KAFKA_BROKERS = KAFKA_BROKERS_STRING.split(';');

export {
  DATABASE_URL,
  PORT,
  KAFKA_BROKERS_STRING,
  KAFKA_BROKERS,
  KAFKA_CLIENT_ID
};

export default {
  DATABASE_URL,
  PORT,
  KAFKA_BROKERS_STRING,
  KAFKA_BROKERS,
  KAFKA_CLIENT_ID
};
