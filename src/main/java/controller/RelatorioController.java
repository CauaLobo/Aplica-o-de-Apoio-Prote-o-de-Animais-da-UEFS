package controller;

import model.Animal;
import model.Especie;
import model.SetorResponsavel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador responsável pela geração de relatórios relacionados aos animais e setores.
 * Permite gerar relatórios completos, por setor responsável e por espécie, exportando para arquivo.
 */
public class RelatorioController {

    /**
     * Controlador de animais utilizado para obter dados dos animais.
     */
    private final AnimalController animalController;
    /**
     * Controlador de setores utilizado para obter dados dos setores responsáveis.
     */
    private final SetorResponsavelController setorController;

    /**
     * Construtor do controlador de relatórios.
     * @param animalController Controlador de animais
     * @param setorController Controlador de setores responsáveis
     */
    public RelatorioController(AnimalController animalController, SetorResponsavelController setorController) {
        this.animalController = animalController;
        this.setorController = setorController;
    }

    /**
     * Cria uma string repetida de um caractere, compatível com versões antigas do Java.
     * @param caractere Caractere a ser repetido
     * @param vezes Número de repetições
     * @return String formada pelo caractere repetido
     */
    private String repetirCaractere(char caractere, int vezes) {
        return String.join("", Collections.nCopies(vezes, String.valueOf(caractere)));
    }

    /**
     * Gera um relatório completo de todos os animais cadastrados e salva em arquivo.
     * @param caminhoArquivo Caminho do arquivo para salvar o relatório
     * @throws IOException Caso ocorra erro ao salvar o arquivo
     */
    public void gerarRelatorioCompletoAnimais(String caminhoArquivo) throws IOException {
        List<Animal> animais = animalController.listarAnimais();
        StringBuilder relatorio = new StringBuilder();

        gerarCabecalho(relatorio, "Relatório Completo de Animais");

        if (animais.isEmpty()) {
            relatorio.append("Nenhum animal cadastrado no sistema.\n");
        } else {
            relatorio.append(String.format("%-4s | %-15s | %-12s | %-8s | %-25s | %-20s\n", "ID", "NOME", "ESPÉCIE", "IDADE", "SITUAÇÃO", "SETOR RESPONSÁVEL"));
            relatorio.append(repetirCaractere('-', 90)).append("\n");
            for (Animal a : animais) {
                String nomeSetor = a.getSetorResponsavel() != null ? a.getSetorResponsavel().getNome() : "N/A";
                relatorio.append(String.format("%-4d | %-15s | %-12s | %-8d | %-25s | %-20s\n",
                        a.getId(), a.getNome(), a.getEspecie(), a.getIdade(), a.getSituacaoAtual(), nomeSetor));
            }
        }

        gerarRodape(relatorio);
        salvarRelatorio(caminhoArquivo, relatorio.toString());
    }

    /**
     * Gera um relatório agrupando os animais por setor responsável e salva em arquivo.
     * @param caminhoArquivo Caminho do arquivo para salvar o relatório
     * @throws IOException Caso ocorra erro ao salvar o arquivo
     */
    public void gerarRelatorioAnimaisPorSetor(String caminhoArquivo) throws IOException {
        List<Animal> animais = animalController.listarAnimais();
        List<SetorResponsavel> setores = setorController.listarSetores();
        StringBuilder relatorio = new StringBuilder();

        gerarCabecalho(relatorio, "Relatório de Animais por Setor Responsável");

        Map<Integer, List<Animal>> animaisPorSetor = animais.stream()
                .filter(a -> a.getSetorResponsavel() != null)
                .collect(Collectors.groupingBy(a -> a.getSetorResponsavel().getId()));

        if (setores.isEmpty()) {
            relatorio.append("Nenhum setor cadastrado para agrupar os animais.\n");
        } else {
            for (SetorResponsavel setor : setores) {
                relatorio.append("\n======================================================================\n");
                relatorio.append(" SETOR: ").append(setor.getNome().toUpperCase()).append(" (ID: ").append(setor.getId()).append(")\n");
                relatorio.append("======================================================================\n");

                List<Animal> animaisDoSetor = animaisPorSetor.getOrDefault(setor.getId(), Collections.emptyList());
                if (animaisDoSetor.isEmpty()) {
                    relatorio.append("Nenhum animal associado a este setor.\n");
                } else {
                    relatorio.append(String.format("%-4s | %-15s | %-12s | %-8s | %-25s\n", "ID", "NOME", "ESPÉCIE", "IDADE", "SITUAÇÃO"));
                    relatorio.append(repetirCaractere('-', 70)).append("\n");
                    for (Animal a : animaisDoSetor) {
                        relatorio.append(String.format("%-4d | %-15s | %-12s | %-8d | %-25s\n",
                                a.getId(), a.getNome(), a.getEspecie(), a.getIdade(), a.getSituacaoAtual()));
                    }
                }
            }
        }

        gerarRodape(relatorio);
        salvarRelatorio(caminhoArquivo, relatorio.toString());
    }

