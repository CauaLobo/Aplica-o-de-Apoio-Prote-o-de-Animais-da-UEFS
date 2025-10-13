package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Endereco.
 */
class EnderecoTest {

    @Test
    void testConstrutorEGetters() {
        Endereco e = new Endereco("Rua X", "Bairro Y", "12345678", "Cidade Z", "Estado W");
        assertEquals("Rua X", e.getRua());
        assertEquals("Bairro Y", e.getBairro());
        assertEquals("12345678", e.getCep());
        assertEquals("Cidade Z", e.getCidade());
        assertEquals("Estado W", e.getEstado());
    }

    @Test
    void testSetters() {
        Endereco e = new Endereco();
        e.setRua("Rua 1");
        e.setBairro("Bairro 2");
        e.setCep("87654321");
        e.setCidade("Cidade 3");
        e.setEstado("Estado 4");
        assertEquals("Rua 1", e.getRua());
        assertEquals("Bairro 2", e.getBairro());
        assertEquals("87654321", e.getCep());
        assertEquals("Cidade 3", e.getCidade());
        assertEquals("Estado 4", e.getEstado());
    }
}
