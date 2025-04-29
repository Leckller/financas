import fetchData from '../fetch'
import { host } from '../host'

export default async function createTransactionWithTags (data = {}) {
  const url = host + '/tag/transaction'

  const { name = '', amount = 0, tags = [] } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      authorization: `${localStorage.getItem('authToken')}`
    },
    body: { name, amount, tags }
  }

  return await fetchData(url, options)
}
