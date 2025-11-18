package operations;

import grade.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Delete {

    // ============================================================
    // ELIMINAR POR ID EN ARCHIVO BINARIO
    // ============================================================
    public void deleteFromBinary(String fileName, int id) {
        List<Student> students = new ArrayList<>();

        // Leer archivo existente
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {

            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o instanceof Student) {
                        Student s = (Student) o;
                        if (s.getIdStudent() != id) { // mantener los que no se eliminan
                            students.add(s);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo binario no encontrado. No se eliminó nada.");
            return;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer archivo binario: " + e.getMessage());
            return;
        }

        // Reescribir archivo con la nueva lista
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Estudiante eliminado del archivo binario.");
        } catch (IOException e) {
            System.out.println("Error al escribir archivo binario: " + e.getMessage());
        }
    }

    // ============================================================
    // ELIMINAR POR ID EN ARCHIVO JSON
    // ============================================================
    public void deleteFromJson(String fileName, int id) {
        List<Student> students = new ArrayList<>();

        // Leer JSON simple generado por tu CreateJsonList
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            StringBuilder json = new StringBuilder();

            while ((line = br.readLine()) != null) {
                json.append(line);
            }

            // Formato esperado:
            // {"students": [ { ... }, { ... } ] }

            String raw = json.toString()
                    .replace("{\"students\": [", "")
                    .replace("]}", "");

            if (!raw.trim().isEmpty()) {
                String[] items = raw.split("},");

                for (String item : items) {
                    item = item.trim();
                    if (!item.endsWith("}"))
                        item += "}";

                    Student s = Student.fromJson(item);
                    if (s.getIdStudent() != id) {
                        students.add(s);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo JSON no encontrado. No se eliminó nada.");
            return;
        } catch (IOException e) {
            System.out.println("Error al leer archivo JSON: " + e.getMessage());
            return;
        }

        // Reescribir JSON con estudiantes filtrados
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("{\"students\": [\n");

            for (int i = 0; i < students.size(); i++) {
                bw.write("  " + students.get(i).toJson());
                if (i < students.size() - 1)
                    bw.write(",");
                bw.newLine();
            }

            bw.write("]}");

            System.out.println("Estudiante eliminado del archivo JSON.");

        } catch (IOException e) {
            System.out.println("Error al escribir archivo JSON: " + e.getMessage());
        }
    }
}
