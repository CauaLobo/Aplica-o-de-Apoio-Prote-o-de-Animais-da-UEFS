package view;

import controller.RelatorioController;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RelatorioView {

    private final RelatorioController controller;
    private final Scanner scanner;

    public RelatorioView(RelatorioController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- EMISSÃO DE RELATÓRIOS ---");
            System.out.println("1. Relação completa de Animais");
            System.out.println("2. Relação de Animais por Setor Responsável");
            System.out.println("3. Relação de Animais por Espécie");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print(">>> Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                String nomeArquivo;

                switch (opcao) {
                    case 1:
                        nomeArquivo = "relatorio_completo_animais.txt";
                        controller.gerarRelatorioCompletoAnimais(nomeArquivo);
                        System.out.println("\nSUCESSO: Relatório gerado em '" + nomeArquivo + "'.");
                        break;
                    case 2:
                        nomeArquivo = "relatorio_animais_por_setor.txt";
                        controller.gerarRelatorioAnimaisPorSetor(nomeArquivo);
                        System.out.println("\nSUCESSO: Relatório gerado em '" + nomeArquivo + "'.");
                        break;
                    case 3:
                        nomeArquivo = "relatorio_animais_por_especie.txt";
                        controller.gerarRelatorioAnimaisPorEspecie(nomeArquivo);
                        System.out.println("\nSUCESSO: Relatório gerado em '" + nomeArquivo + "'.");
                        break;
                    case 0:
                        System.out.println("\nVoltando ao menu principal...");
                        break;
                    default:
                        System.out.println("\nOpção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Entrada inválida. Digite apenas o número da opção.");
                scanner.nextLine();
                opcao = -1;
            } catch (IOException e) {
                System.out.println("\nERRO: Falha ao salvar o arquivo de relatório: " + e.getMessage());
            }
        } while (opcao != 0);
    }
}
