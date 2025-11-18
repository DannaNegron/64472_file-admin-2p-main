package operations;

import grade.Student;
import grade.DegreeFI;
import grade.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reading {

    // ============================================================
    // LECTURA CSV
    // ============================================================
    public List<Student> readCsv(String fileName) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Student s = new Student();
                s.setIdStudent(Integer.parseInt(data[0]));
                s.setName(data[1]);
                s.setLastName(data[2]);

                // DegreeFI ← objeto con idDegree y name
                String degreeName = data[3];
                DegreeFI degree = new DegreeFI();
                degree.setDegreeName(degreeName);
                s.setDegree(degree);

                s.setSemester(data[4]);
                s.setPromedio(Double.parseDouble(data[5]));
                s.setStatus(Status.valueOf(data[6]));
                s.setEdad(Integer.parseInt(data[7]));
                s.setCorreo(data[8]);

                students.add(s);
            }

            System.out.println("CSV leído correctamente:");
            students.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error leyendo CSV: " + e.getMessage());
        }

        return students;
    }

    // ============================================================
    // LECTURA BINARIA (List<Student>)
    // ============================================================

    public List<Student> readBinary(String fileName) {
        List<Student> students = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {

            Object obj = ois.readObject();

            // Validación segura del objeto
            if (obj instanceof List<?>) {
                List<?> rawList = (List<?>) obj;

                // Convertir solo objetos que realmente sean Student
                for (Object o : rawList) {
                    if (o instanceof Student) {
                        students.add((Student) o);
                    } else {
                        System.out.println("Objeto inválido encontrado en la lista binaria");
                    }
                }

            } else {
                System.out.println("El archivo no contiene una lista de estudiantes válida.");
            }

            System.out.println("Alumnos desde archivo binario:");
            students.forEach(System.out::println);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer archivo binario");
            e.printStackTrace();
        }

        return students;
    }

    // ============================================================
    // LECTURA JSON (como tus archivos generados)
    // ============================================================
    public List<Student> readJson(String fileName) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }

            String json = sb.toString();

            // Verifica si es un solo estudiante
            if (json.contains("\"student\"")) {
                Student s = parseSingleStudent(json);
                students.add(s);
            }

            // Verifica si es un array
            else if (json.contains("\"students\"")) {
                students = parseStudentArray(json);
            }

            System.out.println("JSON leído exitosamente:");
            students.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error al leer JSON: " + e.getMessage());
        }

        return students;
    }

    // ============================================================
    // PARSE JSON — Muy simple acorde a tu formato
    // ============================================================

    private Student parseSingleStudent(String json) {
        json = json.substring(json.indexOf("{", json.indexOf("student")) + 1, json.lastIndexOf("}"));
        return parseStudent(json);
    }

    private List<Student> parseStudentArray(String json) {
        List<Student> list = new ArrayList<>();

        String arrayData = json.substring(json.indexOf("["), json.lastIndexOf("]") + 1);
        String[] entries = arrayData.split("},"); // Separación simple por tu formato

        for (String entry : entries) {
            entry = entry.replace("[", "")
                    .replace("]", "")
                    .trim();

            if (!entry.endsWith("}"))
                entry += "}";
            list.add(parseStudent(entry));
        }

        return list;
    }

    // ============================================================
    // PARSE de un estudiante JSON simplificado
    // ============================================================
    private Student parseStudent(String json) {

        Student s = new Student();
        DegreeFI degree = new DegreeFI();

        s.setIdStudent(extractInt(json, "idStudent"));
        s.setName(extractText(json, "name"));
        s.setLastName(extractText(json, "lastName"));

        degree.setIdDegree(extractInt(json, "idDegree"));
        degree.setDegreeName(extractText(json, "degreeName"));
        s.setDegree(degree);

        s.setSemester(extractText(json, "semester"));
        s.setPromedio(extractDouble(json, "promedio"));
        s.setStatus(Status.valueOf(extractText(json, "status")));
        s.setEdad(extractInt(json, "edad"));
        s.setCorreo(extractText(json, "correo"));

        return s;
    }

    private String extractText(String json, String key) {
        try {
            int start = json.indexOf("\"" + key + "\"") + key.length() + 4;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "";
        }
    }

    private int extractInt(String json, String key) {
        try {
            String value = json.split("\"" + key + "\"")[1]
                    .split(":")[1]
                    .split(",")[0]
                    .replace("}", "")
                    .trim();
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private double extractDouble(String json, String key) {
        try {
            String value = json.split("\"" + key + "\"")[1]
                    .split(":")[1]
                    .split(",")[0]
                    .replace("}", "")
                    .trim();
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
