package operations;

import grade.Student;

import java.io.*;
import java.util.List;

/**
 * Clase Writing adaptada a Student, DegreeFI y Status.
 * Compatible con CSV, JSON y Binario.
 */
public class Writing {

    // ============================================================
    // ESCRITURA CSV - UN ESTUDIANTE
    // ============================================================
    public void writeCsv(String fileName, Student student) {
        System.out.println("Escribiendo estudiante en CSV: " + fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(student.toCsv());
            System.out.println("Estudiante escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir CSV: " + e.getMessage());
        }
    }

    // ============================================================
    // APPEND CSV - AGREGA AL FINAL
    // ============================================================
    public void appendCsv(String fileName, Student student) {
        System.out.println("Agregando estudiante al archivo CSV: " + fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.newLine();
            bw.write(student.toCsv());
            System.out.println("Estudiante agregado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al agregar a CSV: " + e.getMessage());
        }
    }

    // ============================================================
    // ESCRIBIR LISTA CSV
    // ============================================================
    public void writeCsvList(String fileName, List<Student> students) {
        System.out.println("Escribiendo " + students.size() + " estudiantes en CSV: " + fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < students.size(); i++) {
                bw.write(students.get(i).toCsv());
                if (i < students.size() - 1) {
                    bw.newLine();
                }
            }

            System.out.println("Lista CSV escrita exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir lista CSV: " + e.getMessage());
        }
    }

    // ============================================================
    // ESCRITURA JSON - 1 ESTUDIANTE
    // ============================================================
    public void writeJson(String fileName, Student student) {
        System.out.println("Escribiendo estudiante en JSON: " + fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("{\n");
            bw.write("  \"student\": ");
            bw.write(student.toJson());
            bw.newLine();
            bw.write("}");

            System.out.println("JSON escrito correctamente.");

        } catch (IOException e) {
            System.out.println("Error al escribir JSON: " + e.getMessage());
        }
    }

    // ============================================================
    // ESCRIBIR LISTA EN JSON
    // ============================================================
    public void writeJsonList(String fileName, List<Student> students) {
        System.out.println("Escribiendo lista de estudiantes en JSON: " + fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("{\n");
            bw.write("  \"students\": [\n");

            for (int i = 0; i < students.size(); i++) {
                bw.write("    ");
                bw.write(students.get(i).toJson());
                if (i < students.size() - 1) {
                    bw.write(",");
                }
                bw.newLine();
            }

            bw.write("  ]\n");
            bw.write("}");

            System.out.println("Lista JSON escrita correctamente.");

        } catch (IOException e) {
            System.out.println("Error al escribir lista JSON: " + e.getMessage());
        }
    }

    // ============================================================
    // ESCRITURA EN BINARIO - LISTA COMPLETA
    // ============================================================
    public void writeBinary(String fileName, List<Student> students) {
        System.out.println("Escribiendo lista en binario: " + fileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {

            oos.writeObject(students);
            System.out.println("Archivo binario escrito correctamente.");

        } catch (IOException e) {
            System.out.println("Error al escribir binario: " + e.getMessage());
        }
    }

    // ============================================================
    // ESCRITURA EN BINARIO - 1 ESTUDIANTE
    // ============================================================
    public void writeBinaryStudent(String fileName, Student student) {
        System.out.println("Escribiendo un estudiante en binario: " + fileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {

            oos.writeObject(student);
            System.out.println("Estudiante escrito en binario.");

        } catch (IOException e) {
            System.out.println("Error en binario: " + e.getMessage());
        }
    }
}
