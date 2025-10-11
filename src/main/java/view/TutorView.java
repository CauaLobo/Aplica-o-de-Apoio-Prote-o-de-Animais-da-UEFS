package view;

import controller.TutorController;
import controller.SetorResponsavelController;
import model.Tutor;
import model.Endereco;
import model.SetorResponsavel;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

/**
 * Menu de Linha de Comando (CLI) para gerenciamento da entidade Tutor.
 */
public class TutorView {

    private final TutorController controller;
    private final SetorResponsavelController setorController;
    private final Scanner scanner;

    public TutorView(TutorController controller, SetorResponsavelController setorController, Scanner scanner) {
        this.controller = controller;
        this.setorController = setorController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR PESSOAS TUTORAS ---");
            System.out.println("1. Cadastrar Novo Tutor");
            System.out.println("2. Listar Todos os Tutores");
            System.out.println("3. Atualizar Dados do Tutor");
            System.out.println("4. Remover Tutor");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print(">>> Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // CRÍTICO: Limpa a quebra de linha após o int

                switch (opcao) {
                    case 1: cadastrarTutor(); break;
                    case 2: listarTutores(controller.listarTutores()); break;
                    case 3: atualizarTutor(); break;
                    case 4: removerTutor(); break;
                    case 0: System.out.println("\nVoltando ao menu principal..."); break;
                    default: System.out.println("\nOpção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Entrada inválida. Digite apenas o número da opção.");
                scanner.nextLine(); // CRÍTICO: Limpa o buffer após o erro
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarTutor() {
        try {
            System.out.println("\n--- NOVO CADASTRO DE TUTOR ---");
            System.out.print("Nome Completo: ");
            String nome = scanner.nextLine();
            // ... (restante das entradas de dados)
            System.out.print("Telefone (Apenas números): ");
            String telefone = scanner.nextLine();

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            // Coleta de Dados do Endereço (Implementação resumida)
            Endereco endereco = new Endereco("", "", "", "", "");

            int setorId = menuColetaSetor();

            Tutor novoTutor = new Tutor(0, nome, endereco, telefone, email, setorId);
            controller.adicionarTutor(novoTutor);

        } catch (IllegalArgumentException e) {
            System.out.println("\nERRO de Validação: Falha ao cadastrar: " + e.getMessage());
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
    private void listarSetoresDisponiveis() { /* Implementação de listagem dos setores */ }
    private void listarTutores(List<Tutor> lista) { /* Implementação de listagem dos tutores */ }
    private void atualizarTutor() { /* Implementação de atualização */ }
    private void removerTutor() { /* Implementação de remoção */ }
}