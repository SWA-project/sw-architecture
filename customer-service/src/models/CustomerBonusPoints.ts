import {
  Model,
  Table,
  Column,
  DataType,
  ForeignKey
} from 'sequelize-typescript';

import {
  CustomerBonusPointsAttributes,
  CustomerBonusPointsCreationAttributes
} from '../types/models';
import Customer from './Customer';

@Table
class CustomerBonusPoints
  extends Model<
    CustomerBonusPointsAttributes,
    CustomerBonusPointsCreationAttributes
  >
  implements CustomerBonusPointsAttributes
{
  @Column({
    primaryKey: true,
    autoIncrement: true,
    type: DataType.INTEGER
  })
  id!: number;

  @ForeignKey(() => Customer)
  customerId!: number;

  @Column({
    type: DataType.FLOAT,
    unique: true
  })
  orderId!: number;

  @Column({
    type: DataType.FLOAT
  })
  points!: number;
}

export default CustomerBonusPoints;
