package main;

import controller.AnimalController;
import controller.SetorResponsavelController;
import controller.TutorController;
import view.AnimalView;
import view.SetorResponsavelView;
import view.TutorView;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principal que inicia a Aplicação de Apoio à Proteção de Animais da UEFS.
 * Contém o menu de navegação principal, com tratamento de erros de entrada.
 */
public class Main {

    public static void main(String[] args) {

        // --- 1. CONFIGURAÇÃO INICIAL ---
        Scanner scanner = new Scanner(System.in);

        // Inicializa Controllers
        AnimalController animalController = new AnimalController();
        SetorResponsavelController setorController = new SetorResponsavelController();
        TutorController tutorController = new TutorController();

        // Inicializa Views. ATENÇÃO: SetorResponsavelView está sendo chamada com o mínimo de argumentos.
        AnimalView animalView = new AnimalView(animalController, setorController, scanner);

        TutorView tutorView = new TutorView(tutorController, scanner);

        // LINHA CORRIGIDA PARA EVITAR O ERRO DE CONSTRUTOR:
        SetorResponsavelView setorView = new SetorResponsavelView(setorController, tutorController, scanner);


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
                        System.out.println("\nFuncionalidade de Relatórios pendente de implementação.");
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

        System.out.println("\n-------------------------------------------------");
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