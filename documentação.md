# Documentação do Sistema de Gerenciamento de Restaurante

## Classes

### 1. App
Classe principal que gerencia a interação com o usuário e o restaurante.

#### Métodos
- `limparTela()`: Limpa a tela do console.
- `pausa()`: Pausa o programa até que o usuário pressione Enter.
- `cabecalho()`: Exibe o cabeçalho do aplicativo.
- `menuPrincipal()`: Exibe o menu principal e retorna a opção escolhida pelo usuário.
- `mostrarFila()`: Mostra a fila de espera do restaurante.
- `exibirMesas()`: Exibe o status das mesas.
- `solicitarMesa()`: Solicita uma mesa para um cliente.
- `cadastrarNovoCliente()`: Cadastra um novo cliente.
- `criarRequisicao(Cliente cliente)`: Cria uma requisição de mesa para um cliente.
- `encerrarMesa()`: Encerra o atendimento de uma mesa.
- `exibirCardapio()`: Exibe o cardápio.
- `adicionarItemAoPedido()`: Adiciona um item ao pedido de uma mesa.
- `adicionarItemAoPedidoFechado()`: Adiciona um item ao pedido fechado de uma mesa.
- `main(String[] args)`: Método principal que inicia o programa.

### 2. Cardapio
Classe que representa o cardápio do restaurante.

#### Atributos
- `Map<Integer, Item> itens`: Mapa que armazena os itens do cardápio.
- `int ultimoCodigo`: Código do último item adicionado.

#### Métodos
- `Cardapio()`: Construtor que inicializa o cardápio e carrega os itens.
- `carregarItens()`: Carrega os itens do cardápio.
- `addItem(String nome, double preco)`: Adiciona um item ao cardápio.
- `getItem(int codigo)`: Retorna o item correspondente ao código fornecido.
- `toString()`: Retorna uma representação em string do cardápio.

### 3. CardapioFechado
Classe que representa o cardápio fechado do restaurante, com itens restritos.

#### Métodos
- `carregarItens()`: Carrega os itens restritos do cardápio fechado.

### 4. Cliente
Classe que representa um cliente do restaurante.

#### Atributos
- `int ultimoID`: Código do último cliente cadastrado.
- `String nomeCliente`: Nome do cliente.
- `int idCliente`: Identificador do cliente.

#### Métodos
- `Cliente(String nome)`: Construtor que inicializa o cliente com um nome e um identificador.
- `toString()`: Retorna uma representação em string do cliente.
- `hashCode()`: Retorna o hash code do cliente.

### 5. Item
Classe que representa um item do cardápio.

#### Atributos
- `String nome`: Nome do item.
- `double preco`: Preço do item.

#### Métodos
- `Item(String nome, double preco)`: Construtor que inicializa o item com um nome e um preço.
- `getNome()`: Retorna o nome do item.
- `getPreco()`: Retorna o preço do item.
- `toString()`: Retorna uma representação em string do item.

### 6. Mesa
Classe que representa uma mesa do restaurante.

#### Atributos
- `int ultimoID`: Código da última mesa criada.
- `int idMesa`: Identificador da mesa.
- `int capacidade`: Capacidade da mesa.
- `boolean ocupada`: Indica se a mesa está ocupada.

#### Métodos
- `Mesa(int capacidade)`: Construtor que inicializa a mesa com uma capacidade e um identificador.
- `ocupar()`: Marca a mesa como ocupada.
- `desocupar()`: Marca a mesa como desocupada.
- `estahLiberada(int quantPessoas)`: Verifica se a mesa está liberada para uma determinada quantidade de pessoas.
- `getIdMesa()`: Retorna o identificador da mesa.
- `toString()`: Retorna uma representação em string da mesa.

### 7. Pedido
Classe que representa um pedido no restaurante.

#### Atributos
- `List<Item> itens`: Lista de itens do pedido.

#### Métodos
- `Pedido()`: Construtor que inicializa o pedido.
- `adicionarItem(Item item)`: Adiciona um item ao pedido.
- `calcularTotal()`: Calcula o total do pedido.
- `toString()`: Retorna uma representação em string do pedido.

### 8. PedidoFechado
Classe que representa um pedido fechado no restaurante, com itens e quantidades restritas.

