package operations;

import grade.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Create para crear y guardar estudiantes en:
 * - Archivo binario
 * - Archivo JSON
 * - Archivo CSV (opcional)
 * 
 * Implementación usando try-with-resources
 */
public class Create {

    // ============================================================
    // GUARDAR UN SOLO ESTUDIANTE EN ARCHIVO BINARIO
    // ============================================================
    public void createBinary(String fileName, Student student) {
        List<Student> students = new ArrayList<>();
        students.add(student);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Estudiante guardado en BINARIO correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir archivo binario: " + e.getMessage());
        }
    }

    // ============================================================
    // AGREGAR ESTUDIANTE A ARCHIVO BINARIO EXISTENTE
    // ============================================================
    public void appendBinary(String fileName, Student student) {
        List<Student> students = new ArrayList<>();

        // Leer archivo existente
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o instanceof Student)
                        students.add((Student) o);
                }
            }
        } catch (Exception e) {
            System.out.println("No hay archivo previo, se creará uno nuevo.");
        }

        // Agregar estudiante
        students.add(student);

        // Guardar archivo nuevamente
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Estudiante agregado al BINARIO correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir archivo binario: " + e.getMessage());
        }
    }

    // ============================================================
    // GUARDAR UN ESTUDIANTE EN JSON
    // ============================================================
    public void createJson(String fileName, Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("{\"student\": ");
            bw.write(student.toJson());
            bw.write("}");

            System.out.println("Estudiante guardado en JSON correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir JSON: " + e.getMessage());
        }
    }

    // ============================================================
    // GUARDAR UNA LISTA DE ESTUDIANTES EN JSON
    // ============================================================
    public void createJsonList(String fileName, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("{\"students\": [\n");

            for (int i = 0; i < students.size(); i++) {
                bw.write("  " + students.get(i).toJson());
                if (i < students.size() - 1)
                    bw.write(",");
                bw.newLine();
            }

            bw.write("]}");

            System.out.println("Lista guardada en JSON correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir JSON: " + e.getMessage());
        }
    }

    // ============================================================
    // GUARDAR UN ESTUDIANTE EN CSV (OPCIONAL)
    // ============================================================
    public void createCsv(String fileName, Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(student.toCsv());
            System.out.println("Estudiante guardado en CSV correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir CSV: " + e.getMessage());
        }
    }

    // ============================================================
    // GUARDAR LISTA COMPLETA CSV (OPCIONAL)
    // ============================================================
    public void createCsvList(String fileName, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < students.size(); i++) {
                bw.write(students.get(i).toCsv());
                if (i < students.size() - 1)
                    bw.newLine();
            }

            System.out.println("Lista guardada en CSV correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir CSV: " + e.getMessage());
        }
    }
}
