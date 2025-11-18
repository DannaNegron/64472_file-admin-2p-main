package grade;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idStudent;
    private String name;
    private String lastName;
    private DegreeFI degree;
    private String semester;
    private double promedio;

    // Nuevos atributos
    private Status status; // enum
    private int edad;
    private String correo;

    // ---------- SETTERS ----------
    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDegree(DegreeFI degree) {
        this.degree = degree;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // ---------- GETTERS ----------
    public int getIdStudent() {
        return idStudent;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public DegreeFI getDegree() {
        return degree;
    }

    public String getSemester() {
        return semester;
    }

    public double getPromedio() {
        return promedio;
    }

    public Status getStatus() {
        return status;
    }

    public int getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    // ---------- toString ----------
    @Override
    public String toString() {
        return idStudent + "," +
                name + "," +
                lastName + "," +
                degree + "," +
                semester + "," +
                promedio + "," +
                status + "," +
                edad + "," +
                correo;
    }

    // ---------- toJson ----------
    public String toJson() {
        return "{\n" +
                "  \"idStudent\": " + idStudent + ",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"lastName\": \"" + lastName + "\",\n" +
                "  \"degree\": {\n" +
                "      \"idDegree\": " + degree.getIdDegree() + ",\n" +
                "      \"degreeName\": \"" + degree.getDegreeName() + "\"\n" +
                "  },\n" +
                "  \"semester\": \"" + semester + "\",\n" +
                "  \"promedio\": " + promedio + ",\n" +
                "  \"status\": \"" + status + "\",\n" +
                "  \"edad\": " + edad + ",\n" +
                "  \"correo\": \"" + correo + "\"\n" +
                "}";
    }

    // ---------- fromJson ----------
    public static Student fromJson(String json) {
        Student s = new Student();

        json = json.replace("{", "")
                .replace("}", "")
                .trim();

        String[] parts = json.split(",");

        for (String part : parts) {
            String[] keyValue = part.split(":");

            if (keyValue.length < 2)
                continue;

            String key = keyValue[0].replace("\"", "").trim();
            String value = keyValue[1].replace("\"", "").trim();

            switch (key) {
                case "idStudent":
                    s.setIdStudent(Integer.parseInt(value));
                    break;
                case "name":
                    s.setName(value);
                    break;
                case "lastName":
                    s.setLastName(value);
                    break;
                case "semester":
                    s.setSemester(value);
                    break;
                case "promedio":
                    s.setPromedio(Double.parseDouble(value));
                    break;
                case "status":
                    s.setStatus(Status.valueOf(value));
                    break;
                case "edad":
                    s.setEdad(Integer.parseInt(value));
                    break;
                case "correo":
                    s.setCorreo(value);
                    break;
            }

            // Degree processing
            if (part.contains("degreeName")) {
                String degreeName = part.split(":")[1].replace("\"", "").trim();
                s.setDegree(DegreeFI.fromName(degreeName));
            }
        }

        return s;
    }

    // ------método toCsv()--------------
    public String toCsv() {
        return idStudent + "," +
                name + "," +
                lastName + "," +
                (degree != null ? degree.getIdDegree() : "0") + "," +
                (degree != null ? degree.getDegreeName() : "SIN_DEGREE") + "," +
                semester + "," +
                promedio + "," +
                (status != null ? status.name() : "SIN_STATUS") + "," +
                edad + "," +
                correo;
    }

}