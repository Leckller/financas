import fetchData from '../fetch'
import { host } from '../host'

export default async function listTags () {
  const url = host + '/tag'

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      authorization: `${localStorage.getItem('authToken')}`
    }
  }

  return await fetchData(url, options, true)
}
