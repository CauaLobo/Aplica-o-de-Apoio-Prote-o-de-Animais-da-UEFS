package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Animal.
 */
class AnimalTest {

    @Test
    void testConstrutorEGetters() {
        SetorResponsavel setor = new SetorResponsavel("Setor 1", "Endereço 1", null);
        Animal animal = new Animal("Rex", Especie.CACHORRO, "SRD", 5, "M", SituacaoAtual.EM_OBSERVACAO, setor);
        assertEquals("Rex", animal.getNome());
        assertEquals(Especie.CACHORRO, animal.getEspecie());
        assertEquals("SRD", animal.getRaca());
        assertEquals(5, animal.getIdade());
        assertEquals("M", animal.getSexo());
        assertEquals(SituacaoAtual.EM_OBSERVACAO, animal.getSituacaoAtual());
        assertEquals(setor, animal.getSetorResponsavel());
    }

    @Test
    void testSetIdadeValida() {
        Animal animal = new Animal();
        animal.setIdade(3);
        assertEquals(3, animal.getIdade());
    }

    @Test
    void testSetIdadeInvalida() {
        Animal animal = new Animal();
        assertThrows(IllegalArgumentException.class, () -> animal.setIdade(-1));
    }

    @Test
    void testAtualizarAnimal() {
        SetorResponsavel setor1 = new SetorResponsavel("Setor 1", "Endereço 1", null);
        SetorResponsavel setor2 = new SetorResponsavel("Setor 2", "Endereço 2", null);
        Animal a1 = new Animal("Rex", Especie.CACHORRO, "SRD", 5, "M", SituacaoAtual.EM_OBSERVACAO, setor1);
        Animal a2 = new Animal("Luna", Especie.GATO, "Persa", 2, "F", SituacaoAtual.DISPONIVEL_PARA_ADOCAO, setor2);
        a1.atualizar(a2);
        assertEquals("Luna", a1.getNome());
        assertEquals(Especie.GATO, a1.getEspecie());
        assertEquals("Persa", a1.getRaca());
        assertEquals(2, a1.getIdade());
        assertEquals("F", a1.getSexo());
        assertEquals(SituacaoAtual.DISPONIVEL_PARA_ADOCAO, a1.getSituacaoAtual());
        assertEquals(setor2, a1.getSetorResponsavel());
    }
}
