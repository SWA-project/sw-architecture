/* eslint-disable @typescript-eslint/no-misused-promises */
import { Customer } from '../models';
import express from 'express';
import customerService from '../services/customer';
import orderService from '../services/order';

const customerRouter = express.Router();

customerRouter.get('/', async (_, res) => {

  try {
    const customers = await Customer.findAll();

    res.json(customers);
  } catch (e) {
    res.status(500).json([]);
  }
});

customerRouter.post('/cards', async (req, res) => {
  try {
    const { ssn, creditCardType } = req.body;
    const creditCardId = await customerService.createCreditCard(ssn, creditCardType);
    res.status(200).json({ id: creditCardId})
  } catch (e) {
    console.log(e)
    res.status(500).json([]);
  }
})

customerRouter.post('/order', async (req, res) => {
  try {
    const { customerId, creditAmount, orderId } = req.body;
    const applicationId = await orderService.create(customerId, creditAmount, orderId);
    res.status(200).json({ id: applicationId })
  } catch (e) {
    console.log(e)
    res.status(500).json([]);
  }
})

export default customerRouter;
