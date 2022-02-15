import { CustomerBonusPoints } from '../models';

const create = async (
  customerId: number,
  orderId: number,
  points: number
): Promise<CustomerBonusPoints> => {
  const newBonusPoints = await CustomerBonusPoints.create<CustomerBonusPoints>({
    customerId,
    points,
    orderId
  });

  return newBonusPoints;
};

const deleteByOrderId = async (orderId: number) => {
  await CustomerBonusPoints.destroy({ where: { orderId: orderId } });
  return orderId;
};

export default {
  create,
  deleteByOrderId
};
