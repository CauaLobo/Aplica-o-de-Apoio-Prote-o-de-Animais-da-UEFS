package controller;

import model.Animais;
import java.util.ArrayList;
import java.util.List;

public class AnimaisController {
    // Lista simulando um banco de dados em memória
    private List<Animais> animais = new ArrayList<>();

    // CREATE - Cadastrar animal
    public void adicionarAnimal(Animais animal) {
        // Verifica se já existe um animal com o mesmo ID
        for (Animais a : animais) {
            if (a.getId() == animal.getId()) {
                System.out.println("Erro: Já existe um animal com o ID " + animal.getId());
                return;
            }
        }
        animais.add(animal);
        System.out.println("Animal cadastrado com sucesso: " + animal.getNome());
    }

    // READ - Listar todos os animais
    public List<Animais> listarAnimais() {
        return animais;
    }

    // READ - Buscar animal por ID
    public Animais buscarAnimalPorId(int id) {
        for (Animais a : animais) {
            if (a.getId() == id) {
                return a;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
        return null;
    }

    // UPDATE - Atualizar animal pelo ID
    public void atualizarAnimal(int id, Animais novoAnimal) {
        for (int i = 0; i < animais.size(); i++) {
            if (animais.get(i).getId() == id) {
                animais.set(i, novoAnimal);
                System.out.println("Animal com ID " + id + " atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
    }

    // DELETE - Remover animal pelo ID
    public void removerAnimal(int id) {
        for (int i = 0; i < animais.size(); i++) {
            if (animais.get(i).getId() == id) {
                Animais removido = animais.remove(i);
                System.out.println("Animal removido: " + removido.getNome());
                return;
            }
        }
        System.out.println("Animal com ID " + id + " não encontrado.");
    }
}
