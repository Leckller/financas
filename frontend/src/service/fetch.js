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
  if (!response.ok) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: `Something went wrong! ${response.statusText}`
    })
  }
  return await response.json()
}
