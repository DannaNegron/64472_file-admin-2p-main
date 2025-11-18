package grade;

public enum Status {
    ACTIVO("Estudiante activo"),
    INACTIVO("Estudiante inactivo"),
    SUSPENDIDO("Estudiante suspendido"),
    EGRESADO("Estudiante egresado"),
    BAJA("Estudiante dado de baja");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}