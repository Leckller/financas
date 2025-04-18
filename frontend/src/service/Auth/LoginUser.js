import { host } from '../host'

export default async function loginUser (data = {}) {
  const url = host + '/auth/login'

  const { password = '', username = '' } = data

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: { password, username }
  }

  return await fetch(url, options)
}
