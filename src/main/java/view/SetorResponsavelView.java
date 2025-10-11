package view;

import controller.SetorResponsavelController;
import controller.TutorController;
import model.SetorResponsavel;
import model.Tutor;

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

/**
 * Menu de Linha de Comando (CLI) para gerenciamento da entidade SetorResponsavel.
 */
public class SetorResponsavelView {

    private final SetorResponsavelController controller;
    private final TutorController tutorController;
    private final Scanner scanner;

    public SetorResponsavelView(SetorResponsavelController controller, TutorController tutorController, Scanner scanner) {
        this.controller = controller;
        this.tutorController = tutorController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR SETORES RESPONSÁVEIS ---");
            System.out.println("1. Cadastrar Novo Setor");
            System.out.println("2. Listar Todos os Setores");
            System.out.println("3. Atualizar Dados do Setor");
            System.out.println("4. Remover Setor");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print(">>> Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1: cadastrarSetor(); break;
                    case 2: listarSetores(controller.listarSetores()); break;
                    case 3: atualizarSetor(); break;
                    case 4: removerSetor(); break;
                    case 0: System.out.println("\nVoltando ao menu principal..."); break;
                    default: System.out.println("\nOpção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Entrada inválida. Digite apenas o número da opção.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarSetor() {

        List<Tutor> tutores = tutorController.listarTutores();

        // Verifica se há tutores cadastrados antes de permitir o cadastro de setores
        if (tutores.isEmpty()) {
            System.out.println("\nERRO: É necessário cadastrar pelo menos um tutor antes de cadastrar setores responsáveis.");
            return;
        }

        try {
            System.out.println("\n--- NOVO CADASTRO DE SETOR ---");
            System.out.print("Nome do Setor: ");
            String nome = scanner.nextLine();

            System.out.print("Localização/Endereço (Ex: Módulo 1): ");
            String endereco = scanner.nextLine();

            Tutor tutorSelecionado = selecionarTutorParaSetor(tutores);

            SetorResponsavel novoSetor = new SetorResponsavel(nome, endereco, tutorSelecionado);
            controller.adicionarSetor(novoSetor);
        } catch (Exception e) {
            System.out.println("\nERRO: Ocorreu um erro no cadastro: " + e.getMessage());
        }
    }

    private void listarSetores(List<SetorResponsavel> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNenhum setor cadastrado.");
            return;
        }

        System.out.println("\n--- RELAÇÃO DE SETORES RESPONSÁVEIS ---");
        System.out.printf("| %-4s | %-25s | %-25s | %-15s |\n", "ID", "NOME", "LOCALIZAÇÃO", "TUTOR RESPONSÁVEL");
        System.out.println("----------------------------------------------------------");
        for (SetorResponsavel s : lista) {
            System.out.printf("| %-4d | %-25s | %-25s | %-15s |\n",
                    s.getId(), s.getNome(), s.getEndereco(), s.getTutorResponsavel().getNome()
            );
        }

        System.out.println("----------------------------------------------------------");
    }

    private void atualizarSetor() {
        System.out.print("Digite o ID do setor para atualizar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            SetorResponsavel setorExistente = controller.buscarSetorPorId(id);
            if (setorExistente == null) {
                System.out.println("\nSetor com ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\n--- ATUALIZAR SETOR (ID: " + id + ") ---");
            System.out.println("Deixe em branco para manter o valor atual.");

            System.out.print("Novo Nome (" + setorExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.isEmpty()) setorExistente.setNome(novoNome);

            System.out.print("Nova Localização (" + setorExistente.getEndereco() + "): ");
            String novoEndereco = scanner.nextLine();
            if (!novoEndereco.isEmpty()) setorExistente.setEndereco(novoEndereco);

            controller.atualizarSetor(id, setorExistente);
            System.out.println("\nSUCESSO: Setor ID " + id + " atualizado.");

        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada inválida. O ID deve ser um número inteiro.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("\nERRO: Ocorreu um erro na atualização: " + e.getMessage());
        }
    }

    private void removerSetor() {
        System.out.print("Digite o ID do setor para remover: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            SetorResponsavel s = controller.buscarSetorPorId(id);
            if (s != null) {
                System.out.println("\nConfirme a exclusão do setor: " + s.getNome());
                System.out.print("Tem certeza que deseja EXCLUIR permanentemente? (S/N): ");
                String confirmacao = scanner.nextLine().trim().toUpperCase();

                if (confirmacao.equals("S")) {
                    controller.removerSetor(id);
                } else {
                    System.out.println("\nExclusão cancelada pelo usuário.");
                }
            } else {
                System.out.println("\nSetor com ID " + id + " não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada inválida. O ID deve ser um número inteiro.");
            scanner.nextLine();
        }
    }

    private Tutor selecionarTutorParaSetor(List<Tutor> tutores) {
        System.out.println("\n--- SELECIONAR TUTOR PARA O SETOR ---");
        System.out.printf("| %-4s | %-25s | %-15s |\n", "ID", "NOME", "TELEFONE");
        System.out.println("--------------------------------------------------");
        for (Tutor t : tutores) {
            System.out.printf("| %-4d | %-25s | %-15s |\n",
                    t.getId(), t.getNome(), t.getTelefone());
        }
        System.out.println("--------------------------------------------------");

        while (true) {
            System.out.print("Digite o ID do tutor para vincular ao setor: ");
            try {
                int tutorId = scanner.nextInt();
                scanner.nextLine();

                Tutor tutorSelecionado = tutorController.buscarTutorPorId(tutorId);
                if (tutorSelecionado != null) {
                    System.out.println("Tutor '" + tutorSelecionado.getNome() + "' vinculado ao setor.");
                    return null;
                } else {
                    System.out.println("Tutor com ID " + tutorId + " não encontrado. Tente novamente.");
                }

                return tutorSelecionado;
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. O ID deve ser um número inteiro.");
                scanner.nextLine();
            }
        }
    }
}