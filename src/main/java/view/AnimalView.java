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
        try {
            System.out.println("\n--- CADASTRO DE ANIMAL ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

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

    // --- MÉTODOS AUXILIARES E CRUD IMPLEMENTADOS ---

    /**
     * Permite ao usuário selecionar a Situação Atual do Animal a partir do Enum.
     */
    private SituacaoAtual selecionarSituacao() {
        SituacaoAtual[] opcoes = SituacaoAtual.values();
        int escolha;

        do {
            System.out.println("\n*** Selecione a Situação Atual (número): ***");
            for (int i = 0; i < opcoes.length; i++) {
                // Exibe o nome da constante do Enum
                System.out.println("  " + (i + 1) + ". " + opcoes[i]);
            }
            System.out.print(">>> Opção: ");

            try {
                escolha = scanner.nextInt();
                scanner.nextLine();

                if (escolha > 0 && escolha <= opcoes.length) {
                    return opcoes[escolha - 1];
                } else {
                    System.out.println("Opção inválida. Digite o número correspondente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Digite apenas números.");
                scanner.nextLine();
                escolha = -1;
            }
        } while (true);
    }

    /**
     * Exibe a lista de animais de forma formatada.
     */
    private void listarAnimais(List<Animal> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNenhum animal cadastrado no momento.");
            return;
        }

        System.out.println("\n--- RELAÇÃO DE ANIMAIS CADASTRADOS ---");
        // Ajuste as larguras conforme sua necessidade
        System.out.printf("| %-4s | %-15s | %-10s | %-10s | %-5s | %-10s | %-15s | %-5s |\n",
                "ID", "NOME", "ESPÉCIE", "RAÇA", "SEXO", "IDADE", "SITUAÇÃO", "SETOR");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Animal a : lista) {
            // Formata o ID do Setor para exibição (usando 'N/A' se for -1)
            String setorDisplay = (a.getSetorResponsavelId() == -1) ? "N/A" : String.valueOf(a.getSetorResponsavelId());

            System.out.printf("| %-4d | %-15s | %-10s | %-10s | %-5s | %-10d | %-15s | %-5s |\n",
                    a.getId(),
                    a.getNome(),
                    a.getEspecie(),
                    a.getRaca(),
                    a.getSexo(),
                    a.getIdade(),
                    a.getSituacaoAtual(),
                    setorDisplay);
        }
        System.out.println("-----------------------------------------------------------------------------------------");
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
     * Permite buscar um animal por ID e alterar seus dados.
     */
    private void atualizarAnimal() {
        System.out.print("\nDigite o ID do Animal que deseja atualizar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animalExistente = controller.buscarAnimalPorId(id);

            if (animalExistente == null) {
                System.out.println("ERRO: Animal ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\n--- ATUALIZANDO ANIMAL ID: " + id + " ---");
            System.out.println("Deixe em branco para manter o valor atual.");

            System.out.print("Novo Nome (" + animalExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) animalExistente.setNome(novoNome);

            // Exemplo de como atualizar a situação (opcional)
            System.out.print("Deseja alterar a Situação Atual? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                animalExistente.setSituacaoAtual(selecionarSituacao());
            }

            controller.atualizarAnimal(id, animalExistente);
            System.out.println("SUCESSO: Animal atualizado.");

        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite um número inteiro para o ID.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ERRO ao atualizar: " + e.getMessage());
        }
    }

    /**
     * Permite remover um animal por ID, exigindo confirmação.
     */
    private void removerAnimal() {
        System.out.print("\nDigite o ID do Animal que deseja remover: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animalExistente = controller.buscarAnimalPorId(id);

            if (animalExistente == null) {
                System.out.println("ERRO: Animal ID " + id + " não encontrado.");
                return;
            }

            System.out.print("Tem certeza que deseja remover o Animal " + animalExistente.getNome() + " (S/N)? ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                controller.removerAnimal(id);
                System.out.println("SUCESSO: Animal removido.");
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