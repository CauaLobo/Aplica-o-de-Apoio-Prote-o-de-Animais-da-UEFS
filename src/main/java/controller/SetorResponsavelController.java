package controller;

import model.SetorResponsavel;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador responsável pelas operações de cadastro, busca, atualização, listagem e remoção de setores responsáveis.
 * Gerencia uma lista de setores em memória, simulando um repositório.
 */
public class SetorResponsavelController {

    /**
     * Lista de setores responsáveis simulando um repositório em memória.
     */
    private final List<SetorResponsavel> setores;

    /**
     * Construtor padrão. Inicializa a lista de setores como vazia.
     */
    public SetorResponsavelController() {
        this.setores = new ArrayList<>();
    }

    /**
     * Construtor que aceita uma lista de setores já carregada.
     * @param setores Lista de setores a ser gerenciada.
     */
    public SetorResponsavelController(List<SetorResponsavel> setores) {
        this.setores = setores;
    }

    /**
     * Adiciona um novo setor responsável à lista.
     * @param setor O setor a ser cadastrado.
     */
    public void adicionarSetor(SetorResponsavel setor) {
        setores.add(setor);
        System.out.println("\nSUCESSO: Setor '" + setor.getNome() + "' cadastrado com ID: " + setor.getId());
    }

    /**
     * Busca um setor responsável pelo seu ID.
     * @param id O ID do setor a ser buscado.
     * @return O setor encontrado ou null se não existir.
     */
    public SetorResponsavel buscarSetorPorId(int id) {
        for (SetorResponsavel setor : setores) {
            if (setor.getId() == id) {
                return setor;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de todos os setores responsáveis cadastrados.
     * @return Lista de setores responsáveis.
     */
    public List<SetorResponsavel> listarSetores() {
        return setores;
    }

    /**
     * Atualiza os dados de um setor responsável existente pelo ID.
     * @param id O ID do setor a ser atualizado.
     * @param dadosNovos O objeto SetorResponsavel com os novos dados.
     */
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

    /**
     * Remove um setor responsável da lista pelo seu ID.
     * @param id O ID do setor a ser removido.
     */
    public void removerSetor(int id) {
        boolean removido = setores.removeIf(setor -> setor.getId() == id);
        if (removido) {
            System.out.println("SUCESSO: Setor ID " + id + " removido.");
        } else {
            System.out.println("ERRO: Setor ID " + id + " não encontrado.");
        }
    }
}