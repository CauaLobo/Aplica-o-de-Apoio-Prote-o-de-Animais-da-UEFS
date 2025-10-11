package view;

import controller.TutorController;
import model.Tutor;
import model.Endereco;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

/**
 * Menu de Linha de Comando (CLI) para gerenciamento da entidade Tutor.
 */
public class TutorView {

    private final TutorController controller;
    private final Scanner scanner;

    public TutorView(TutorController controller, Scanner scanner) {
        this.controller = controller;
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
                scanner.nextLine();
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
                scanner.nextLine();
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

            Tutor novoTutor = new Tutor(0, nome, endereco, telefone, email);
            controller.adicionarTutor(novoTutor);

        } catch (IllegalArgumentException e) {
            System.out.println("\nERRO de Validação: Falha ao cadastrar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nERRO: Ocorreu um erro no cadastro: " + e.getMessage());
        }
    }

    private void listarTutores(List<Tutor> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNenhum tutor cadastrado.");
            return;
        }
        System.out.println("\n--- RELAÇÃO DE PESSOAS TUTORAS ---");
        System.out.printf("| %-4s | %-20s | %-15s | %-30s |\n", "ID", "NOME", "TELEFONE", "E-MAIL");
        System.out.println("------------------------------------------------------------------");
        for (Tutor t : lista) {
            System.out.printf("| %-4d | %-20s | %-15s | %-30s |\n",
                    t.getId(), t.getNome(), t.getTelefone(), t.getEmail());
        }
        System.out.println("------------------------------------------------------------------");
    }

    private void atualizarTutor() {
        System.out.print("Digite o ID do tutor que deseja atualizar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Tutor tutorExistente = controller.buscarTutorPorId(id);
            if (tutorExistente == null) {
                System.out.println("\nTutor com ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\n--- ATUALIZAR TUTOR (ID: " + id + ") ---");
            System.out.println("Deixe em branco para manter o valor atual.");

            // 1. Atualizar Nome
            System.out.print("Novo Nome (" + tutorExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.isEmpty()) tutorExistente.setNome(novoNome);

            // 2. Atualizar Telefone
            System.out.print("Novo Telefone (" + tutorExistente.getTelefone() + "): ");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.isEmpty()) tutorExistente.setTelefone(novoTelefone);

            // 3. Atualizar E-mail
            System.out.print("Novo E-mail (" + tutorExistente.getEmail() + "): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.isEmpty()) tutorExistente.setEmail(novoEmail);

            // 4. Atualizar Endereço
            Endereco enderecoExistente = tutorExistente.getEndereco();
            System.out.println("\n--- ATUALIZAR ENDEREÇO ---");

            System.out.print("Nova Rua (" + enderecoExistente.getRua() + "): ");
            String novaRua = scanner.nextLine();
            if (!novaRua.isEmpty()) enderecoExistente.setRua(novaRua);

            System.out.print("Novo Bairro (" + enderecoExistente.getBairro() + "): ");
            String novoBairro = scanner.nextLine();
            if (!novoBairro.isEmpty()) enderecoExistente.setBairro(novoBairro);

            controller.atualizarTutor(id, tutorExistente);
            System.out.println("\nSUCESSO: Tutor ID " + id + " atualizado.");

        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada inválida. O ID deve ser um número inteiro.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("\nERRO de Validação: Falha ao atualizar: " + e.getMessage());
        }
    }

    private void removerTutor() {
        System.out.print("Digite o ID do tutor para remover: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Tutor t = controller.buscarTutorPorId(id);
            if (t != null) {
                System.out.println("\nConfirme a exclusão do tutor: " + t.getNome());
                System.out.print("Tem certeza que deseja EXCLUIR permanentemente? (S/N): ");
                String confirmacao = scanner.nextLine().trim().toUpperCase();

                if (confirmacao.equals("S")) {
                    controller.removerTutor(id);
                } else {
                    System.out.println("\nExclusão cancelada pelo usuário.");
                }
            } else {
                System.out.println("\nTutor com ID " + id + " não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada inválida. O ID deve ser um número inteiro.");
            scanner.nextLine();
        }
    }
}