package controller;


import model.SetorResponsavel;

import java.util.ArrayList;
import java.util.List;

public class SetorResponsavelController {
    private List<SetorResponsavel> setores = new ArrayList<>();
    private static int nextId = 1;

    // CREATE - Cadastrar setor responsavel
    public void adicionarSetor(SetorResponsavel setor) {
        setor.setId(nextId);
        setores.add(setor);
        nextId++;
        System.out.println("Setor cadastrado com sucesso: " + setor.getNome());


    }
    // UPDATE - Atualizar setor pelo ID
    public void atualizarSetor(int id, SetorResponsavel novoSetor) {
        for (SetorResponsavel s : setores) {
            if (s.getId() == id) {
                s.atualizarSetor(novoSetor);
                System.out.println("Setor com ID " + id + " atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Setor com ID " + id + " não encontrado.");
    }
    // READ - Listar todos os setores
    public List<SetorResponsavel> listarSetores() {
        return setores;
    }
    // READ - Buscar setor por ID
    public SetorResponsavel buscarSetorPorId(int id) {
        for (SetorResponsavel s : setores) {
            if (s.getId() == id) {
                return s;
            }
        }
        System.out.println("Setor com ID " + id + " não encontrado.");
        return null;
    }
    // DELETE - Remover setor pelo ID
    public void removerSetor(int id){
        for (SetorResponsavel s: setores){
            if (s.getId() == id){
                setores.remove(s);
                System.out.println("Setor com ID " + id + " removido com sucesso!");
                return;
            }
        }
        System.out.println("Setor com ID " + id + " não encontrado.");
    }
}

