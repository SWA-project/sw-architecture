import { Sequelize } from 'sequelize-typescript';
import { DATABASE_URL } from './config';
import { Customer, CustomerCreditCard } from '../models';
const sequelize = new Sequelize(DATABASE_URL, {
  dialectOptions: {
  },
  models: [Customer, CustomerCreditCard]
});

const connectToDatabase = async () => {
  try {
    await sequelize.authenticate();
    await sequelize.sync({ force: true });
    console.log('connected to the database');
  } catch (err) {
    console.log('failed to connect to the database');
    console.log(err);
    process.exit(1);
  }

  return null;
};

export {
  connectToDatabase,
  sequelize
};