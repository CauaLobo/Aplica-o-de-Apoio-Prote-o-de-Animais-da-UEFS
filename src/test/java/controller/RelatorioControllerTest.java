package controller;

import model.Animal;
import model.SetorResponsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para RelatorioController.
 * Testa apenas a geração dos relatórios sem salvar arquivos.
 */
class RelatorioControllerTest {
    private AnimalController animalController;
    private SetorResponsavelController setorController;
    private RelatorioController relatorioController;

    @BeforeEach
    void setUp() {
        animalController = new AnimalController(new ArrayList<>());
        setorController = new SetorResponsavelController(new ArrayList<>());
        relatorioController = new RelatorioController(animalController, setorController);
    }

    @Test
    void testRelatorioComAnimaisVazios() {
        // Não deve lançar exceção ao gerar relatórios com listas vazias
        assertDoesNotThrow(() -> relatorioController.gerarRelatorioCompletoAnimais("relatorio_test.txt"));
        assertDoesNotThrow(() -> relatorioController.gerarRelatorioAnimaisPorSetor("relatorio_test.txt"));
        assertDoesNotThrow(() -> relatorioController.gerarRelatorioAnimaisPorEspecie("relatorio_test.txt"));
    }
}