#### Atributos
- `int MAX_COMIDAS`: Número máximo de comidas permitidas no pedido fechado.
- `int MAX_BEBIDAS`: Número máximo de bebidas permitidas no pedido fechado.
- `double PRECO_FIXO`: Preço fixo do pedido fechado.
- `int contComidas`: Contador de comidas no pedido fechado.
- `int contBebidas`: Contador de bebidas no pedido fechado.

#### Métodos
- `PedidoFechado()`: Construtor que inicializa o pedido fechado.
- `adicionarItem(Item item)`: Adiciona um item ao pedido fechado, verificando as restrições de quantidade e tipo.
- `calcularTotal()`: Calcula o total do pedido fechado, retornando o preço fixo.
- `toString()`: Retorna uma representação em string do pedido fechado.

### 9. Requisicao
Classe que representa uma requisição de mesa no restaurante.

#### Atributos
- `Cliente cliente`: Cliente que fez a requisição.
- `Mesa mesa`: Mesa alocada para a requisição.
- `int quantPessoas`: Quantidade de pessoas na requisição.
- `LocalDateTime entrada`: Data e hora de entrada.
- `LocalDateTime saida`: Data e hora de saída.
- `boolean encerrada`: Indica se a requisição foi encerrada.
- `Pedido pedido`: Pedido associado à requisição.

#### Métodos
- `Requisicao(int quantPessoas, Cliente cliente)`: Construtor que inicializa a requisição.
- `encerrar()`: Encerra a requisição e desocupa a mesa.
- `alocarMesa(Mesa mesa)`: Aloca uma mesa para a requisição.
- `estahEncerrada()`: Verifica se a requisição está encerrada.
- `ehDaMesa(int idMesa)`: Verifica se a requisição pertence a uma determinada mesa.
- `getQuantPessoas()`: Retorna a quantidade de pessoas na requisição.
- `adicionarAoPedido(Item item)`: Adiciona um item ao pedido da requisição.
- `setPedido(Pedido pedido)`: Define um pedido para a requisição.
- `toString()`: Retorna uma representação em string da requisição.

### 10. Restaurante
Classe que representa o restaurante, gerenciando mesas, clientes e pedidos.

#### Atributos
- `int MAX_FILA`: Número máximo de requisições na fila de espera.
- `int MAX_MESAS`: Número máximo de mesas no restaurante.
- `int MAX_CLIENTES`: Número máximo de clientes cadastrados.
- `List<Mesa> mesas`: Lista de mesas do restaurante.
- `Map<Integer, Cliente> clientes`: Mapa de clientes do restaurante.
- `List<Requisicao> atendidas`: Lista de requisições atendidas.
- `List<Requisicao> espera`: Lista de requisições na fila de espera.
- `int quantClientes`: Quantidade de clientes cadastrados.
- `Cardapio cardapio`: Cardápio do restaurante.
- `CardapioFechado cardapioFechado`: Cardápio fechado do restaurante.

#### Métodos
- `Restaurante()`: Construtor que inicializa o restaurante.
- `criarMesas()`: Cria as mesas do restaurante.
- `addCliente(Cliente novo)`: Adiciona um novo cliente ao restaurante.
- `localizarCliente(int idCli)`: Localiza um cliente pelo seu identificador.
- `localizarMesaDisponivel(int quantPessoas)`: Localiza uma mesa disponível para uma determinada quantidade de pessoas.
- `encerrarAtendimento(int idMesa)`: Encerra o atendimento de uma mesa.
- `processarFila()`: Processa a fila de requisições.
- `retirarDaFila(int pos)`: Retira uma requisição da fila de espera.
- `registrarRequisicao(Requisicao novaRequisicao)`: Registra uma nova requisição na fila de espera.
- `atenderRequisicao(Requisicao requisicao, Mesa mesa)`: Atende uma requisição alocando uma mesa.
- `statusMesas()`: Retorna o status das mesas.
- `filaDeEspera()`: Retorna a fila de espera.
- `exibirCardapio()`: Exibe o cardápio.
- `exibirCardapioFechado()`: Exibe o cardápio fechado.
- `adicionarItemAoPedido(int idMesa, int codigoItem)`: Adiciona um item ao pedido de uma mesa.
- `adicionarItemAoPedidoFechado(int idMesa, int codigoItem, int quantidade)`: Adiciona um item ao pedido fechado de uma mesa.
- `localizarRequisicao(int idMesa)`: Localiza uma requisição para uma determinada mesa.
