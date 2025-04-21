import fetchData from '../fetch'
import { host } from '../host'

export default async function createTransaction (data = {}) {
  const url = host + '/transaction'

  const { name = '', amount = 0 } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      authorization: `${localStorage.getItem('authToken')}`
    },
    body: { name, amount }
  }

  return await fetchData(url, options)
}
