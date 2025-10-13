package view;

import controller.SetorResponsavelController;
import controller.TutorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Testes básicos para SetorResponsavelView simulando entradas do usuário.
 */
class SetorResponsavelViewTest {
    private SetorResponsavelController setorController;
    private TutorController tutorController;
    private SetorResponsavelView setorView;

    @BeforeEach
    void setUp() {
        setorController = new SetorResponsavelController(new ArrayList<>());
        tutorController = new TutorController(new ArrayList<>());
        Scanner scanner = new Scanner(System.in); // Será substituído em cada teste
        setorView = new SetorResponsavelView(setorController, tutorController, scanner);
    }

    @Test
    void testListarSetoresVazio() throws Exception {
        Method method = SetorResponsavelView.class.getDeclaredMethod("listarSetores", java.util.List.class);
        method.setAccessible(true);
        method.invoke(setorView, new ArrayList<>());
    }

    @Test
    void testCadastrarSetorSemTutor() throws Exception {
        InputStream sysInBackup = System.in;
        try {
            System.setIn(new ByteArrayInputStream("\n".getBytes()));
            Scanner sc = new Scanner(System.in);
            SetorResponsavelView view = new SetorResponsavelView(setorController, tutorController, sc);
            Method method = SetorResponsavelView.class.getDeclaredMethod("cadastrarSetor");
            method.setAccessible(true);
            method.invoke(view);
        } finally {
            System.setIn(sysInBackup);
        }
    }
}
