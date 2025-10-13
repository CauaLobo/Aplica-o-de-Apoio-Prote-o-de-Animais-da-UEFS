package util;

import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Classe utilitária para persistência de dados em formato JSON.
 * Responsável por carregar e salvar listas de Tutores, Setores Responsáveis e Animais
 * a partir de um arquivo JSON, utilizando parsing manual sem bibliotecas externas.
 */
public class JsonPersistence {

    /**
     * Classe interna para conter as listas carregadas de tutores, setores e animais.
     */
    public static class DataContainer {
        /** Lista de tutores carregados do JSON */
        public final List<Tutor> tutores;
        /** Lista de setores responsáveis carregados do JSON */
        public final List<SetorResponsavel> setores;
        /** Lista de animais carregados do JSON */
        public final List<Animal> animais;

        /**
         * Construtor do DataContainer.
         * @param tutores Lista de tutores
         * @param setores Lista de setores responsáveis
         * @param animais Lista de animais
         */
        public DataContainer(List<Tutor> tutores, List<SetorResponsavel> setores, List<Animal> animais) {
            this.tutores = tutores;
            this.setores = setores;
            this.animais = animais;
        }
    }

    /**
     * Carrega os dados do arquivo JSON informado, populando listas de tutores, setores e animais.
     * @param filename Caminho do arquivo JSON
     * @return DataContainer contendo as listas carregadas
     */
    public static DataContainer loadData(String filename) {
        List<Tutor> tutores = new ArrayList<>();
        List<SetorResponsavel> setores = new ArrayList<>();
        List<Animal> animais = new ArrayList<>();

        try {
            if (!Files.exists(Paths.get(filename))) {
                System.out.println("Arquivo de dados não encontrado. Iniciando com listas vazias.");
                return new DataContainer(tutores, setores, animais);
            }

            String jsonContent = new String(Files.readAllBytes(Paths.get(filename)));

            // 1. Carregar Tutores e Endereços
            List<Map<String, String>> tutoresData = parseJsonArray(jsonContent, "tutores");
            int maxTutorId = 0;
            for (Map<String, String> tutorMap : tutoresData) {
                Tutor tutor = new Tutor();
                int tutorId = Integer.parseInt(tutorMap.get("id"));
                tutor.setId(tutorId);
                tutor.setNome(tutorMap.get("nome"));
                tutor.setTelefone(tutorMap.get("telefone"));
                tutor.setEmail(tutorMap.get("email"));

                // Parse do objeto Endereco aninhado
                String enderecoJson = tutorMap.get("endereco");
                Map<String, String> enderecoMap = parseJsonObject(enderecoJson);
                Endereco endereco = new Endereco();
                endereco.setRua(enderecoMap.get("rua"));
                endereco.setBairro(enderecoMap.get("bairro"));
                endereco.setCep(enderecoMap.get("cep"));
                endereco.setCidade(enderecoMap.get("cidade"));
                endereco.setEstado(enderecoMap.get("estado"));
                tutor.setEndereco(endereco);

                tutores.add(tutor);
                if (tutorId > maxTutorId) {
                    maxTutorId = tutorId;
                }
            }
            Tutor.setContadorIds(maxTutorId + 1);

            // 2. Carregar Setores e associar Tutores
            List<Map<String, String>> setoresData = parseJsonArray(jsonContent, "setores");
            int maxSetorId = 0;
            for (Map<String, String> setorMap : setoresData) {
                SetorResponsavel setor = new SetorResponsavel();
                int setorId = Integer.parseInt(setorMap.get("id"));
                setor.setId(setorId);
                setor.setNome(setorMap.get("nome"));
                setor.setEndereco(setorMap.get("endereco"));

                int tutorResponsavelId = Integer.parseInt(setorMap.get("tutorResponsavelId"));
                Tutor tutorResponsavel = tutores.stream().filter(t -> t.getId() == tutorResponsavelId).findFirst().orElse(null);
                setor.setTutorResponsavel(tutorResponsavel);

                setores.add(setor);
                if (setorId > maxSetorId) {
                    maxSetorId = setorId;
                }
            }
            SetorResponsavel.setContadorIds(maxSetorId + 1);

            // 3. Carregar Animais e associar Setores
            List<Map<String, String>> animaisData = parseJsonArray(jsonContent, "animais");
            int maxAnimalId = 0;
            for (Map<String, String> animalMap : animaisData) {
                Animal animal = new Animal();
                int animalId = Integer.parseInt(animalMap.get("id"));
                animal.setId(animalId);
                animal.setNome(animalMap.get("nome"));
                animal.setEspecie(Especie.valueOf(animalMap.get("especie")));
                animal.setRaca(animalMap.get("raca"));
                animal.setIdade(Integer.parseInt(animalMap.get("idade")));
                animal.setSexo(animalMap.get("sexo"));
                animal.setSituacaoAtual(SituacaoAtual.valueOf(animalMap.get("situacaoAtual")));

                int setorResponsavelId = Integer.parseInt(animalMap.get("setorResponsavelId"));
                SetorResponsavel setorResponsavel = setores.stream().filter(s -> s.getId() == setorResponsavelId).findFirst().orElse(null);
                animal.setSetorResponsavel(setorResponsavel);

                animais.add(animal);
                if (animalId > maxAnimalId) {
                    maxAnimalId = animalId;
                }
            }
            Animal.setContadorIds(maxAnimalId + 1);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao carregar e analisar o arquivo JSON: " + e.getMessage());
            e.printStackTrace(); // Ajuda a depurar
        }

        return new DataContainer(tutores, setores, animais);
    }

