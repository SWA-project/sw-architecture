import { Sequelize } from 'sequelize-typescript';
import { DATABASE_URL } from './config';
import { Customer, CustomerBonusPoints } from '../models';
const sequelize = new Sequelize(DATABASE_URL, {
  dialectOptions: {},
  models: [Customer, CustomerBonusPoints]
});

const insertInitialData = async () => {
  const isInitialUserInDb = await Customer.findByPk(1);
  if (!isInitialUserInDb) {
    await Customer.create({
      id: 1,
      firstName: 'Ossi',
      ssn: '010101-1111'
    });
  }
};

const connectToDatabase = async () => {
  try {
    await sequelize.authenticate();
    await sequelize.sync();
    console.log('connected to the database');
    await insertInitialData();
  } catch (err) {
    console.log('failed to connect to the database');
    console.log(err);
    process.exit(1);
  }

  return null;
};

export { connectToDatabase, sequelize };
