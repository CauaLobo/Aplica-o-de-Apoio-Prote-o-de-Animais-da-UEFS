package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Tutor.
 */
class TutorTest {

    @Test
    void testConstrutorEGetters() {
        Endereco endereco = new Endereco("Rua A", "Bairro B", "12345678", "Cidade C", "Estado D");
        Tutor tutor = new Tutor("João", endereco, "1234567890", "joao@email.com");
        assertEquals("João", tutor.getNome());
        assertEquals(endereco, tutor.getEndereco());
        assertEquals("1234567890", tutor.getTelefone());
        assertEquals("joao@email.com", tutor.getEmail());
    }

    @Test
    void testSetTelefoneValido() {
        Tutor tutor = new Tutor();
        tutor.setTelefone("9876543210");
        assertEquals("9876543210", tutor.getTelefone());
    }

    @Test
    void testSetTelefoneInvalido() {
        Tutor tutor = new Tutor();
        assertThrows(IllegalArgumentException.class, () -> tutor.setTelefone("abc"));
    }

    @Test
    void testSetEmailValido() {
        Tutor tutor = new Tutor();
        tutor.setEmail("teste@exemplo.com");
        assertEquals("teste@exemplo.com", tutor.getEmail());
    }

    @Test
    void testSetEmailInvalido() {
        Tutor tutor = new Tutor();
        assertThrows(IllegalArgumentException.class, () -> tutor.setEmail("sem-arroba"));
    }

    @Test
    void testAtualizarTutor() {
        Endereco e1 = new Endereco("Rua 1", "Bairro 1", "11111111", "Cidade 1", "Estado 1");
        Endereco e2 = new Endereco("Rua 2", "Bairro 2", "22222222", "Cidade 2", "Estado 2");
        Tutor t1 = new Tutor("Maria", e1, "11111111", "maria@a.com");
        Tutor t2 = new Tutor("Ana", e2, "22222222", "ana@b.com");
        t1.atualizarTutor(t2);
        assertEquals("Ana", t1.getNome());
        assertEquals(e2, t1.getEndereco());
        assertEquals("22222222", t1.getTelefone());
        assertEquals("ana@b.com", t1.getEmail());
    }
}
