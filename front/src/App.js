import React, { useEffect, useState } from 'react'
import CreditOrder from './components/CreditOrder';
import RequestStream from './components/RequestStream';
import { fetchOrders } from './services/bankService';
import Styles from './Styles';


const App = () => {
  const [verdicts, setVerdicts] = useState([])

      
  useEffect(()=> {
      initVerdicts()
  }, [verdicts])

  const initVerdicts = async () => {
    let fetchedVerdicts = []

    try {
      fetchedVerdicts = await fetchOrders()
    } catch (error) {
      console.log(error)
    }

    setVerdicts(fetchedVerdicts)
  }

    return (
    <div style={Styles.container}>
        <h2>Test environment</h2>
        <div style={Styles.containerChild}>
          <CreditOrder verdicts={verdicts} />
        </div>
        <div class={Styles.containerChild}>
          <RequestStream />
        </div>
    </div>
    )
}

export default App;
