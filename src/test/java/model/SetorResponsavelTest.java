package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe SetorResponsavel.
 */
class SetorResponsavelTest {

    @Test
    void testConstrutorEGetters() {
        Tutor tutor = new Tutor("João", new Endereco("Rua A", "Bairro B", "12345678", "Cidade C", "Estado D"), "1234567890", "joao@email.com");
        SetorResponsavel setor = new SetorResponsavel("Setor X", "Endereço Y", tutor);
        assertEquals("Setor X", setor.getNome());
        assertEquals("Endereço Y", setor.getEndereco());
        assertEquals(tutor, setor.getTutorResponsavel());
    }

    @Test
    void testAtualizarSetor() {
        Tutor t1 = new Tutor("A", new Endereco(), "11111111", "a@a.com");
        Tutor t2 = new Tutor("B", new Endereco(), "22222222", "b@b.com");
        SetorResponsavel s1 = new SetorResponsavel("S1", "E1", t1);
        SetorResponsavel s2 = new SetorResponsavel("S2", "E2", t2);
        s1.atualizarSetor(s2);
        assertEquals("S2", s1.getNome());
        assertEquals("E2", s1.getEndereco());
        assertEquals(t2, s1.getTutorResponsavel());
    }
}
