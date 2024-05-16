import java.util.Scanner;

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
        int opcao;
        cabecalho();
        System.out.println("1 - Verificar Mesas");
        System.out.println("2 - Verificar Fila");
        System.out.println("3 - Solicitar Mesa");
        System.out.println("4 - Encerrar Mesa");
        System.out.println("5 - Processar Fila");
        System.out.println("6 - Exibir Cardápio");
        System.out.println("7 - Adicionar Item ao Pedido");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
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
        int idCli;
        cabecalho();
        System.out.print("Digite o id do cliente: ");
        idCli = Integer.parseInt(teclado.nextLine());
        Cliente localizado = restaurante.localizarCliente(idCli);
        if (localizado == null) {
            System.out.println("Cliente não localizado. Vamos cadastrá-lo:");
            localizado = cadastrarNovoCliente();
            restaurante.addCliente(localizado);
        }
        criarRequisicao(localizado);
    }

    static Cliente cadastrarNovoCliente() {
        String nome;
        System.out.print("Nome do cliente: ");
        nome = teclado.nextLine();
        Cliente novo = new Cliente(nome);
        System.out.println("Cliente cadastrado: " + novo);
        pausa();
        return novo;
    }

    static <teclado> void criarRequisicao(Cliente cliente) {
        int quantasPessoas;
        cabecalho();
        System.out.print("Para quantas pessoas será a mesa? ");
        quantasPessoas = Integer.parseInt(teclado.nextLine());
        Requisicao novaRequisicao = new Requisicao(quantasPessoas, cliente);
        restaurante.registrarRequisicao(novaRequisicao);
        novaRequisicao = processarFila();
        if (novaRequisicao != null) {
            System.out.println(novaRequisicao);
        } else {
            System.out.println("Não há mesas disponíveis no momento. Requisição em espera");
        }
        pausa();
    }

    static Requisicao processarFila() {
        return restaurante.processarFila();
    }

    static void encerrarMesa() {
        int idMesa;
        cabecalho();
        System.out.print("Qual o número da mesa para encerrar atendimento? ");
        idMesa = Integer.parseInt(teclado.nextLine());
        Requisicao finalizada = restaurante.encerrarAtendimento(idMesa);
        if (finalizada != null) {
            System.out.println(finalizada);
        } else {
            System.out.println("Mesa " + idMesa + " não está em atendimento");
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
        System.out.print("Digite o nome do item: ");
        String nomeItem = teclado.nextLine();
        if (restaurante.adicionarItemAoPedido(idMesa, nomeItem)) {
            System.out.println("Item adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar item. Verifique o nome do item e a mesa.");
        }
        pausa();
    }

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in);
        int opcao;
        restaurante = new Restaurante();
        do {
            opcao = menuPrincipal();
            switch (opcao) {
                case 1 -> exibirMesas();
                case 2 -> mostrarFila();
                case 3 -> solicitarMesa();
                case 4 -> encerrarMesa();
                case 5 -> {
                    Requisicao atendida = processarFila();
                    if (atendida != null) {
                        System.out.println(atendida);
                    } else {
                        System.out.println("Fila vazia ou mesas não disponíveis. Favor verificar a situação.");
                    }
                    pausa();
                }
                case 6 -> exibirCardapio();
                case 7 -> adicionarItemAoPedido();
            }
        } while (opcao != 0);
        teclado.close();
    }
}