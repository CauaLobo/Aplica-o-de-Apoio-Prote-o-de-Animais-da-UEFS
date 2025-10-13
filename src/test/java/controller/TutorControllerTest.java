package controller;

import model.Tutor;
import model.Endereco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para TutorController.
 */
class TutorControllerTest {

    @Test
    void testAdicionarBuscarRemoverTutor() {
        TutorController controller = new TutorController();
        Tutor t = new Tutor("Maria", new Endereco(), "12345678", "maria@a.com");
        controller.adicionarTutor(t);
        assertEquals(t, controller.buscarTutorPorId(t.getId()));
        controller.removerTutor(t.getId());
        assertNull(controller.buscarTutorPorId(t.getId()));
    }

    @Test
    void testAtualizarTutor() {
        TutorController controller = new TutorController();
        Tutor t = new Tutor("Maria", new Endereco(), "12345678", "maria@a.com");
        controller.adicionarTutor(t);
        Tutor novo = new Tutor("Ana", new Endereco(), "87654321", "ana@b.com");
        controller.atualizarTutor(t.getId(), novo);
        Tutor atualizado = controller.buscarTutorPorId(t.getId());
        assertEquals("Ana", atualizado.getNome());
        assertEquals("87654321", atualizado.getTelefone());
        assertEquals("ana@b.com", atualizado.getEmail());
    }
}
