import {
  CustomerAttributes,
  CustomerCreationAttributes,
  CustomerBonusPointsAttributes
} from './../types/models';
import { Customer, CustomerBonusPoints } from '../models';

interface CustomerFullData extends CustomerAttributes {
  bonusPointsTotal: number;
}

const create = async (
  customerCreationAttributes: CustomerCreationAttributes
): Promise<CustomerFullData | null> => {
  const newCustomer = await Customer.create<Customer>(
    customerCreationAttributes
  );
  const customerAttributes = newCustomer.toJSON();
  const bonusPointsArray: CustomerBonusPointsAttributes[] = [];
  const fullCustomerData = {
    ...customerAttributes,
    bonusPointsTotal: 0,
    bonusPoints: bonusPointsArray
  };

  return fullCustomerData;
};

const findById = async (id: number): Promise<CustomerFullData | null> => {
  const customer = await Customer.findOne<Customer>({
    where: { id: id },
    include: [CustomerBonusPoints]
  });
  if (!customer) {
    return null;
  }
  const customerAttributes = customer.toJSON();
  const bonusPointsTotal = customer.bonusPoints.reduce(
    (prev, cur) => prev + cur.points,
    0
  );

  const customerFullData = {
    ...customerAttributes,
    bonusPointsTotal
  };

  return customerFullData;
};

export default {
  findById,
  create
};
