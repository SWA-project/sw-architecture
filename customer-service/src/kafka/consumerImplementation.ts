import { KafkaProducer } from './KafkaProducer';
import { EachMessageHandler, EachMessagePayload } from 'kafkajs';
import {} from './';
import customerService from '../services/customer';
import bonusPointsService from '../services/bonusPoints';

enum CustomerFoundStatus {
  FOUND = 'CUSTOMERFOUND',
  NOTFOUND = 'CUSTOMERNOTFOUND'
}

interface CustomerFoundEventResponseMessageValue {
  orderId: number,
  status: CustomerFoundStatus
}

interface VerifyCustomerMessageValue {
  customerId: number, 
  orderId: number, 
  creditAmount: number
}

const sendCustomerFoundEvent = async (value : CustomerFoundEventResponseMessageValue) => {
  const producer = KafkaProducer.getInstance().producer;

  const responseTopic = 'customer-order';
  const finalMessage = {
    value: JSON.stringify(value),
    partition: 0,
  };
  console.log({ value });
  await producer.send({
    topic: responseTopic,
    messages: [ finalMessage ]
  });
};



const handleVerifyCustomerEvent = async (payload: EachMessagePayload) => {
  
  const { message } = payload;
  const valueString  = message.value.toString();
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  const { customerId, orderId, creditAmount }: VerifyCustomerMessageValue = JSON.parse(valueString);
  const customer = await customerService.getById(customerId);

  const responseMessageValueObject = {
    orderId,
  };

  if (customer) {
    try {
      await bonusPointsService.create(customerId, orderId, creditAmount);
      
    } catch (e) {
      // do something??
    }
    
    const responseMessageValue = {
      ...responseMessageValueObject,
      status: CustomerFoundStatus.FOUND
    };
    
    await sendCustomerFoundEvent(responseMessageValue);
  } else {
    const responseMessageValue = {
      ...responseMessageValueObject,
      status: CustomerFoundStatus.NOTFOUND
    };
    
    await sendCustomerFoundEvent(responseMessageValue);
  }
};


const orderCustomerTopicHandler = async (payload: EachMessagePayload) => {
  await handleVerifyCustomerEvent(payload);
};

const eachMessageHandler: EachMessageHandler = async (payload) => {
  
  const { topic } = payload;
  console.log(`Message with topic "${topic}" received`);
  switch(topic) {
    case 'order-customer':
      return await orderCustomerTopicHandler(payload);
    default:
      return;
  }
};

export {
  eachMessageHandler
};