    /**
     * Gera um relatório agrupando os animais por espécie e salva em arquivo.
     * @param caminhoArquivo Caminho do arquivo para salvar o relatório
     * @throws IOException Caso ocorra erro ao salvar o arquivo
     */
    public void gerarRelatorioAnimaisPorEspecie(String caminhoArquivo) throws IOException {
        List<Animal> animais = animalController.listarAnimais();
        StringBuilder relatorio = new StringBuilder();

        gerarCabecalho(relatorio, "Relatório de Animais por Espécie");

        Map<Especie, List<Animal>> animaisPorEspecie = animais.stream()
                .collect(Collectors.groupingBy(Animal::getEspecie));

        if (animais.isEmpty()) {
            relatorio.append("Nenhum animal cadastrado.\n");
        } else {
            for (Especie especie : Especie.values()) {
                relatorio.append("\n======================================================================\n");
                relatorio.append(" ESPÉCIE: ").append(especie.name().toUpperCase()).append("\n");
                relatorio.append("======================================================================\n");

                List<Animal> animaisDaEspecie = animaisPorEspecie.getOrDefault(especie, Collections.emptyList());
                if (animaisDaEspecie.isEmpty()) {
                    relatorio.append("Nenhum animal desta espécie cadastrado.\n");
                } else {
                    relatorio.append(String.format("%-4s | %-15s | %-8s | %-25s | %-20s\n", "ID", "NOME", "IDADE", "SITUAÇÃO", "SETOR RESPONSÁVEL"));
                    relatorio.append(repetirCaractere('-', 80)).append("\n");
                    for (Animal a : animaisDaEspecie) {
                        String nomeSetor = a.getSetorResponsavel() != null ? a.getSetorResponsavel().getNome() : "N/A";
                        relatorio.append(String.format("%-4d | %-15s | %-8d | %-25s | %-20s\n",
                                a.getId(), a.getNome(), a.getIdade(), a.getSituacaoAtual(), nomeSetor));
                    }
                }
            }
        }

        gerarRodape(relatorio);
        salvarRelatorio(caminhoArquivo, relatorio.toString());
    }

    /**
     * Gera o cabeçalho do relatório.
     * @param sb StringBuilder do relatório
     * @param titulo Título do relatório
     */
    private void gerarCabecalho(StringBuilder sb, String titulo) {
        sb.append("********************************************************************************\n");
        sb.append("        Aplicação de Apoio à Proteção de Animais da UEFS\n");
        sb.append("********************************************************************************\n");
        sb.append("\n");
        sb.append("## ").append(titulo).append(" ##\n\n");
    }

    /**
     * Gera o rodapé do relatório, incluindo data e hora de geração.
     * @param sb StringBuilder do relatório
     */
    private void gerarRodape(StringBuilder sb) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        sb.append("\n\n---\n");
        sb.append("Relatório gerado em: ").append(dtf.format(LocalDateTime.now())).append("\n");
        sb.append("Fim do relatório.\n");
    }

    /**
     * Salva o conteúdo do relatório em um arquivo.
     * @param caminhoArquivo Caminho do arquivo
     * @param conteudo Conteúdo a ser salvo
     * @throws IOException Caso ocorra erro ao salvar o arquivo
     */
    private void salvarRelatorio(String caminhoArquivo, String conteudo) throws IOException {
        Files.write(Paths.get(caminhoArquivo), conteudo.getBytes());
    }
}
