import { useState } from "react"
import { sendOrder } from "../services/bankService"
import { Button } from "../StyledComponents"
import Styles from "../Styles"

const RequestStream = () => {
    const [stop, setStop] = useState(false)

    const requestStream = async (event) => {
        event.preventDefault()
        const interval = setInterval(()=> {
            if (stop) clearInterval(interval)
            sendRequest()       
        }, 2000)
    }

    const sendRequest = async () => {
        if (stop) {
            return
        }

        let ids = [1, 2]
        const credits = [10, 20, 50, 100, 1000]

        const newOrder = {
            customerId: ids[Math.floor(Math.random() * ids.length)],
            creditAmount: credits[Math.floor(Math.random() * credits.length)],
        }
        try {
            await sendOrder(newOrder)
        } catch (error) {
            console.log(error)
        }
    }

    const handleStop = async () => {
        setStop(true)
    }

    
    return (
        <div style={Styles.container}>
            <h3>Make a request stream</h3>
            <div>
                <i> By starting a request stream, 
                    the client side will send requests 
                    to the server in every second.</i>
            </div>
            <div>
                <i> Requests are generated randomly between user that is identified customer (1)
                    and user that is not (2).    
                </i>
            </div>
           
            <form style={Styles.buttonChild} onSubmit={requestStream}>
                <Button type="submit">Start</Button>
            </form>
            <form style={Styles.buttonChild} onSubmit={handleStop}>
                <Button type="submit">Stop</Button>
            </form>
        </div>
    )
}

export default RequestStream