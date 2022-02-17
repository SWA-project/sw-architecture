import { Optional } from 'sequelize';

export interface CreatedAtUpdatedAt {
  createdAt?: Date;
  updatedAt?: Date;
}

export interface CustomerAttributes extends CreatedAtUpdatedAt {
  id: number;
  firstName: string;
  lastName: string;
  ssn: string;
  bonusPoints: CustomerBonusPointsAttributes[];
}

export type CustomerCreationAttributes = Optional<
  CustomerAttributes,
  'id' | 'createdAt' | 'updatedAt' | 'lastName' | 'bonusPoints'
>;

export interface CustomerBonusPointsAttributes extends CreatedAtUpdatedAt {
  id: number;
  customerId: number;
  orderId: number;
  points: number;
}

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface CustomerBonusPointsCreationAttributes
  extends Optional<
    CustomerBonusPointsAttributes,
    'id' | 'createdAt' | 'updatedAt'
  > {}
