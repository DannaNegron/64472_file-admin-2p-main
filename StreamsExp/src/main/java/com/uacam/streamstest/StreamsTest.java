package com.uacam.streamstest;

import java.io.*;
import java.util.ArrayList;

/**
 * @author sergi
 */
public class StreamsTest {

    private static final String EMPLEADOS_FILE = "EmpleadosUAC_DB.txt";

    static void escribir() {
        try (OutputStream fos = new FileOutputStream("datos.txt")) {

            for (int i = 0x10; i < 0x19; i++) {
                fos.write(i);
            }

        } catch (FileNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    static void leer() {
        try (InputStream fis = new FileInputStream("datos.txt")) {

            // int val;
            // do {
            // val = fis.read();
            // if (val == -1) {
            // System.err.println("FIN");
            // } else {
            // System.err.println(val);
            // }
            // } while (val != -1);
            byte[] arr = new byte[7];
            int cuantos = fis.read(arr);
            System.err.println(cuantos);
            cuantos = fis.read(arr);
            System.err.println(cuantos);

        } catch (FileNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    static void escribirObjeto() {
        try (OutputStream fos = new FileOutputStream("Objetos.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            ArrayList<String> elemList = new ArrayList<>();
            elemList.add("hello");
            elemList.add("from");
            elemList.add("elemList");

            double x = 3.5;

            oos.writeDouble(x);
            oos.writeUTF("Hola Mundo");
            oos.writeObject(elemList);

        } catch (FileNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    static void leerObjeto() {
        try (InputStream fis = new FileInputStream("Objetos.txt"); ObjectInputStream ois = new ObjectInputStream(fis)) {

            System.out.println(ois.readDouble());
            System.out.println(ois.readUTF());

            ArrayList<String> list = (ArrayList<String>) ois.readObject();
            System.err.println(list.size());

        } catch (FileNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (ClassNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    static void escribirEmpleado() {

        var empleados = new ArrayList<EmpleadoUAC>();

        empleados.add(new EmpleadoUAC("Sergio", "serganoh@uacam.mx", "FDI"));
        empleados.add(new EmpleadoUAC("Andres", "andres@uacam.mx", "FDM"));
        empleados.add(new EmpleadoUAC("Pedro", "pedro@uacam.mx", "FDB"));

        try (OutputStream fos = new FileOutputStream(EMPLEADOS_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(empleados);

        } catch (FileNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    static void leerEmpleado() {

        ArrayList<EmpleadoUAC> empleadosUAC = new ArrayList<>();

        // Si el archivo no existe, lo creamos primero para evitar FileNotFoundException
        if (!new File(EMPLEADOS_FILE).exists()) {
            System.out.println("Archivo de empleados no encontrado. Creando archivo inicial...");
            escribirEmpleado();
        }

        try (InputStream fis = new FileInputStream(EMPLEADOS_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            empleadosUAC = (ArrayList<EmpleadoUAC>) ois.readObject();

        } catch (FileNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (ClassNotFoundException ex) {
            System.getLogger(StreamsTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        for (EmpleadoUAC euac : empleadosUAC) {
            System.err.println(euac);
        }
    }

    public static void main(String[] args) {
        leerEmpleado();

    }
}
