package view;

import controller.TutorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes básicos para TutorView simulando entradas do usuário.
 */
class TutorViewTest {
    private TutorController tutorController;
    private TutorView tutorView;

    @BeforeEach
    void setUp() {
        tutorController = new TutorController(new ArrayList<>());
        Scanner scanner = new Scanner(System.in); // Será substituído em cada teste
        tutorView = new TutorView(tutorController, scanner);
    }

    @Test
    void testListarTutoresVazio() throws Exception {
        Method method = TutorView.class.getDeclaredMethod("listarTutores", java.util.List.class);
        method.setAccessible(true);
        method.invoke(tutorView, new ArrayList<>());
    }

    @Test
    void testCadastrarTutorEntradaInvalida() throws Exception {
        InputStream sysInBackup = System.in;
        try {
            System.setIn(new ByteArrayInputStream("\n\n\n\n\n\n\n\n".getBytes()));
            Scanner sc = new Scanner(System.in);
            TutorView view = new TutorView(tutorController, sc);
            Method method = TutorView.class.getDeclaredMethod("cadastrarTutor");
            method.setAccessible(true);
            method.invoke(view);
        } finally {
            System.setIn(sysInBackup);
        }
    }
}
