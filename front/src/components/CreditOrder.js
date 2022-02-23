import { useState } from "react"
import { sendOrder } from "../services/bankService"
import { Button, Input } from "../StyledComponents"

const CreditOrder = ( { verdicts } ) => {
    const [customerId, setCustomerId] = useState('')
    const [creditAmount, setCreditAmount] = useState('')
    const [order, setOrder] = useState(null)

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
        } catch (error) {
            console.log(error)
        }
        setCustomerId('')
        setCreditAmount('')
    }
    
    return (
    <div>
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

export default CreditOrder