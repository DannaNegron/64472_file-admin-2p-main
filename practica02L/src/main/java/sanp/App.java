package sanp;

import grade.Student;
import grade.DegreeFI;
import operations.Reading;
import operations.Writing;
import grade.Status;

public class App {
    public static void main(String[] args) {
        Student student = readStudent();
        
        saveStudent(student);     // Guardar CSV / JSON / BINARIO
        loadStudent();            // Leer CSV / JSON / BINARIO
    }

    public static Student readStudent() {
        Student student = new Student();

        DegreeFI degree = new DegreeFI(1, "Ingeniería en Software");

        student.setIdStudent(1);
        student.setName("Sergio");
        student.setLastName("Lopez");
        student.setDegree(degree);
        student.setSemester("7");
        student.setPromedio(9.1);
        student.setStatus(Status.ACTIVO);
        student.setEdad(22);
        student.setCorreo("sergio@mail.com");

        return student;
    }

    public static void saveStudent(Student student) {
        Writing writer = new Writing();

        writer.writeCsv("studentsDB.csv", student);   // funciona
        writer.writeJson("studentsDB.json", student); // funciona
        writer.writeBinaryStudent("studentsDB.dat", student); // funciona
    }

    public static void loadStudent() {
        Reading reader = new Reading();

        reader.readCsv("studentsDB.csv");       // funciona
        reader.readJson("studentsDB.json");     // funciona
        reader.readBinary("studentsDB.dat");    // funciona
    }
}

