const { DomainEventPublisher, DefaultChannelMapping, MessageProducer } = require('eventuate-tram-core-nodejs');
import { CustomerCreditCardCreatedEvent, CustomerEntityTypeName } from '../eventuate-tram/eventConfig';

import { CustomerAttributes, CustomerCreditCardAttributes } from './../types/models';
import { Customer, CustomerCreditCard } from '../models';



const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });



const createCreditCard = async (ssn: CustomerAttributes['ssn'], creditCardType: CustomerCreditCardAttributes['type']) : Promise<Number> => {
  let customer = await Customer.findOne({ where: { ssn: ssn } });
  if (!customer) {
    customer = await Customer.create({
      firstName: "etunimi",
      lastName: "sukunimi",
      ssn: ssn
    });
  }
  const newCreditCard = await CustomerCreditCard.create({
    customerId: customer.id,
    type: creditCardType
  })

  try {
    await domainEventPublisher.publish(
      CustomerEntityTypeName, 
      newCreditCard.id, 
      [{ _type: CustomerCreditCardCreatedEvent, ...newCreditCard.toJSON()}],
    );
  } catch (e) {
    await CustomerCreditCard.destroy({ where: { id: newCreditCard.id }})
    throw new Error(e);
  }

  return newCreditCard.id

}


export default {
  createCreditCard
}