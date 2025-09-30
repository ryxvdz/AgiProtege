const API_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", () => {
  // Botão criar cliente
  const btnCriar = document.getElementById("btnCriar");
  btnCriar.addEventListener("click", async () => {
    const cliente = {
      nome: document.getElementById("nome").value,
      cpf: document.getElementById("cpf").value,
      sexo: document.getElementById("sexo").value,
      email: document.getElementById("email").value,
      telefone: document.getElementById("telefone").value,
      renda: parseFloat(document.getElementById("renda").value),
      idade: parseInt(document.getElementById("idade").value),
      estadoCivil: document.getElementById("estadoCivil").value
    };

    try {
      const res = await fetch(`${API_URL}/cliente`, {
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

  // Botão buscar cliente
  const btnBuscar = document.getElementById("btnBuscar");
  btnBuscar.addEventListener("click", async () => {
    const id = document.getElementById("clienteId").value;

    try {
      const res = await fetch(`${API_URL}/cliente/${id}`);
      if (!res.ok) throw new Error("Cliente não encontrado");

      const data = await res.json();
      document.getElementById("respostaBuscar").innerText = JSON.stringify(data, null, 2);
    } catch (err) {
      document.getElementById("respostaBuscar").innerText = err.message;
    }
  });
});
