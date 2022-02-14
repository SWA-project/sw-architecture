


const create = async (customerId: number, creditAmount: number, orderId: number) : Promise<Number> => {
  
  const randomId = Math.floor(Math.random() * 10000);
  const application = {
    id: randomId,
    customerId,
    creditAmount,
    orderId
  }

  

  return application.id

}


export default {
  create
}