const API_URL = "http://localhost:8080/";

document.addEventListener("DOMContentLoaded", () => {
  // Criar Cliente
  document.getElementById("btnCriar").addEventListener("click", async () => {
    const cliente = {
      nome: document.getElementById("nome").value,
      cpf: document.getElementById("cpf").value,
      sexo: document.getElementById("sexo").value,
      email: document.getElementById("email").value,
      telefone: document.getElementById("telefone").value,
      renda: parseFloat(document.getElementById("renda").value),
      idade: parseInt(document.getElementById("idade").value),
      estadoCivil: document.getElementById("estado_civil").value,
      perfilRisco: document.getElementById("perfil_risco").value
    };

    try {
      const res = await fetch(`${API_URL}cliente`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente)
      });

      if (!res.ok) throw new Error("Erro ao criar cliente");

      const data = await res.json();
      document.getElementById("respostaCriar").innerText = JSON.stringify(data, null, 2);
    } catch (err) {
      document.getElementById("respostaCriar").innerText = err.message;
    }
  });

  // Buscar Cliente
  document.getElementById("btnBuscar").addEventListener("click", async () => {
    const id = document.getElementById("clienteIdBuscar").value;

    try {
      const res = await fetch(`${API_URL}cliente/${id}`);
      if (!res.ok) throw new Error("Cliente não encontrado");

      const data = await res.json();
      document.getElementById("respostaBuscar").innerText = JSON.stringify(data, null, 2);
    } catch (err) {
      document.getElementById("respostaBuscar").innerText = err.message;
    }
  });

  // Atualizar Cliente
  document.getElementById("btnAtualizar").addEventListener("click", async () => {
    const id = document.getElementById("clienteIdUpdate").value;
    const cliente = {
      nome: document.getElementById("nomeUpdate").value,
      cpf: document.getElementById("cpfUpdate").value,
      sexo: document.getElementById("sexoUpdate").value,
      email: document.getElementById("emailUpdate").value,
      telefone: document.getElementById("telefoneUpdate").value,
      renda: parseFloat(document.getElementById("rendaUpdate").value),
      idade: parseInt(document.getElementById("idadeUpdate").value),
      estadoCivil: document.getElementById("estadoCivilUpdate").value
    };

    try {
      const res = await fetch(`${API_URL}cliente/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente)
      });

      if (!res.ok) throw new Error("Erro ao atualizar cliente");

      const data = await res.json();
      document.getElementById("respostaUpdate").innerText = JSON.stringify(data, null, 2);
    } catch (err) {
      document.getElementById("respostaUpdate").innerText = err.message;
    }
  });

  // Deletar Cliente
  document.getElementById("btnDeletar").addEventListener("click", async () => {
    const id = document.getElementById("clienteIdDelete").value;

    try {
      const res = await fetch(`${API_URL}cliente/${id}`, { method: "DELETE" });
      if (res.status === 204) {
        document.getElementById("respostaDelete").innerText = "Cliente deletado com sucesso!";
      } else {
        throw new Error("Erro ao deletar cliente");
      }
    } catch (err) {
      document.getElementById("respostaDelete").innerText = err.message;
    }
  });
});