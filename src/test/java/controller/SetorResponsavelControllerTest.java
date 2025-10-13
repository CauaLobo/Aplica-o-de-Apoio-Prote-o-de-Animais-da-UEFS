package controller;

import model.SetorResponsavel;
import model.Tutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para SetorResponsavelController.
 */
class SetorResponsavelControllerTest {
    private SetorResponsavelController controller;
    private Tutor tutor;

    @BeforeEach
    void setUp() {
        controller = new SetorResponsavelController(new ArrayList<>());
        tutor = new Tutor("João", null, "12345678", "joao@email.com");
    }

    @Test
    void testAdicionarBuscarRemoverSetor() {
        SetorResponsavel setor = new SetorResponsavel("Setor 1", "Endereço 1", tutor);
        controller.adicionarSetor(setor);
        assertEquals(setor, controller.buscarSetorPorId(setor.getId()));
        controller.removerSetor(setor.getId());
        assertNull(controller.buscarSetorPorId(setor.getId()));
    }

    @Test
    void testAtualizarSetor() {
        SetorResponsavel setor = new SetorResponsavel("Setor 1", "Endereço 1", tutor);
        controller.adicionarSetor(setor);
        SetorResponsavel novo = new SetorResponsavel("Setor 2", "Endereço 2", tutor);
        controller.atualizarSetor(setor.getId(), novo);
        SetorResponsavel atualizado = controller.buscarSetorPorId(setor.getId());
        assertEquals("Setor 2", atualizado.getNome());
        assertEquals("Endereço 2", atualizado.getEndereco());
    }

    @Test
    void testListarSetores() {
        SetorResponsavel s1 = new SetorResponsavel("Setor 1", "Endereço 1", tutor);
        SetorResponsavel s2 = new SetorResponsavel("Setor 2", "Endereço 2", tutor);
        controller.adicionarSetor(s1);
        controller.adicionarSetor(s2);
        List<SetorResponsavel> lista = controller.listarSetores();
        assertTrue(lista.contains(s1));
        assertTrue(lista.contains(s2));
        assertEquals(2, lista.size());
    }
}

