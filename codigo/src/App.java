import java.util.Scanner;

/**
 * Classe principal que gerencia a interação com o usuário e o restaurante.
 */
public class App {

    static Scanner teclado;
    static Restaurante restaurante;

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println(" LPM VEGAN - v0.1 ");
        System.out.println("=====================");
    }

    static int menuPrincipal() {
        cabecalho();
        System.out.println("1 - Verificar Mesas");
        System.out.println("2 - Verificar Fila");
        System.out.println("3 - Solicitar Mesa");
        System.out.println("4 - Encerrar Mesa");
        System.out.println("5 - Processar Fila");
        System.out.println("6 - Exibir Cardápio");
        System.out.println("7 - Adicionar Item ao Pedido");
        System.out.println("8 - Adicionar Item ao Pedido Fechado");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    static void mostrarFila() {
        cabecalho();
        System.out.println(restaurante.filaDeEspera());
        pausa();
    }

    static void exibirMesas() {
        cabecalho();
        System.out.println(restaurante.statusMesas());
        pausa();
    }

    static void solicitarMesa() {
        cabecalho();
        System.out.print("Digite o id do cliente: ");
        int idCli = Integer.parseInt(teclado.nextLine());
        Cliente localizado = restaurante.localizarCliente(idCli);
        if (localizado == null) {
            System.out.println("Cliente não localizado. Vamos cadastrá-lo:");
            localizado = cadastrarNovoCliente();
            restaurante.addCliente(localizado);
        }
        criarRequisicao(localizado);
    }

    static Cliente cadastrarNovoCliente() {
        cabecalho();
        System.out.print("Nome do cliente: ");
        String nome = teclado.nextLine();
        Cliente novo = new Cliente(nome);
        System.out.println("Cliente cadastrado: " + novo);
        pausa();
        return novo;
    }

    static void criarRequisicao(Cliente cliente) {
        cabecalho();
        System.out.print("Para quantas pessoas será a mesa? ");
        int quantasPessoas = Integer.parseInt(teclado.nextLine());
        Requisicao novaRequisicao = new Requisicao(quantasPessoas, cliente);
        restaurante.registrarRequisicao(novaRequisicao);
        novaRequisicao = restaurante.processarFila();
        if (novaRequisicao != null) {
            System.out.println(novaRequisicao);
        } else {
            System.out.println("Não há mesas disponíveis no momento. Requisição em espera");
        }
        pausa();
    }

    static void encerrarMesa() {
        cabecalho();
        System.out.print("Qual o número da mesa para encerrar atendimento? ");
        int idMesa = Integer.parseInt(teclado.nextLine());
        try {
            Requisicao finalizada = restaurante.encerrarAtendimento(idMesa);
            System.out.println(finalizada);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        pausa();
    }

    static void exibirCardapio() {
        cabecalho();
        System.out.println(restaurante.exibirCardapio());
        pausa();
    }

    static void adicionarItemAoPedido() {
        cabecalho();
        System.out.print("Digite o número da mesa: ");
        int idMesa = Integer.parseInt(teclado.nextLine());
        exibirCardapio();
        System.out.print("Digite o código do item: ");
        int codigoItem = Integer.parseInt(teclado.nextLine());
        try {
            if (restaurante.adicionarItemAoPedido(idMesa, codigoItem)) {
                System.out.println("Item adicionado com sucesso!");
            } else {
                System.out.println("Erro ao adicionar item. Verifique o código do item e a mesa.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        pausa();
    }

    static void adicionarItemAoPedidoFechado() {
        cabecalho();
        System.out.print("Digite o número da mesa: ");
        int idMesa = Integer.parseInt(teclado.nextLine());
        System.out.println(restaurante.exibirCardapioFechado());
        System.out.print("Digite o código do item: ");
        int codigoItem = Integer.parseInt(teclado.nextLine());
        System.out.print("Digite a quantidade: ");
        int quantidade = Integer.parseInt(teclado.nextLine());
        try {
            if (restaurante.adicionarItemAoPedidoFechado(idMesa, codigoItem, quantidade)) {
                System.out.println("Item adicionado com sucesso!");
            } else {
                System.out.println("Erro ao adicionar item. Verifique o código do item, a mesa e a quantidade.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        pausa();
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        restaurante = new Restaurante();
        int opcao;
        do {
            opcao = menuPrincipal();
            switch (opcao) {
                case 1 -> exibirMesas();
                case 2 -> mostrarFila();
                case 3 -> solicitarMesa();
                case 4 -> encerrarMesa();
                case 5 -> {
                    try {
                        Requisicao atendida = restaurante.processarFila();
                        if (atendida != null) {
                            System.out.println(atendida);
                        } else {
                            System.out.println("Fila vazia ou mesas não disponíveis. Favor verificar a situação.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    pausa();
                }
                case 6 -> exibirCardapio();
                case 7 -> adicionarItemAoPedido();
                case 8 -> adicionarItemAoPedidoFechado();
            }
        } while (opcao != 0);
        teclado.close();
    }
}
