package controller;

import model.Tutor;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe controladora para gerenciar operações relacionadas a Tutores.
 * Permite adicionar, atualizar, listar, buscar e remover tutores.
 */
public class TutorController {
    private final List<Tutor> tutores;

    /**
     * Construtor padrão. Inicializa uma nova lista de tutores vazia.
     */
    public TutorController() {
        this.tutores = new ArrayList<>();
    }

    /**
     * Construtor que inicializa o controlador com uma lista de tutores pré-existente.
     * @param tutores A lista de tutores para ser gerenciada.
     */
    public TutorController(List<Tutor> tutores) {
        this.tutores = tutores;
    }

    /**
     * Adiciona um novo tutor à lista.
     * @param novoTutor O objeto Tutor a ser adicionado.
     */
    public void adicionarTutor(Tutor novoTutor) {
        tutores.add(novoTutor);
        System.out.println("Tutor cadastrado com sucesso: " + novoTutor.getNome());
    }
    /**
     * Atualiza os dados de um tutor existente com base no seu ID.
     * @param id O ID do tutor a ser atualizado.
     * @param novoTutor O objeto Tutor com os novos dados.
     */
    public void atualizarTutor(int id, Tutor novoTutor) {
        for (Tutor t : tutores) {
            if (t.getId() == id) {
                t.atualizarTutor(novoTutor);
                System.out.println("Tutor com ID " + id + " atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Tutor com ID " + id + " não encontrado.");
    }
    /**
     * Retorna a lista de todos os tutores cadastrados.
     * @return Uma lista de objetos Tutor.
     */
    public List<Tutor> listarTutores() {
        return tutores;
    }
    /**
     * Busca um tutor na lista pelo seu ID.
     * @param id O ID do tutor a ser buscado.
     * @return O objeto Tutor encontrado ou null se não for encontrado.
     */
    public Tutor buscarTutorPorId(int id) {
        for (Tutor t : tutores) {
            if (t.getId() == id) {
                return t;
            }
        }
        // System.out.println("Tutor com ID " + id + " não encontrado."); // Removido para não poluir
        return null;
    }
    /**
     * Remove um tutor da lista com base no seu ID.
     * @param id O ID do tutor a ser removido.
     */
    public void removerTutor(int id){
        Tutor tutorParaRemover = buscarTutorPorId(id);
        if (tutorParaRemover != null) {
            tutores.remove(tutorParaRemover);
            System.out.println("Tutor com ID " + id + " removido com sucesso!");
        } else {
            System.out.println("Tutor com ID " + id + " não encontrado.");
        }
    }
}
