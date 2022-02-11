const { DomainEventPublisher, DefaultChannelMapping, MessageProducer, eventMessageHeaders: { AGGREGATE_ID } } = require('eventuate-tram-core-nodejs');
import { BankEntityTypeName, OrderCreatedEvent, CustomerEntityTypeName, CustomerValidationFailedEvent} from './eventConfig';
import bonuspointService from '../services/bonusPoints';
import customerService from '../services/customer';

const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });

const publishCustomerValidationFailedEvent = async (orderId, customerId) => {
  const customerValidationFailedEvent = { orderId, _type: CustomerValidationFailedEvent };
  return await domainEventPublisher.publish(CustomerEntityTypeName,
    customerId,
    [ { ...customerValidationFailedEvent} ],
    { }
  );
}

export default {
  [BankEntityTypeName]: {
    [OrderCreatedEvent]: async (event) => {
      
      let resultObject = {
        customerFound: undefined,
        bonusPoints: undefined
      }
      const { customerId, creditAmount, orderId } = event.payload;
      if (!customerId) {

        return await publishCustomerValidationFailedEvent(orderId, String(customerId));
      }
      const customer = await customerService.getById(customerId);
      if (!customer) {
        // do something
        return publishCustomerValidationFailedEvent(orderId, customerId);
      }

      const newBonusPoints = await bonuspointService.create(customerId, orderId, creditAmount);
      
      try {
        await bonuspointService.publishBonusPointsCreatedEvent(newBonusPoints.toJSON());
      } catch (e) {
        await bonuspointService.deleteByOrderId(newBonusPoints.orderId);
      }
        
      
      
    }
  }
};