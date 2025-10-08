package controller;

import model.Tutor;
import java.util.List;
import java.util.ArrayList;

public class TutorController {
    public List<Tutor> tutor = new ArrayList<>();
    private static int nextId = 1;

    // CREATE - Cadastrar tutor

    public void adicionarTutor(Tutor novoTutor) {
        novoTutor.setId(nextId);
        tutor.add(novoTutor);
        nextId++;
        System.out.println("Tutor cadastrado com sucesso: " + novoTutor.getNome());
    }
    // UPDATE - Atualizar tutor pelo ID
    public void atualizarTutor(int id, Tutor novoTutor) {
        for (Tutor t : tutor) {
            if (t.getId() == id) {
                t.atualizarTutor(novoTutor);
                System.out.println("Tutor com ID " + id + " atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Tutor com ID " + id + " não encontrado.");
    }
    // READ - Listar todos os tutores
    public List<Tutor> listarTutores() {
        return tutor;
    }
    // READ - Buscar tutor por ID
    public Tutor buscarTutorPorId(int id) {
        for (Tutor t : tutor) {
            if (t.getId() == id) {
                return t;
            }
        }
        System.out.println("Tutor com ID " + id + " não encontrado.");
        return null;
    }
    // DELETE - Remover tutor pelo ID
    public void removerTutor(int id){
        for (Tutor t: tutor){
            if (t.getId() == id){
                tutor.remove(t);
                System.out.println("Tutor com ID " + id + " removido com sucesso!");
                return;
            }
        }
        System.out.println("Tutor com ID " + id + " não encontrado.");
    }

}
