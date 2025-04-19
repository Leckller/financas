import fetchData from '../fetch'
import { host } from '../host'

export default async function listTransaction () {
  const url = host + '/transaction'

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      authorization: `Bearer ${localStorage.getItem('token')}`
    },
    body: {}
  }

  return await fetchData(url, options)
}
