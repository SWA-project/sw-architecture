import axios from "axios"

export const sendOrder = async (newObject) => {
  const response = await axios.post("http://localhost:9192/orders", newObject)
  return response.data
}

export const fetchOrders = async () => {
    const response = await axios.get(`http://localhost:9192/orders/`)
    return response.data
}

export const fetchOrder = async (id) => {
    const response = await axios.get(`http://localhost:9192/orders/${id}`)
    return response.data
}