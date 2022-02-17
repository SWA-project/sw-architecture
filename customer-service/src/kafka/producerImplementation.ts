import { KafkaProducer } from './KafkaProducer';
import { CustomerFoundEventResponseMessageValue } from '../types/kafka';

export const sendCustomerFoundEvent = async (
  value: CustomerFoundEventResponseMessageValue
) => {
  const producer = KafkaProducer.getInstance().producer;

  const responseTopic = 'customer-order';
  const finalMessage = {
    value: JSON.stringify(value),
    partition: 0
  };

  await producer.send({
    topic: responseTopic,
    messages: [finalMessage]
  });
};

export default {
  sendCustomerFoundEvent
};
