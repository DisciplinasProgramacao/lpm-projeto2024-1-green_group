Classe Cliente: Representa os clientes que chegam ao restaurante.

Atributos: nome, quantidadePessoas
Métodos: fazerRequisicao()
Classe Mesa: Representa as mesas disponíveis no restaurante.

Atributos: idMesa, capacidade, status (livre/ocupada)
Métodos: alocar(), liberar()
Classe Requisicao: Representa a requisição de uma mesa por um cliente.

Atributos: idRequisicao, dataEntrada, horaEntrada, horaSaida, cliente (associado à Classe Cliente), mesa (associado à Classe Mesa)
Métodos: criarRequisicao(), fecharRequisicao()
Classe Restaurante: Gerencia as operações do restaurante, incluindo mesas e requisições.

Atributos: mesas (lista de Mesas), filaEspera (fila de Requisicoes)
Métodos: alocarMesa(), adicionarFilaEspera(), processarFilaEspera()

@startuml
class Restaurante {
    - mesasDisponiveis: List<Mesa>
    - filaEspera: FilaClientes
    + adicionarCliente(cliente: Cliente): void
    + alocarMesa(cliente: Cliente): void
    + liberarMesa(mesa: Mesa): void
}

class Mesa {
    - capacidade: int
    - clientesAlocados: List<Cliente>
    + adicionarCliente(cliente: Cliente): boolean
    + removerCliente(cliente: Cliente): void
    + isCheia(): boolean
}

class Cliente {
    - nome: String
    - numeroPessoas: int
    - horaEntrada: Date
    - horaSaida: Date
    + calcularTempoPermanencia(): int
    + registrarSaida(): void
}

class FilaClientes {
    - clientes: List<Cliente>
    + adicionarCliente(cliente: Cliente): void
    + removerCliente(cliente: Cliente): void
    + proximoCliente(): Cliente
}

Restaurante -- Mesa: possui
Restaurante -- FilaClientes: possui
Mesa "4" -- "1" Cliente: alocados
FilaClientes "0..*" -- "1" Cliente: clientes
@enduml

