package grade;

import java.io.Serializable;

public class DegreeFI implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idDegree;
    private String degreeName;

    // Constructor vacío
    public DegreeFI() {
    }

    // Constructor completo
    public DegreeFI(int idDegree, String degreeName) {
        this.idDegree = idDegree;
        this.degreeName = degreeName;
    }

    // Setters
    public void setIdDegree(int idDegree) {
        this.idDegree = idDegree;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    // Getters
    public int getIdDegree() {
        return this.idDegree;
    }

    public String getDegreeName() {
        return this.degreeName;
    }

    @Override
    public String toString() {
        return idDegree + "," + degreeName;
    }

    // MÉTODO QUE NECESITAS PARA fromJson
    public static DegreeFI fromName(String name) {
        DegreeFI degree = new DegreeFI();
        degree.setDegreeName(name);
        degree.setIdDegree(0); // id default
        return degree;
    }
}
