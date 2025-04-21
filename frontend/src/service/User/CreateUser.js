import fetchData from '../fetch'
import { host } from '../host'

export default async function createUser (data = {}) {
  const url = host + '/user'

  const { email = '', password = '', username = '', budget = 0, name = '' } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: { email, password, username, budget, name }
  }

  return await fetchData(url, options)
}
