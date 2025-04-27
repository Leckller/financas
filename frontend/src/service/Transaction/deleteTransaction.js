import fetchData from '../fetch'
import { host } from '../host'

export default async function deleteTransaction (id) {
  const url = host + '/transaction/' + id

  const options = {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      authorization: `Bearer ${localStorage.getItem('authToken')}`
    },
    body: {}
  }

  return await fetchData(url, options)
}
