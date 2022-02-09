const { DomainEventPublisher, DefaultChannelMapping, MessageProducer } = require('eventuate-tram-core-nodejs');

import { CustomerAttributes } from './../types/models';
import { ApplicationCreatedEvent, BankEntityTypeName } from '../eventuate-tram/eventConfig';



const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });



const create = async (ssn: CustomerAttributes['ssn']) : Promise<Number> => {
  
  const randomId = Math.floor(Math.random() * 10000);
  const application = {
    id: randomId,
    ssn
  }

  await domainEventPublisher.publish(
    BankEntityTypeName, 
    application.id, 
    [{ _type: ApplicationCreatedEvent, ...application}],
  );

  return application.id

}


export default {
  create
}