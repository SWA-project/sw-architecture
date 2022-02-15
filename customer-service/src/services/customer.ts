import { CustomerAttributes } from './../types/models';
import { Customer } from '../models';

const getById = async (id: number): Promise<CustomerAttributes | null> => {
  const customer = await Customer.findOne<Customer>({ where: { id: id } });
  return customer;
};

export default {
  getById
};
