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

            System.out.print("Telefone (Apenas números): ");
            String telefone = scanner.nextLine();

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            // Coleta de Dados do Endereço
            System.out.println("\n--- Dados do Endereço ---");
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            System.out.print("Bairro: ");
            String bairro = scanner.nextLine();
            System.out.print("CEP: ");
            String cep = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();
            System.out.print("Estado: ");
            String estado = scanner.nextLine();

            Endereco endereco = new Endereco(rua, bairro, cep, cidade, estado);

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
            // O SetorResponsavelController PRECISA ter o método cadastrarSetorRapido(String, String)
            int novoId = setorController.cadastrarSetorRapido(nome, endereco);
            System.out.println("SUCESSO: Setor '" + nome + "' cadastrado com ID: " + novoId);
            return novoId;
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao cadastrar o Setor. " + e.getMessage());
            return -1;
        }
    }

    // --- MÉTODOS AUXILIARES E CRUD COMPLETOS ---

    /**
     * @Override: Exibe a lista de tutores de forma formatada.
     */
    private void listarTutores(List<Tutor> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNenhuma pessoa tutora cadastrada no momento.");
            return;
        }

        System.out.println("\n--- RELAÇÃO DE PESSOAS TUTORAS ---");
        System.out.printf("| %-4s | %-25s | %-15s | %-5s |\n", "ID", "NOME", "TELEFONE", "SETOR");
        System.out.println("----------------------------------------------------------");

        for (Tutor t : lista) {
            String setorDisplay = (t.getSetorResponsavelId() == -1) ? "N/A" : String.valueOf(t.getSetorResponsavelId());

            System.out.printf("| %-4d | %-25s | %-15s | %-5s |\n",
                    t.getId(),
                    t.getNome(),
                    t.getTelefone(),
                    setorDisplay);
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Auxiliar: Lista todos os Setores para que o usuário possa escolher um ID.
     */
    private void listarSetoresDisponiveis() {
        List<SetorResponsavel> lista = setorController.listarSetores();
        if (lista.isEmpty()) {
            System.out.println("Nenhum setor cadastrado.");
            return;
        }
        System.out.println("\n--- SETORES DISPONÍVEIS ---");
        System.out.printf("| %-4s | %-25s |\n", "ID", "NOME");
        System.out.println("------------------------------");
        for (SetorResponsavel s : lista) {
            System.out.printf("| %-4d | %-25s |\n", s.getId(), s.getNome());
        }
        System.out.println("------------------------------");
    }

    /**
     * Permite buscar um tutor por ID e alterar seus dados.
     */
    private void atualizarTutor() {
        System.out.print("\nDigite o ID do Tutor que deseja atualizar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Tutor tutorExistente = controller.buscarTutorPorId(id);

            if (tutorExistente == null) {
                System.out.println("ERRO: Tutor ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\n--- ATUALIZANDO TUTOR ID: " + id + " ---");
            System.out.println("Deixe em branco para manter o valor atual.");

            System.out.print("Novo Nome (" + tutorExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) tutorExistente.setNome(novoNome);

            // A lógica de atualização de endereço e outros campos seria similar

            controller.atualizarTutor(id, tutorExistente);
            System.out.println("SUCESSO: Tutor atualizado.");

        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite um número inteiro para o ID.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ERRO ao atualizar: " + e.getMessage());
        }
    }

    /**
     * Permite remover um tutor por ID, com confirmação.
     */
    private void removerTutor() {
        System.out.print("\nDigite o ID do Tutor que deseja remover: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Tutor tutorExistente = controller.buscarTutorPorId(id);

            if (tutorExistente == null) {
                System.out.println("ERRO: Tutor ID " + id + " não encontrado.");
                return;
            }

            System.out.print("Tem certeza que deseja remover o Tutor " + tutorExistente.getNome() + " (S/N)? ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                controller.removerTutor(id);
                System.out.println("SUCESSO: Tutor removido.");
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite um número inteiro para o ID.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ERRO ao remover: " + e.getMessage());
        }
    }
}