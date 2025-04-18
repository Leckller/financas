import { host } from '../host'

export default async function createTransaction (data = {}) {
  const url = host + '/transaction'

  const { name = '', amount = 0 } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      authorization: `Bearer ${localStorage.getItem('token')}`
    },
    body: { name, amount }
  }

  return await fetch(url, options)
}
