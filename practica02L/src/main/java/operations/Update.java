package operations;

import grade.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Update {

    // MÉTODO GENERAL: Decide si es BIN o JSON
    // ============================================================
    public boolean update(String fileName, Student updatedStudent) {

        if (fileName.endsWith(".bin")) {
            return updateBinary(fileName, updatedStudent);

        } else if (fileName.endsWith(".json")) {
            return updateJson(fileName, updatedStudent);

        } else {
            System.out.println("Formato no soportado: " + fileName);
            return false;
        }
    }

    // UPDATE PARA ARCHIVO BINARIO
    // ============================================================
    private boolean updateBinary(String fileName, Student updatedStudent) {

        List<Student> students = new ArrayList<>();

        // Leer binario con validación segura
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {

            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                List<?> tempList = (List<?>) obj;
                List<Student> converted = new ArrayList<>();

                for (Object o : tempList) {
                    if (o instanceof Student) {
                        converted.add((Student) o);
                    } else {
                        System.out.println("Objeto inválido dentro del archivo binario: " + o.getClass().getName());
                    }
                }

                if (converted.isEmpty()) {
                    System.out.println("No se encontraron objetos Student válidos en el archivo.");
                    return false;
                }

                students = converted;
            } else {
                System.out.println("El archivo binario no contiene una lista válida.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al leer archivo binario: " + e.getMessage());
            return false;
        }

        // Buscar y reemplazar alumno
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getIdStudent() == updatedStudent.getIdStudent()) {
                students.set(i, updatedStudent);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Estudiante no encontrado en BIN.");
            return false;
        }

        // Guardar nuevamente
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);

        } catch (IOException e) {
            System.out.println("Error al escribir archivo binario: " + e.getMessage());
            return false;
        }

        return true;
    }

    // UPDATE PARA ARCHIVO JSON
    // ============================================================
    private boolean updateJson(String fileName, Student updatedStudent) {

        List<Student> students = new ArrayList<>();

        // Leer JSON línea por línea
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String raw;

            while ((raw = br.readLine()) != null) {
                raw = raw.trim();
                if (!raw.isEmpty()) {
                    students.add(Student.fromJson(raw));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer JSON: " + e.getMessage());
            return false;
        }

        // Buscar y reemplazar estudiante
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getIdStudent() == updatedStudent.getIdStudent()) {
                students.set(i, updatedStudent);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Estudiante no encontrado en JSON.");
            return false;
        }

        // Escribir JSON nuevamente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (Student s : students) {
                bw.write(s.toJson());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al escribir JSON: " + e.getMessage());
            return false;
        }

        return true;
    }
}