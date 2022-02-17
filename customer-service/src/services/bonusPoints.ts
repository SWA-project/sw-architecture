import {
  CustomerBonusPointsAttributes,
  CustomerBonusPointsCreationAttributes
} from './../types/models';
import { CustomerBonusPoints } from '../models';

const create = async ({
  customerId,
  points,
  orderId
}: CustomerBonusPointsCreationAttributes): Promise<CustomerBonusPointsAttributes> => {
  const newBonusPoints = await CustomerBonusPoints.create<CustomerBonusPoints>({
    customerId,
    points,
    orderId
  });

  return newBonusPoints.toJSON();
};

const deleteByOrderId = async (orderId: number) => {
  await CustomerBonusPoints.destroy({ where: { orderId: orderId } });
  return orderId;
};

export default {
  create,
  deleteByOrderId
};
