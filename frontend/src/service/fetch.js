import Swal from 'sweetalert2'

export default async function fetchData (url, options = {}) {
  const { method = 'GET', headers = {}, body } = options
  const isGet = method.toUpperCase() === 'GET'

  const response = await fetch(url, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers
    },
    ...(isGet ? {} : { body: JSON.stringify(body) })
  })

  const json = await response.json()

  if (!response.ok) {
    const text = Object.values(json).join('\n')
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text
    })
    throw new Error(text)
  }

  return json
}
