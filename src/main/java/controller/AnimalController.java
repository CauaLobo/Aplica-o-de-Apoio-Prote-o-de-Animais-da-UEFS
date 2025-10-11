package controller;

import model.Animal;
import java.util.ArrayList;
import java.util.List;

public class AnimalController {
    // Lista simulando um banco de dados em memória
    private List<Animal> animais = new ArrayList<>();

    // CREATE - Cadastrar animal
    public void adicionarAnimal(Animal animal) {
        animais.add(animal);
        System.out.println("Animal cadastrado com sucesso: " + animal.getNome());
    }

    // READ - Listar todos os animais
    public List<Animal> listarAnimais() {
        return animais;
    }

    // READ - Buscar animal por ID
    public Animal buscarAnimalPorId(int id) {
        for (Animal a : animais) {
            if (a.getId() == id) {
                return a;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
        return null;
    }

    // UPDATE - Atualizar animal pelo ID
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

    // DELETE - Remover animal pelo ID
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
