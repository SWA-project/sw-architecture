export enum CustomerFoundStatus {
  FOUND = 'CUSTOMERFOUND',
  NOTFOUND = 'CUSTOMERNOTFOUND'
}

export interface CustomerFoundEventResponseMessageValue {
  orderId: number;
  status: CustomerFoundStatus;
}

export interface CustomerFoundEventResponseMessageValue {
  orderId: number;
  status: CustomerFoundStatus;
}

export interface VerifyCustomerMessageValue {
  customerId: number;
  orderId: number;
  creditAmount: number;
}

export interface OrderRollBackMessageValue {
  orderId: number;
  event: string;
}
