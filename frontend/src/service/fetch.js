import Swal from 'sweetalert2'

export default async function fetchData (url, options = {}) {
  const { method = 'GET', headers = {}, body } = options

  const response = await fetch(url, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers
    },
    body: JSON.stringify(body)
  })

  const json = await response.json()

  if (response.status !== 200 && response.status !== 201) {
    const text = Object.values(json).map(t => t + '\n')
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text
    })
    throw new Error()
  }

  return json
}
