import { CustomerBonusPoints } from '../models';



const create = async (customerId: number, orderId: number, points: number): Promise<CustomerBonusPoints> => {
  console.log({ customerId })
  const newCustomerPoints = await CustomerBonusPoints.create({
    customerId,
    points,
    orderId
  })
  return newCustomerPoints;
};

export default {
  create
}