    /**
     * Realiza o parsing de um array JSON identificado pelo nome, retornando uma lista de mapas chave-valor.
     * @param jsonContent Conteúdo JSON completo
     * @param arrayName Nome do array a ser extraído
     * @return Lista de mapas representando os objetos do array
     */
    private static List<Map<String, String>> parseJsonArray(String jsonContent, String arrayName) {
        List<Map<String, String>> list = new ArrayList<>();
        Pattern p = Pattern.compile("\"" + arrayName + "\":\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher m = p.matcher(jsonContent);
        if (m.find()) {
            String arrayString = m.group(1).trim();
            if (arrayString.isEmpty()) return list;

            // CORREÇÃO: Regex para encontrar objetos, tratando corretamente o aninhamento
            Matcher objectMatcher = Pattern.compile("\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*\\})*\\})*\\}").matcher(arrayString);
            while (objectMatcher.find()) {
                list.add(parseJsonObject(objectMatcher.group()));
            }
        }
        return list;
    }

    /**
     * Realiza o parsing de um objeto JSON simples (sem arrays), retornando um mapa chave-valor.
     * @param objectContent String do objeto JSON
     * @return Mapa com os pares chave-valor extraídos
     */
    private static Map<String, String> parseJsonObject(String objectContent) {
        Map<String, String> map = new HashMap<>();
        if (objectContent == null || objectContent.trim().isEmpty()) {
            return map;
        }
        // Regex para encontrar pares "chave": "valor" ou "chave": {objeto}
        Pattern p = Pattern.compile("\"(.*?)\":\\s*(\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*\\})*\\})*\\}|\".*?\"|[\\d.truefalse]+)");
        Matcher m = p.matcher(objectContent);
        while (m.find()) {
            String key = m.group(1);
            String value = m.group(2).trim();
            // Remove aspas das strings, mas não de objetos JSON
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * Salva as listas de tutores, setores e animais em um arquivo JSON.
     * @param filename Caminho do arquivo JSON
     * @param tutores Lista de tutores
     * @param setores Lista de setores responsáveis
     * @param animais Lista de animais
     */
    public static void saveData(String filename, List<Tutor> tutores, List<SetorResponsavel> setores, List<Animal> animais) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");

        jsonBuilder.append("  \"tutores\": [\n");
        jsonBuilder.append(tutores.stream().map(Tutor::toJson).collect(Collectors.joining(",\n")));
        jsonBuilder.append("\n  ],\n");

        jsonBuilder.append("  \"setores\": [\n");
        jsonBuilder.append(setores.stream().map(SetorResponsavel::toJson).collect(Collectors.joining(",\n")));
        jsonBuilder.append("\n  ],\n");

        jsonBuilder.append("  \"animais\": [\n");
        jsonBuilder.append(animais.stream().map(Animal::toJson).collect(Collectors.joining(",\n")));
        jsonBuilder.append("\n  ]\n");

        jsonBuilder.append("}\n");

        try {
            Files.write(Paths.get(filename), jsonBuilder.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
        }
    }
}
