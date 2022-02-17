import { OrderRollBackMessageValue } from './../types/kafka';
import { EachMessageHandler, EachMessagePayload } from 'kafkajs';

import producer from './producerImplementation';

import customerService from '../services/customer';
import bonusPointsService from '../services/bonusPoints';
import {
  VerifyCustomerMessageValue,
  CustomerFoundStatus
} from '../types/kafka';

const handleVerifyCustomerEvent = async (payload: EachMessagePayload) => {
  const { message } = payload;
  const valueString = message.value.toString();
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  const { customerId, orderId, creditAmount }: VerifyCustomerMessageValue =
    JSON.parse(valueString);
  const customer = await customerService.findById(customerId);

  const responseMessageValueObject = {
    orderId
  };

  if (customer) {
    try {
      await bonusPointsService.create({
        customerId,
        orderId,
        points: creditAmount
      });
    } catch (e) {
      // do something??
    }

    const responseMessageValue = {
      ...responseMessageValueObject,
      status: CustomerFoundStatus.FOUND
    };

    await producer.sendCustomerFoundEvent(responseMessageValue);
  } else {
    const responseMessageValue = {
      ...responseMessageValueObject,
      status: CustomerFoundStatus.NOTFOUND
    };
    await producer.sendCustomerFoundEvent(responseMessageValue);
  }
};

const orderCustomerTopicHandler = async (payload: EachMessagePayload) => {
  await handleVerifyCustomerEvent(payload);
};

const orderRollbackTopicHandler = async (payload: EachMessagePayload) => {
  const { message } = payload;
  const valueString = message.value.toString();
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  const { orderId }: OrderRollBackMessageValue = JSON.parse(valueString);
  await bonusPointsService.deleteByOrderId(orderId);
};

const eachMessageHandler: EachMessageHandler = async (payload) => {
  const { topic } = payload;
  console.log(
    `Message with topic "${topic}" received, with payload ${payload.message.value.toString()}`
  );

  switch (topic) {
    case 'order-customer':
      return await orderCustomerTopicHandler(payload);
    case 'order-rollback':
      return await orderRollbackTopicHandler(payload);
    default:
      return;
  }
};

export { eachMessageHandler };
