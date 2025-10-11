package view;

import controller.AnimalController;
import controller.SetorResponsavelController;
import model.Animal;
import model.SituacaoAtual;
import model.SetorResponsavel;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Menu de Linha de Comando (CLI) para gerenciamento da entidade Animal.
 */
public class AnimalView {

    private final AnimalController controller;
    private final SetorResponsavelController setorController;
    private final Scanner scanner;

    public AnimalView(AnimalController controller, SetorResponsavelController setorController, Scanner scanner) {
        this.controller = controller;
        this.setorController = setorController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR ANIMAIS ---");
            System.out.println("1. Cadastrar Novo Animal");
            System.out.println("2. Listar Todos os Animais");
            System.out.println("3. Atualizar Dados do Animal");
            System.out.println("4. Remover Animal");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print(">>> Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // CRÍTICO: Limpa a quebra de linha após o int

                switch (opcao) {
                    case 1: cadastrarAnimal(); break;
                    case 2: listarAnimais(controller.listarAnimais()); break;
                    case 3: atualizarAnimal(); break;
                    case 4: removerAnimal(); break;
                    case 0: System.out.println("\nVoltando ao menu principal..."); break;
                    default: System.out.println("\nOpção não reconhecida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Entrada inválida. Digite apenas o número da opção.");
                scanner.nextLine(); // CRÍTICO: Limpa o buffer após o erro
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarAnimal() {
        try {
            System.out.println("\n--- CADASTRO DE ANIMAL ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            // ... (restante das entradas de dados)
            System.out.print("Espécie (Ex: gato, cachorro): ");
            String especie = scanner.nextLine();

            System.out.print("Raça (Opcional): ");
            String raca = scanner.nextLine();

            System.out.print("Idade aproximada (Anos, número inteiro): ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Sexo (M/F): ");
            String sexo = scanner.nextLine();

            SituacaoAtual situacao = selecionarSituacao();

            // COLETA E VINCULAÇÃO VIA NOVO MENU
            int setorId = menuColetaSetor();

            Animal novoAnimal = new Animal(0, nome, especie, raca, idade, sexo, situacao, setorId);
            controller.adicionarAnimal(novoAnimal);

        } catch (IllegalArgumentException e) {
            System.out.println("\nERRO de Validação: Falha ao cadastrar: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada de dados inválida. Digite números onde solicitado.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("\nERRO: Ocorreu um erro no cadastro: " + e.getMessage());
        }
    }

    // --- LÓGICA DE VINCULAÇÃO CLARA ---
    private int menuColetaSetor() {
        int opcao;

        do {
            System.out.println("\n--- VINCULAR SETOR RESPONSÁVEL ---");
            System.out.println("1. Vincular a um Setor Existente (Buscar por ID)");
            System.out.println("2. Cadastrar Novo Setor (Rápido)");
            System.out.println("3. Listar Setores Disponíveis");
            System.out.println("0. NÃO VINCULAR (Deixar em aberto)");
            System.out.print(">>> Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Digite apenas o número da opção.");
                scanner.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    int idExistente = vincularSetorExistente();
                    if (idExistente > 0) return idExistente;
                    break;
                case 2:
                    int idNovo = cadastrarSetorRapidoInterno();
                    if (idNovo > 0) return idNovo;
                    break;
                case 3:
                    listarSetoresDisponiveis();
                    break;
                case 0:
                    System.out.println("Vínculo de Setor ignorado. Continua sem Setor Responsável.");
                    return -1; // Retorna -1 para 'Não Vinculado'
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (true);
    }

    private int vincularSetorExistente() {
        System.out.print("Digite o ID do Setor para vincular: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            SetorResponsavel setor = setorController.buscarSetorPorId(id);

            if (setor != null) {
                System.out.println("SUCESSO: Vinculado ao Setor ID " + id + " (" + setor.getNome() + ").");
                return id;
            } else {
                System.out.println("ERRO: Setor ID " + id + " não encontrado. Tente novamente.");
                return -1;
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO: ID inválido. Digite um número inteiro.");
            scanner.nextLine();
            return -1;
        }
    }

    private int cadastrarSetorRapidoInterno() {
        System.out.println("\n--- CADASTRO RÁPIDO DE NOVO SETOR ---");
        System.out.print("Nome do Novo Setor: ");
        String nome = scanner.nextLine();

        System.out.print("Localização/Endereço: ");
        String endereco = scanner.nextLine();

        try {
            int novoId = setorController.cadastrarSetorRapido(nome, endereco);
            System.out.println("SUCESSO: Setor '" + nome + "' cadastrado com ID: " + novoId);
            return novoId;
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao cadastrar o Setor. " + e.getMessage());
            return -1;
        }
    }

    // --- MÉTODOS AUXILIARES (Implementação resumida) ---
    private SituacaoAtual selecionarSituacao() { /* Implementação de seleção de enum */ return SituacaoAtual.EM_OBSERVACAO; }
    private void listarSetoresDisponiveis() { /* Implementação de listagem dos setores */ }
    private void listarAnimais(List<Animal> lista) { /* Implementação de listagem dos animais */ }
    private void atualizarAnimal() { /* Implementação de atualização */ }
    private void removerAnimal() { /* Implementação de remoção */ }
}