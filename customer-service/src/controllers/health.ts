/* eslint-disable @typescript-eslint/no-misused-promises */
import express from 'express';
import { sequelize } from '../utils/db';
import { Response } from 'express';

const healthRouter = express.Router();

healthRouter.get('/health', (_req, res) => {
  
  res.json('healthy');
});

healthRouter.get('/health/db-connection', async (_req, res: Response) => {
  try {
    await sequelize.authenticate();
    res.json('healthy');
  } catch (e) {
    res.status(500).json('not-healthy');
  }
});

export default healthRouter;