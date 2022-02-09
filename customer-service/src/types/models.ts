import { Optional } from 'sequelize';

interface CreatedAtUpdatedAt {
  createdAt: Date
  updatedAt: Date
}

export interface CustomerAttributes extends CreatedAtUpdatedAt {
  id: number
  firstName: string
  lastName: string
  ssn: string
}

export type CustomerCreationAttributes = Optional<CustomerAttributes, 'id' | 'createdAt' | 'updatedAt'>;

export enum CreditCardType {
  VISA = 'visa',
  MASTER = 'master'
}

export interface CustomerCreditCardAttributes extends CreatedAtUpdatedAt {
  id: number,
  customerId: number
  type: CreditCardType
}