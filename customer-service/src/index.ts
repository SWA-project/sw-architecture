/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import express from 'express';
import healthRouter from './controllers/health';
import customerRouter from './controllers/customer';

import config from './utils/config';

import { connectToDatabase } from './utils/db';

const app = express();
app.use(express.json());

const PORT: string = config.PORT;

app.use(healthRouter);
app.use('/customer', customerRouter);

const startServer = async () => {
  

  try {
  } catch (e) {
    console.log(e)
    console.log('existing...')
    process.exit(1);
  }

  await connectToDatabase();
  app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
  });
};

void startServer();
