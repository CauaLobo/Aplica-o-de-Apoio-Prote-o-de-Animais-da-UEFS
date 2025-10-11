package controller;

import model.SetorResponsavel;
import java.util.ArrayList;
import java.util.List;

public class SetorResponsavelController {

    // Repositório simulado (adapte para sua estrutura de persistência, ex: RepositorioSetor)
    private final List<SetorResponsavel> setores;

    public SetorResponsavelController() {
        this.setores = new ArrayList<>();
    }

    // --- MÉTODOS DE SERVIÇO ---
    public void adicionarSetor(SetorResponsavel setor) {
        setores.add(setor);
        System.out.println("\nSUCESSO: Setor '" + setor.getNome() + "' cadastrado com ID: " + setor.getId());
    }

    public SetorResponsavel buscarSetorPorId(int id) {
        for (SetorResponsavel setor : setores) {
            if (setor.getId() == id) {
                return setor;
            }
        }
        return null;
    }

    public List<SetorResponsavel> listarSetores() {
        return setores;
    }

    public void atualizarSetor(int id, SetorResponsavel dadosNovos) {
        SetorResponsavel setorExistente = buscarSetorPorId(id);
        if (setorExistente != null) {
            setorExistente.setNome(dadosNovos.getNome());
            setorExistente.setEndereco(dadosNovos.getEndereco());
            System.out.println("SUCESSO: Setor ID " + id + " atualizado.");
        } else {
            System.out.println("ERRO: Setor ID " + id + " não encontrado para atualização.");
        }
    }

    public void removerSetor(int id) {
        boolean removido = setores.removeIf(setor -> setor.getId() == id);
        if (removido) {
            System.out.println("SUCESSO: Setor ID " + id + " removido.");
        } else {
            System.out.println("ERRO: Setor ID " + id + " não encontrado.");
        }
    }
}