import { host } from '../host'

export default async function editTransaction (data = {}) {
  const url = host + '/transaction'

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      authorization: `Bearer ${localStorage.getItem('token')}`
    },
    body: data
  }

  return await fetch(url, options)
}
