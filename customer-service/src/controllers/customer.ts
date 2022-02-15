/* eslint-disable @typescript-eslint/no-misused-promises */
import { Customer } from '../models';
import express from 'express';

const customerRouter = express.Router();

customerRouter.get('/', async (_, res) => {
  try {
    const customers = await Customer.findAll();

    res.json(customers);
  } catch (e) {
    res.status(500).json([]);
  }
});

export default customerRouter;
