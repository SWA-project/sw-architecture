import { KafkaProducer } from './KafkaProducer';
import { EachMessageHandler, EachMessagePayload } from 'kafkajs';
import {} from './';
import customerService from '../services/customer';
import bonusPointsService from '../services/bonusPoints';


const handleVerifyCustomerEvent = async (payload: EachMessagePayload) => {
  const producer = KafkaProducer.getInstance().producer;
  
  const { message } = payload;
  const valueString  = message.value.toString();
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  const { customerId, orderId, event, creditAmount }: { event: string, customerId: number, orderId: number, creditAmount: number } = JSON.parse(valueString);
  const customer = await customerService.getById(customerId);

  const responseMessageValueObject = {
    orderId,
    customerId,
    event
  };
  if (customer) {
    try {
      await bonusPointsService.create(customerId, orderId, creditAmount);
      
    } catch (e) {
      // do something??
    }
    
    const message = {
      value: JSON.stringify({ 
        ...responseMessageValueObject, 
        customerFound: true
      })
    };
    
    await producer.send({
      topic: 'CustomerOrder',
      messages: [ message ]
    });


  } else {

    const message = {
      value: JSON.stringify({ 
        ...responseMessageValueObject, 
        customerFound: false
      })
    };

    await producer.send({
      topic: 'CustomerOrder',
      messages: [ message ]
    });

  }
};


const orderCustomerTopicHandler = async (payload: EachMessagePayload) => {
  const { message } = payload;
  const valueString  = message.value.toString();
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  const value: { event: string } = JSON.parse(valueString);

  const { event } = value;

  switch(event) {
    case 'VerifyCustomer':
      return await handleVerifyCustomerEvent(payload);
    case 'CreditRequest':
      return await handleVerifyCustomerEvent(payload);
    default:
      return;
  }

};

const eachMessageHandler: EachMessageHandler = async (payload) => {
  
  const { topic } = payload;
  console.log(`Message with topic "${topic}" received`);
  switch(topic) {
    // change to OrderCustomer
    case 'orders':
      return await orderCustomerTopicHandler(payload);
    default:
      return;
  }
};

export {
  eachMessageHandler
};