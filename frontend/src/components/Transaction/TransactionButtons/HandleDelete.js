import deleteTransaction from '@/service/Transaction/deleteTransaction'
import Swal from 'sweetalert2'

export const handleDelete = async (id, amount, name, transaction) => {
  const result = await Swal.fire({
    title: `Tem certeza que quer apagar a transação "${name}"?`,
    text: 'Essa ação não pode ser revertida!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    cancelButtonText: 'Cancelar',
    confirmButtonText: 'Sim, exclua-o!'
  })

  if (result.isConfirmed) {
    try {
      await deleteTransaction(id)

      // Reverter o efeito da transação no saldo
      transaction.balance -= amount

      // Remover do estado local
      transaction.deleteTransaction(id)

      Swal.fire({
        title: 'Deletado!',
        text: 'A transação foi deletada com sucesso.',
        icon: 'success'
      })
    } catch (error) {
      console.log(error)
    }
  }
}
