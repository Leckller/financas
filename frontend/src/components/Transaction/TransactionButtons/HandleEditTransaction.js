import Swal from 'sweetalert2'

export const handleEditTransaction = async (id, amound, name) => {
  const { value: formValues } = await Swal.fire({
    title: `Editar tag "${name}"`,
    html: `
      <div class="swal-form-fields">
        <p>Nome:</p>  
        <input id="swal-input-name" class="swal2-input" placeholder="Nome da transação" value=${name}>
        <p>Valor:</p>
        <input id="swal-input-amount" min="1" type="number" class="swal2-input" placeholder="Valor" value=${amound}>
      </div>
      <div class="swal-form-type">
        <button type="button" id="btn-income" class="swal2-confirm btn swal2-styled" style="background-color: #28a745;">Receita</button>
        <button type="button" id="btn-expense" class="swal2-confirm btn swal2-styled" style="background-color: #dc3545;">Despesa</button>
      </div>
      <div id="swal-form-add-tag">
        <button type="button" id="btn-add-tag" class="swal2-confirm btn swal2-styled">Adicionar Tag</button>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: 'Salvar',

    didOpen: () => {
    },

    preConfirm: () => {
    }
  })

  if (formValues) {
    try {
      // todo
    } catch (e) {
      console.log(e)
    }
  }
}
