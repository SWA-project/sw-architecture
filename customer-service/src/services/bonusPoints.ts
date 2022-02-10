import { CustomerBonusPointsAttributes, CustomerAttributes } from './../types/models';
const { DomainEventPublisher, DefaultChannelMapping, MessageProducer } = require('eventuate-tram-core-nodejs');

import { BonusPointsCreatedEvent, CustomerEntityTypeName } from '../eventuate-tram/eventConfig';
const channelMapping = new DefaultChannelMapping(new Map());
const messageProducer = new MessageProducer({ channelMapping });
const domainEventPublisher = new DomainEventPublisher({ messageProducer });


import { CustomerBonusPoints } from '../models';



const publishBonusPointsCreatedEvent = async (bonusPointsObject: CustomerBonusPoints) => {
  await domainEventPublisher.publish(
    CustomerEntityTypeName, 
    bonusPointsObject, 
    [{ _type: BonusPointsCreatedEvent, ...bonusPointsObject}],
  );

}



const create = async (customerId: number, orderId: number, points: number): Promise<CustomerBonusPoints> => {
  
  const newBonusPoints = await CustomerBonusPoints.create<CustomerBonusPoints>({
    customerId,
    points,
    orderId
  });
  
  return newBonusPoints;


};

const deleteByOrderId = async (orderId) => {
  await CustomerBonusPoints.destroy({ where: { orderId: orderId }});
  return orderId;
}

export default {
  create,
  deleteByOrderId,
  publishBonusPointsCreatedEvent
}