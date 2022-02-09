/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import express from 'express';
import healthRouter from './controllers/health';
import customerRouter from './controllers/customer';

import config from './utils/config';

import { connectToDatabase } from './utils/db';
import bankEventHandlers from './eventuate-tram/bankEventHandlers';
const { MessageConsumer, DomainEventDispatcher, DefaultDomainEventNameMapping } = require('eventuate-tram-core-nodejs');

const domainEventNameMapping = new DefaultDomainEventNameMapping();

const messageConsumer = new MessageConsumer();

const app = express();
app.use(express.json());

const PORT: string = config.PORT;

app.use(healthRouter);
app.use('/customer', customerRouter);

const startServer = async () => {
  // eslint-disable-next-line @typescript-eslint/no-unsafe-call
  const domainEventDispatcher = new DomainEventDispatcher({
    eventDispatcherId: 'customerServiceEventsNode',
    
    domainEventHandlers: bankEventHandlers,
    messageConsumer,
    domainEventNameMapping
  });

  try {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-call
    console.log(Object.keys(bankEventHandlers))
    await domainEventDispatcher.initialize();
  } catch (e) {
    console.log(e)
    // eslint-disable-next-line @typescript-eslint/no-unsafe-call
    console.log('existing...')
    process.exit(1);
  }

  await connectToDatabase();
  app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
  });
};

void startServer();
