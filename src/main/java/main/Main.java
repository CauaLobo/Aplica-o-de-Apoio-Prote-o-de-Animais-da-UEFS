package main;

import controller.AnimalController;
import controller.RelatorioController;
import controller.SetorResponsavelController;
import controller.TutorController;
import util.JsonPersistence;
import view.AnimalView;
import view.RelatorioView;
import view.SetorResponsavelView;
import view.TutorView;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principal que inicia a Aplicação de Apoio à Proteção de Animais da UEFS.
 * Contém o menu de navegação principal, com tratamento de erros de entrada.
 */
public class Main {

    private static final String DATA_FILE = "dados_programa.json";

    public static void main(String[] args) {
        System.out.println("Carregando dados do arquivo '" + DATA_FILE + "'...");
        JsonPersistence.DataContainer data = JsonPersistence.loadData(DATA_FILE);

        // --- 2. CONFIGURAÇÃO INICIAL ---
        Scanner scanner = new Scanner(System.in);

        // Inicializa Controllers com os dados carregados
        TutorController tutorController = new TutorController(data.tutores);
        SetorResponsavelController setorController = new SetorResponsavelController(data.setores);
        AnimalController animalController = new AnimalController(data.animais);
        RelatorioController relatorioController = new RelatorioController(animalController, setorController);

        // Inicializa Views
        AnimalView animalView = new AnimalView(animalController, setorController, scanner);
        TutorView tutorView = new TutorView(tutorController, scanner);
        SetorResponsavelView setorView = new SetorResponsavelView(setorController, tutorController, scanner);
        RelatorioView relatorioView = new RelatorioView(relatorioController, scanner);

        // --- INÍCIO DA APLICAÇÃO ---
        System.out.println("\n=================================================");
        System.out.println("   Aplicação de Apoio à Proteção de Animais da UEFS");
        System.out.println("=================================================");

        int opcao;
        do {
            exibirMenuPrincipal();
            try {
                // Tenta ler a opção como um inteiro
                opcao = scanner.nextInt();
                // CRÍTICO: Consome a quebra de linha (Enter) após o nextInt
                scanner.nextLine();

                switch (opcao) {
                    case 1: animalView.exibirMenu(); break;
                    case 2: setorView.exibirMenu(); break;
                    case 3: tutorView.exibirMenu(); break;
                    case 4:
                        relatorioView.exibirMenu();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\nOpção inválida. Tente novamente.");
                }

            } catch (InputMismatchException e) {
                // SE OCORRER ERRO (ex: usuário digitou 'A'), LIMPA O BUFFER DO SCANNER
                System.out.println("\nERRO: Por favor, digite apenas números inteiros válidos.");
                scanner.nextLine(); // LIMPA O BUFFER APÓS ERRO
                opcao = -1; // Garante que o loop continue
            }
        } while (opcao != 0);

        // --- SALVAR DADOS ANTES DE SAIR ---
        JsonPersistence.saveData(
                DATA_FILE,
                tutorController.listarTutores(),
                setorController.listarSetores(),
                animalController.listarAnimais()
        );


        System.out.println("\n-------------------------------------------------");
        System.out.println("Dados salvos em '" + DATA_FILE + "' com sucesso!");

        System.out.println("Sistema encerrado. Até mais!");
        System.out.println("-------------------------------------------------");
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n*** MENU PRINCIPAL ***");
        System.out.println("1. Gerenciar Animais");
        System.out.println("2. Gerenciar Setores Responsáveis");
        System.out.println("3. Gerenciar Pessoas Tutoras");
        System.out.println("4. Emitir Relatórios");
        System.out.println("0. Sair");
        System.out.print(">>> Escolha uma opção: ");
    }
}
