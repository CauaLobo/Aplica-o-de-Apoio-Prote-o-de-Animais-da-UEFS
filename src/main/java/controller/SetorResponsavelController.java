package controller;

import model.SetorResponsavel;
import java.util.ArrayList;
import java.util.List;

public class SetorResponsavelController {

    // Repositório simulado (adapte para sua estrutura de persistência, ex: RepositorioSetor)
    private final List<SetorResponsavel> repositorio;
    private int proximoId;

    public SetorResponsavelController() {
        this.repositorio = new ArrayList<>();
        this.proximoId = 1;

        // Dados de teste para iniciar o sistema
        adicionarSetor(new SetorResponsavel(proximoId++, "Reitoria", "Prédio Principal"));
        adicionarSetor(new SetorResponsavel(proximoId++, "Biblioteca", "Módulo 2"));
    }

    // --- MÉTODOS DE SERVIÇO ---

    public void adicionarSetor(SetorResponsavel setor) {
        if (setor.getId() == 0) {
            setor.setId(proximoId++);
        }
        repositorio.add(setor);
        System.out.println("\nSUCESSO: Setor '" + setor.getNome() + "' cadastrado com ID: " + setor.getId());
    }

    /**
     * Permite o cadastro de um novo setor a partir de outra View (rápido).
     * @return O ID do setor recém-criado.
     */
    public int cadastrarSetorRapido(String nome, String endereco) {
        // Validação básica
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do setor não pode ser vazio.");
        }

        int novoId = proximoId;
        SetorResponsavel novoSetor = new SetorResponsavel(novoId, nome, endereco);

        // Chama o método principal de adição
        adicionarSetor(novoSetor);

        return novoId;
    }

    public SetorResponsavel buscarSetorPorId(int id) {
        for (SetorResponsavel setor : repositorio) {
            if (setor.getId() == id) {
                return setor;
            }
        }
        return null;
    }

    public List<SetorResponsavel> listarSetores() {
        return new ArrayList<>(repositorio);
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
        boolean removido = repositorio.removeIf(setor -> setor.getId() == id);
        if (removido) {
            System.out.println("SUCESSO: Setor ID " + id + " removido.");
        } else {
            System.out.println("ERRO: Setor ID " + id + " não encontrado.");
        }
    }
}