const { DomainEventPublisher, DefaultChannelMapping, MessageProducer, eventMessageHeaders: { AGGREGATE_ID } } = require('eventuate-tram-core-nodejs');
import { BankEntityTypeName, ApplicationCreatedEvent } from './eventConfig';

const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });

export default {
  [BankEntityTypeName]: {
    [ApplicationCreatedEvent]: async (event) => {
      console.log('hereeeeeeeeeeeeeee')
      const { [AGGREGATE_ID]: applicationId } = event;
      console.log({ applicationId })
      console.log('IN EVENT HANDLER!!!!!!!!!!!');
      
    }
  }
};