package sanp;

import grade.*;
import operations.*;
import java.util.Arrays;
import java.util.List;

/**
 * Demostración de operaciones: Binario, JSON, CSV y actualización
 * Adaptado al estilo profesional del Demo completo, sin modificar la lógica original.
 */
public class Demo {

    public static void main(String[] args) {

        System.out.println("=== DEMOSTRACIÓN COMPLETA DEL SISTEMA DE GESTIÓN DE ESTUDIANTES ===\n");

        // Crear estudiantes de ejemplo
        Student student1 = createStudent(1, "Ana", "García", "Sistemas", "6to", 9.2, 20, "ana@uacam.mx", Status.ACTIVO);
        Student student2 = createStudent(2, "Carlos", "Mendoza", "Software", "4to", 8.7, 21, "carlos@uacam.mx", Status.ACTIVO);
        Student student3 = createStudent(3, "María", "López", "Civil", "8vo", 7.5, 22, "maria@uacam.mx", Status.EGRESADO);

        List<Student> lista = Arrays.asList(student1, student2, student3);

        demonstrateBinary(lista);
        demonstrateJson(lista);
        demonstrateCsv(lista);
        demonstrateUpdate(student2);

        System.out.println("\n=== DEMOSTRACIÓN FINALIZADA ===");
    }

    // =========================
    //   CREAR ESTUDIANTE
    // =========================
    private static Student createStudent(int id, String name, String lastName,
                                         String degreeName, String semester,
                                         double promedio, int edad,
                                         String correo, Status status) {

        Student s = new Student();
        s.setIdStudent(id);
        s.setName(name);
        s.setLastName(lastName);
        s.setDegree(new DegreeFI(0, degreeName));
        s.setSemester(semester);
        s.setPromedio(promedio);
        s.setEdad(edad);
        s.setCorreo(correo);
        s.setStatus(status);
        return s;
    }

    // =========================
    //   BINARIO
    // =========================
    private static void demonstrateBinary(List<Student> students) {
        System.out.println("\n1. === DEMOSTRACIÓN BINARIO ===");

        Create create = new Create();
        Reading read = new Reading();
        String file = "demo_students.bin";

        System.out.println("1.1 Creando archivo con primer estudiante...");
        create.createBinary(file, students.get(0));

        System.out.println("1.2 Agregando más estudiantes...");
        create.appendBinary(file, students.get(1));
        create.appendBinary(file, students.get(2));

        System.out.println("1.3 Leyendo archivo binario completo:");
        List<Student> result = read.readBinary(file);
        result.forEach(System.out::println);
    }

    // =========================
    //   JSON
    // =========================
    private static void demonstrateJson(List<Student> students) {
        System.out.println("\n2. === DEMOSTRACIÓN JSON ===");

        Create create = new Create();
        Reading read = new Reading();
        String file = "demo_students.json";

        System.out.println("2.1 Creando archivo JSON con lista...");
        create.createJsonList(file, students);

        System.out.println("2.2 Leyendo JSON completo:");
        List<Student> result = read.readJson(file);
        result.forEach(System.out::println);
    }

    // =========================
    //   CSV
    // =========================
    private static void demonstrateCsv(List<Student> students) {
        System.out.println("\n3. === DEMOSTRACIÓN CSV ===");

        Create create = new Create();
        Reading read = new Reading();
        String file = "demo_students.csv";

        System.out.println("3.1 Creando archivo CSV...");
        create.createCsvList(file, students);

        System.out.println("3.2 Leyendo CSV completo:");
        List<Student> result = read.readCsv(file);
        result.forEach(System.out::println);
    }

    // =========================
    //   UPDATE
    // =========================
    private static void demonstrateUpdate(Student studentToUpdate) {
        System.out.println("\n4. === DEMOSTRACIÓN UPDATE ===");

        Update update = new Update();

        System.out.println("4.1 Modificando promedio del estudiante...");
        studentToUpdate.setPromedio(9.9);

        boolean success = update.update("demo_students.bin", studentToUpdate);

        if (success)
            System.out.println("4.2 ✔ Estudiante actualizado correctamente");
        else
            System.out.println("4.2 ✘ No se pudo actualizar");

        System.out.println("\n4.3 Archivo actualizado:");
        Reading read = new Reading();
        List<Student> result = read.readBinary("demo_students.bin");
        result.forEach(System.out::println);
    }
}
