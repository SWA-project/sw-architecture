/* eslint-disable @typescript-eslint/no-misused-promises */
import { Customer } from '../models';
import express, { Request } from 'express';
import customerService from '../services/customer';

const customerRouter = express.Router();

customerRouter.get('/', async (_, res) => {
  try {
    const customers = await Customer.findAll();

    res.json(customers);
  } catch (e) {
    res.status(500).json([]);
  }
});

customerRouter.get('/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const idNumber = Number(id);

    if (isNaN(idNumber)) {
      return res.status(400).json();
    }

    const customer = await customerService.findById(idNumber);
    if (!customer) {
      return res.status(404).json();
    }

    return res.json(customer);
  } catch (e) {
    return res.status(500).json([]);
  }
});

customerRouter.post('/', async (req: Request, res) => {
  try {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
    const { firstName, lastName, ssn } = req.body;
    if (!firstName || !ssn) {
      return res.status(400).json();
    }
    if (typeof firstName !== 'string' || typeof ssn !== 'string') {
      return res.status(400).json();
    }

    if (typeof lastName !== 'string' && typeof lastName !== 'undefined') {
      return res.status(400).json();
    }

    const newCustomer = await customerService.create({
      firstName,
      lastName,
      ssn
    });

    return res.json(newCustomer);
  } catch (e) {
    console.log(e);
    return res.status(500).json([]);
  }
});

export default customerRouter;
