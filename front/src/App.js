import { Input, Button } from './StyledComponents'
import React, { useEffect, useState } from 'react'
import { fetchOrders, sendOrder } from './services/bankService';


function App() {
    const [customerId, setCustomerId] = useState('')
    const [creditAmount, setCreditAmount] = useState('')
    const [order, setOrder] = useState(null)
    const [verdicts, setVerdicts] = useState([])


    const initVerdicts = async () => {
      let fetchedVerdicts = []

      try {
        fetchedVerdicts = await fetchOrders()
      } catch (error) {
        console.log(error)
      }

      setVerdicts(fetchedVerdicts)
    }

    useEffect(()=> {
    }, [verdicts])

    const handleNewOrder = ( resps ) => {
      setOrder(resps)
    }

    const postOrder = async (event) => {
        event.preventDefault()

        const newOrder = {
            customerId: customerId,
            creditAmount: creditAmount,
        }
        try {
            const newO = await sendOrder(newOrder)
            handleNewOrder(newO)
            initVerdicts()
        } catch (error) {
            console.log(error)
        }
        setCustomerId('')
        setCreditAmount('')
    }
    
    return (
    <div>
        <h2>Test environment</h2>
          <h3>Make a credit order</h3>
            <form onSubmit={postOrder}>
                <div>
                    Customer-id: <Input value={customerId} 
                    onChange={(e) => setCustomerId(e.target.value)} required/>
                </div>
                <div>
                    Credit amount: <Input value={creditAmount} type="number"
                    onChange={(e) => setCreditAmount(e.target.value)} required/>
                </div>
                <div>
                <Button type="submit">Send</Button>
                </div>
            </form>
            <div>
            <h3>New order</h3>
              {order != null?
                <p key={order.id}>Order id: {order.id}, 
                status: {order.status}, credit amount: {order.creditAmount}, 
                rejection reason: {order.rejectionReason}</p>
              : null}
            </div>
            <div>
            <h3>Verdicts</h3>
              {verdicts.map((r) =>  (
                    <p key={r.id} >Order id: {r.id}, 
                    customer id: {r.customerId} 
                    status: {r.status}, 
                    credit amount: {r.creditAmount}, 
                    rejection reason: {r.rejectionReason}</p>
              ))}
            </div>
    </div>
    )
}

export default App;
