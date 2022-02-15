/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */

import express from 'express';

import healthRouter from './controllers/health';
import customerRouter from './controllers/customer';

import { connectToDatabase } from './utils/db';

import kafka from './kafka';

import config from './utils/config';

const { PORT } = config;

const app = express();
app.use(express.json());

app.use(healthRouter);
app.use('/customers', customerRouter);

const startServer = async () => {

  await connectToDatabase();
  try {
    await kafka.connect();
  } catch (e) {
    
    console.log('error on connecting to kafka');
    console.log(e);
    console.log('exiting...');
    process.exit(1);
  }
  app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
  });

  
};

void startServer();
