const { DomainEventPublisher, DefaultChannelMapping, MessageProducer } = require('eventuate-tram-core-nodejs');

import { CustomerAttributes } from '../types/models';
import { OrderCreatedEvent, BankEntityTypeName } from '../eventuate-tram/eventConfig';



const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });



const create = async (customerId: number, creditAmount: number, orderId: number) : Promise<Number> => {
  
  const randomId = Math.floor(Math.random() * 10000);
  const application = {
    id: randomId,
    customerId,
    creditAmount,
    orderId
  }

  await domainEventPublisher.publish(
    BankEntityTypeName, 
    application.id, 
    [{ _type: OrderCreatedEvent, ...application}],
  );

  return application.id

}


export default {
  create
}