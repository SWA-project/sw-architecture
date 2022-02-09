
import { Model, Table, Column, DataType, HasMany } from 'sequelize-typescript';

import { CustomerCreationAttributes, CustomerAttributes } from '../types/models';

import CustomerCreditCard from './CustomerCreditCard';

@Table
class Customer extends Model<CustomerAttributes, CustomerCreationAttributes> {
  @Column({ 
    primaryKey: true, 
    autoIncrement: true,
    type: DataType.INTEGER
  })
  id!: number;

  @Column({
    type: DataType.STRING
  })
  firstName!: string;

  @Column({
    type: DataType.STRING
  })
  lastName!: string;

  @Column({
    type: DataType.STRING
  })
  ssn!: string;

  @HasMany(() => CustomerCreditCard)
  creditCards?: CustomerCreditCard[];

}


export default Customer;