package controller;

import model.Animal;
import model.Especie;
import model.SetorResponsavel;
import model.SituacaoAtual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para AnimalController.
 */
class AnimalControllerTest {
    private AnimalController controller;
    private SetorResponsavel setor;

    @BeforeEach
    void setUp() {
        setor = new SetorResponsavel("Setor Teste", "Endereço Teste", null);
        controller = new AnimalController(new ArrayList<>());
    }

    @Test
    void testAdicionarBuscarRemoverAnimal() {
        Animal animal = new Animal("Rex", Especie.CACHORRO, "SRD", 3, "M", SituacaoAtual.EM_OBSERVACAO, setor);
        controller.adicionarAnimal(animal);
        assertEquals(animal, controller.buscarAnimalPorId(animal.getId()));
        controller.removerAnimal(animal.getId());
        assertNull(controller.buscarAnimalPorId(animal.getId()));
    }

    @Test
    void testAtualizarAnimal() {
        Animal animal = new Animal("Rex", Especie.CACHORRO, "SRD", 3, "M", SituacaoAtual.EM_OBSERVACAO, setor);
        controller.adicionarAnimal(animal);
        Animal novo = new Animal("Luna", Especie.GATO, "Persa", 2, "F", SituacaoAtual.DISPONIVEL_PARA_ADOCAO, setor);
        controller.atualizarAnimal(animal.getId(), novo);
        Animal atualizado = controller.buscarAnimalPorId(animal.getId());
        assertEquals("Luna", atualizado.getNome());
        assertEquals(Especie.GATO, atualizado.getEspecie());
        assertEquals("Persa", atualizado.getRaca());
        assertEquals(2, atualizado.getIdade());
        assertEquals("F", atualizado.getSexo());
        assertEquals(SituacaoAtual.DISPONIVEL_PARA_ADOCAO, atualizado.getSituacaoAtual());
    }

    @Test
    void testListarAnimais() {
        Animal a1 = new Animal("Rex", Especie.CACHORRO, "SRD", 3, "M", SituacaoAtual.EM_OBSERVACAO, setor);
        Animal a2 = new Animal("Luna", Especie.GATO, "Persa", 2, "F", SituacaoAtual.DISPONIVEL_PARA_ADOCAO, setor);
        controller.adicionarAnimal(a1);
        controller.adicionarAnimal(a2);
        List<Animal> lista = controller.listarAnimais();
        assertTrue(lista.contains(a1));
        assertTrue(lista.contains(a2));
        assertEquals(2, lista.size());
    }
}

