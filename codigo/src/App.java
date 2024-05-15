import java.util.Scanner;

/** 
 * MIT License
 *
 * Copyright(c) 2024 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

 /**
  * App para o LPM Comidas Veganas, sprint 1 (cadastrar cliente e atender requisições por mesas)
  */
public class App {

    static Scanner teclado;
    static Restaurante restaurante;

    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Cria uma mensagem de pausa para a CLI
     */
    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    /**
     * Cabeçalho para os diversos menus do restaurante
     */
    static void cabecalho() {
        limparTela();
        System.out.println(" LPM VEGAN - v0.1 ");
        System.out.println("=====================");
    }

    /***
     * Menu com as principais opções do restaurante. Retorna a escolha do usuário
     * @return Int com a escolha do usuário, sem tratamento de dados
     */
    static int menuPrincipal() {
        int opcao;
        cabecalho();    
        System.out.println("1 - Verificar Mesas");
        System.out.println("2 - Verificar Fila");
        System.out.println("3 - Solicitar Mesa");
        System.out.println("4 - Encerrar Mesa");
        System.out.println("5 - Processar Fila");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    /**
     * Método para encapsular a exibição da fila de espera do restaurante
     */
    static void mostrarFila(){
        cabecalho();
        System.out.println(restaurante.filaDeEspera());
        pausa();
    }

    /**
     * Método para encapsular a exibição do relatório de mesas do restaurante
     */
    static void exibirMesas() {
        cabecalho();
        System.out.println(restaurante.statusMesas());
        pausa();
    }
    
    /**
     * Encapsula o processo de solicitar mesa: localizar cliente, cadastrar cliente se não for localizado e chamar a criação da requisição
     */
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

    /**
     * Encapsula o processo de cadastrar um novo cliente. Exibe e retorna o cliente cadastrado
     * @return Cliente cadastrado neste momento.
     */
    static Cliente cadastrarNovoCliente() {
        String nome;
        System.out.print("Nome do cliente: ");
        nome = teclado.nextLine();
        Cliente novo = new Cliente(nome);
        System.out.println("Cliente cadastrado: " + novo);
        pausa();
        return novo;
    }

    /**
     * Encapsula o processo de criar uma requisição para o cliente. Pergunta para quantas pessoas é a requisição, faz seu registro
     * e chama o processamento da fila para atendê-la, se possível. Exibe o resultado desta tentativa.
     * @param cliente Cliente que está solicitando a requisição. Não deve ser nulo.
     */
    static void criarRequisicao(Cliente cliente) {
        int quantasPessoas;
        cabecalho();
        System.out.print("Para quantas pessoas será a mesa? ");
        quantasPessoas = Integer.parseInt(teclado.nextLine());
        Requisicao novaRequisicao = new Requisicao(quantasPessoas, cliente);
        restaurante.registrarRequisicao(novaRequisicao);
        novaRequisicao = processarFila();
        if(novaRequisicao!=null){
            System.out.println(novaRequisicao);
        } else {
            System.out.println("Não há mesas disponíveis no momento. Requisição em espera");
        }
        pausa();
    }

    /**
     * Encapsula a chamada para processar a fila de espera no restaurante, retornando a requisição atendida
     * @return Requisição atendida, ou nulo, caso não possa ser atendida ou não haja requisição na fila.
     */
    static Requisicao processarFila() {
       return restaurante.processarFila();
    }

    /**
     * Encapsula o processo de encerrar uma requisição. Envia o pedido de encerramento para o restaurante e exibe os dados da requisição,
     * caso ela exista e tenha sido encerrada neste momento.
     */
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
                    if(atendida!=null){
                        System.out.println(atendida);
                    }
                    else{
                        System.out.println("Fila vazia ou mesas não disponíveis. Favor verificar a situação.");
                    }
                    pausa();
                }

            }
        } while (opcao != 0);
        teclado.close();
    }
}
