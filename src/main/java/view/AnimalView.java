package view;

import controller.AnimalController;
import controller.SetorResponsavelController;
import model.Animal;
import model.Especie;
import model.SetorResponsavel;
import model.SituacaoAtual;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Menu de Linha de Comando (CLI) para gerenciamento da entidade Animal.
 * Permite cadastrar, listar, atualizar e remover animais do sistema.
 */
public class AnimalView {

    /** Controller de animais utilizado pela view. */
    private final AnimalController controller;
    /** Controller de setores responsáveis utilizado pela view. */
    private final SetorResponsavelController setorResponsavelController;
    /** Scanner para entrada de dados do usuário. */
    private final Scanner scanner;

    /**
     * Construtor da view de animais.
     * @param controller Controller de animais
     * @param setorResponsavelController Controller de setores responsáveis
     * @param scanner Scanner para entrada de dados
     */
    public AnimalView(AnimalController controller, SetorResponsavelController setorResponsavelController, Scanner scanner) {
        this.controller = controller;
        this.setorResponsavelController = setorResponsavelController;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de opções para gerenciamento de animais.
     */
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
                scanner.nextLine();
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
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarAnimal() {


        if (setorResponsavelController.listarSetores().isEmpty()) {
            System.out.println("\nERRO: Nenhum Setor Responsável cadastrado. Cadastre um setor antes de adicionar animais.");
            return;
        }

        try {
            System.out.println("\n--- CADASTRO DE ANIMAL ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            Especie especie = selecionarEspecie(); // Usando enum

            System.out.print("Raça (Opcional): ");
            String raca = scanner.nextLine();

            System.out.print("Idade aproximada (Anos, número inteiro): ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Sexo (M/F): ");
            String sexo = scanner.nextLine();

            SituacaoAtual situacao = selecionarSituacao();
            SetorResponsavel setor = selecionarSetorResponsavel();

            Animal novoAnimal = new Animal(nome, especie, raca, idade, sexo, situacao, setor);
            controller.adicionarAnimal(novoAnimal);

        } catch (IllegalArgumentException e) {
            System.out.println("\nERRO de Validação: Falha ao cadastrar animal: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada de dados inválida. Digite números onde solicitado.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("\nERRO: Ocorreu um erro no cadastro.");
        }
    }

    private SetorResponsavel selecionarSetorResponsavel() {
        List<SetorResponsavel> setores = setorResponsavelController.listarSetores();
        System.out.println("\nSelecione o Setor Responsável (número):");
        for (int i = 0; i < setores.size(); i++) {
            System.out.println((i + 1) + ". " + setores.get(i).getNome() + " - " + setores.get(i).getEndereco());
        }
        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= setores.size()) {
            return setores.get(escolha - 1);
        } else {
            throw new IllegalArgumentException("Opção de Setor inválida. Seleção cancelada.");
        }

    }

    private SituacaoAtual selecionarSituacao() {
        System.out.println("\nSelecione a Situação Atual (número):");
        SituacaoAtual[] opcoes = SituacaoAtual.values();
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + ". " + opcoes[i]);
        }
        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= opcoes.length) {
            return opcoes[escolha - 1];
        } else {
            throw new IllegalArgumentException("Opção de Situação inválida. Seleção cancelada.");
        }
    }

    private void listarAnimais(List<Animal> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNenhum animal cadastrado.");
            return;
        }
        System.out.println("\n--- RELAÇÃO DE ANIMAIS (" + lista.size() + " total) ---");
        System.out.printf("| %-4s | %-15s | %-12s | %-8s | %-25s | %-10s |\n", "ID", "NOME", "ESPÉCIE", "IDADE", "SITUAÇÃO", "SETOR");
        System.out.println("-----------------------------------------------------------------------");
        for (Animal a : lista) {
            System.out.printf("| %-4s | %-15s | %-12s | %-8d | %-25s | %-10s |\n",
                    a.getId(), a.getNome(), a.getEspecie(), a.getIdade(), a.getSituacaoAtual(), a.getSetorResponsavel().getNome()
            );
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    private void atualizarAnimal() {
        System.out.print("Digite o ID do animal para atualizar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animalExistente = controller.buscarAnimalPorId(id);
            if (animalExistente == null) {
                System.out.println("\nAnimal com ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\n--- ATUALIZAR ANIMAL (ID: " + id + ") ---");
            System.out.println("Deixe em branco/zero para manter o valor atual.");

            // Nome
            System.out.print("Novo Nome (" + animalExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.isEmpty()) animalExistente.setNome(novoNome);

            // Idade
            System.out.print("Nova Idade (" + animalExistente.getIdade() + "): ");
            String novaIdadeStr = scanner.nextLine();
            if (!novaIdadeStr.isEmpty()) {
                int novaIdade = Integer.parseInt(novaIdadeStr);
                animalExistente.setIdade(novaIdade);
            }

            System.out.println("\nEspécie Atual: " + animalExistente.getEspecie());
            System.out.print("Deseja alterar a Espécie? (S/N): ");
            String alterarEspecie = scanner.nextLine().trim().toUpperCase();
            if (alterarEspecie.equals("S")) {
                Especie novaEspecie = selecionarEspecie();
                animalExistente.setEspecie(novaEspecie);
            }

            // Situação Atual
            System.out.println("\nSituação Atual: " + animalExistente.getSituacaoAtual());
            System.out.print("Deseja alterar a Situação Atual? (S/N): ");
            String alterarSituacao = scanner.nextLine().trim().toUpperCase();

            if (alterarSituacao.equals("S")) {
                SituacaoAtual novaSituacao = selecionarSituacao();
                animalExistente.setSituacaoAtual(novaSituacao);
            }

            controller.atualizarAnimal(id, animalExistente);
            System.out.println("\nSUCESSO: Animal ID " + id + " atualizado.");

        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("\nERRO: Entrada inválida. Certifique-se de usar números para o ID e Idade.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("\nERRO de Validação: Falha ao atualizar: " + e.getMessage());
        }
    }

    private void removerAnimal() {
        System.out.print("Digite o ID do animal para remover: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal a = controller.buscarAnimalPorId(id);
            if (a != null) {
                System.out.println("\nConfirme a exclusão do animal: " + a.getNome());
                System.out.print("Tem certeza que deseja EXCLUIR permanentemente? (S/N): ");
                String confirmacao = scanner.nextLine().trim().toUpperCase();

                if (confirmacao.equals("S")) {
                    controller.removerAnimal(id);
                } else {
                    System.out.println("\nExclusão cancelada pelo usuário.");
                }
            } else {
                System.out.println("\nAnimal com ID " + id + " não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nERRO: Entrada inválida. O ID deve ser um número inteiro.");
            scanner.nextLine();
        }
    }

    private Especie selecionarEspecie() {
        System.out.println("\nSelecione a Espécie:");
        Especie[] opcoes = Especie.values();
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + ". " + opcoes[i]);
        }
        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        if (escolha > 0 && escolha <= opcoes.length) {
            return opcoes[escolha - 1];
        } else {
            throw new IllegalArgumentException("Opção de Espécie inválida.");
        }
    }

}