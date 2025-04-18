import { host } from '../host'

export default async function deleteTransaction (id) {
  const url = host + '/transaction/' + id

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      authorization: `Bearer ${localStorage.getItem('token')}`
    },
    body: {}
  }

  return await fetch(url, options)
}
