const { DomainEventPublisher, DefaultChannelMapping, MessageProducer, eventMessageHeaders: { AGGREGATE_ID } } = require('eventuate-tram-core-nodejs');
import { BankEntityTypeName, OrderCreatedEvent } from './eventConfig';
import bonuspointService from '../services/bonusPoints';
import customerService from '../services/customer';

const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });

export default {
  [BankEntityTypeName]: {
    [OrderCreatedEvent]: async (event) => {
      
      let resultObject = {
        customerFound: undefined,
        bonusPoints: undefined
      }
      const { customerId, creditAmount, orderId } = event.payload;
      if (!customerId || !creditAmount || !orderId) {
        console.log('invalid payload')
        return;
      }
      
      try {
        const customer = await customerService.getById(customerId);
        if (customer) {
          // do something
          resultObject.customerFound = true;
          
        } else {
          resultObject.customerFound = false;
          console.log('quitting handler')
          // publish some error event
          return;
        }
      } catch (e) {}

      try {
        await bonuspointService.create(customerId, orderId, creditAmount);
        
        console.log('bonuspoints created');
      } catch (e) {

        console.log('error creating bonuspoints:');
        console.log(e);
      }
      
    }
  }
};