import fetchData from '../fetch'
import { host } from '../host'

export default async function createTag (data = {}) {
  const url = host + '/tag'

  const { name = '', color = '' } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      authorization: `${localStorage.getItem('authToken')}`
    },
    body: { name, color }
  }

  return await fetchData(url, options)
}
