package controller;

import model.Animal;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador responsável pelas operações CRUD de animais.
 * Gerencia uma lista de animais em memória, permitindo cadastro, busca, atualização e remoção.
 */
public class AnimalController {
    /**
     * Lista de animais simulando um banco de dados em memória.
     */
    private List<Animal> animais = new ArrayList<>();

    /**
     * Construtor que inicializa o controlador com uma lista de animais existente.
     * @param animais Lista de animais a ser gerenciada.
     */
    public AnimalController(List<Animal> animais) {
        this.animais = animais;
    }

    /**
     * Adiciona um novo animal à lista.
     * @param animal O animal a ser cadastrado.
     */
    public void adicionarAnimal(Animal animal) {
        animais.add(animal);
        System.out.println("Animal cadastrado com sucesso: " + animal.getNome());
    }

    /**
     * Retorna a lista de todos os animais cadastrados.
     * @return Lista de animais.
     */
    public List<Animal> listarAnimais() {
        return animais;
    }

    /**
     * Busca um animal pelo seu ID.
     * @param id O ID do animal a ser buscado.
     * @return O animal encontrado ou null se não existir.
     */
    public Animal buscarAnimalPorId(int id) {
        for (Animal a : animais) {
            if (a.getId() == id) {
                return a;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
        return null;
    }

    /**
     * Atualiza os dados de um animal existente pelo ID.
     * @param id O ID do animal a ser atualizado.
     * @param novoAnimal O objeto Animal com os novos dados.
     */
    public void atualizarAnimal(int id, Animal novoAnimal) {
        for (Animal a: animais){
            if (a.getId()== id){
                a.atualizar(novoAnimal);
                System.out.println("Animal com ID " + id + " atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
    }

    /**
     * Remove um animal da lista pelo seu ID.
     * @param id O ID do animal a ser removido.
     */
    public void removerAnimal(int id) {
        for (Animal a: animais) {
            if (a.getId() == id) {
                animais.remove(a);
                System.out.println("Animal com ID " + id + " removido com sucesso!");
                return;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
    }
}
