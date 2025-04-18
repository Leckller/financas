import { host } from '../host'

export default async function createUser (data = {}) {
  const url = host + '/user'

  const { email = '', password = '', username = '' } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: { email, password, username }
  }

  return await fetch(url, options)
}
