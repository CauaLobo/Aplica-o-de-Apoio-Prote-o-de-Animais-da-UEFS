package view;

import controller.AnimalController;
import controller.SetorResponsavelController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes básicos para AnimalView simulando entradas do usuário.
 */
class AnimalViewTest {
    private AnimalController animalController;
    private SetorResponsavelController setorController;
    private AnimalView animalView;

    @BeforeEach
    void setUp() {
        animalController = new AnimalController(new ArrayList<>());
        setorController = new SetorResponsavelController(new ArrayList<>());
        Scanner scanner = new Scanner(System.in); // Será substituído em cada teste
        animalView = new AnimalView(animalController, setorController, scanner);
    }

    @Test
    void testListarAnimaisVazio() throws Exception {
        Method method = AnimalView.class.getDeclaredMethod("listarAnimais", java.util.List.class);
        method.setAccessible(true);
        method.invoke(animalView, new ArrayList<>());
    }

    @Test
    void testCadastrarAnimalSemSetor() throws Exception {
        InputStream sysInBackup = System.in;
        try {
            System.setIn(new ByteArrayInputStream("\n".getBytes()));
            Scanner sc = new Scanner(System.in);
            AnimalView view = new AnimalView(animalController, setorController, sc);
            Method method = AnimalView.class.getDeclaredMethod("cadastrarAnimal");
            method.setAccessible(true);
            method.invoke(view);
        } finally {
            System.setIn(sysInBackup);
        }
    }
}
