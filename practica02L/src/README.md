# Práctica 02 – Gestión de estudiantes con archivos (BIN, JSON, CSV)

## 1. Descripción general

Este proyecto implementa un sistema de gestión de estudiantes utilizando distintos formatos de almacenamiento de archivos:

•  Binario (.bin / .dat)
•  JSON (.json)
•  CSV (.csv)

El objetivo es practicar:
•  Serialización y deserialización de objetos.
•  Manejo de archivos de texto y binarios.
•  Conversión entre objetos Java y representaciones JSON / CSV.
•  Operaciones CRUD básicas sobre registros de estudiantes.

La estructura principal se divide en:
•  Modelo: Student, DegreeFI, Status (paquete grade)
•  Operaciones sobre archivos: Create, Reading, Writing, Update, Delete (paquete operations)
•  Aplicaciones de demostración: App, Demo (paquete sanp)


## 2. Esquema JSON del modelo


El modelo principal es Student, que contiene un objeto anidado DegreeFI y un estado (Status).

Esquema JSON:

{
  "Student": {
    "idStudent": "int",
    "name": "string",
    "lastName": "string",
    "degree": {
      "idDegree": "int",
      "degreeName": "string"
    },
    "semester": "string",
    "promedio": "double",
    "status": "enum [ACTIVO, INACTIVO, SUSPENDIDO, EGRESADO, BAJA]",
    "edad": "int",
    "correo": "string"
  }
}

### 2.1. Clases del modelo (paquete grade)

Clase Student
•  Atributos:
◦  int idStudent
◦  String name
◦  String lastName
◦  DegreeFI degree
◦  String semester
◦  double promedio
◦  Status status
◦  int edad
◦  String correo
•  Métodos principales:
◦  Getters y setters de todos los campos.
◦  toString(): devuelve una representación en texto separada por comas.
◦  toJson(): construye manualmente un objeto JSON con toda la información del estudiante, incluyendo el degree.
◦  static fromJson(String json): interpreta un JSON sencillo (basado en el formato generado por toJson) y crea un objeto Student.
▪  Usa Status.valueOf(...) para mapear el estado.
▪  Usa DegreeFI.fromName(...) para reconstruir el grado a partir del nombre.
◦  toCsv(): genera una línea CSV con todos los campos, manejando valores nulos con valores por defecto.

Clase DegreeFI
•  Atributos:
◦  int idDegree
◦  String degreeName
•  Métodos principales:
◦  Constructores vacío y completo.
◦  Getters y setters.
◦  toString(): devuelve "idDegree,degreeName".
◦  static DegreeFI fromName(String name): crea un DegreeFI usando únicamente el nombre del grado y un idDegree por defecto (0).

Enum Status
•  Valores:
◦  ACTIVO, INACTIVO, SUSPENDIDO, EGRESADO, BAJA
•  Cada valor tiene una descripción legible.
•  Método:
◦  String getDescription(): devuelve la descripción asociada al estado.


## 3. Métodos implementados y funcionamiento (capa de operaciones)


### 3.1. Creación y guardado – operations.Create

Responsabilidad: crear archivos iniciales con uno o varios estudiantes.

•  void createBinary(String fileName, Student student)
◦  Crea una nueva lista List<Student> con un solo estudiante y la guarda en binario mediante ObjectOutputStream.
◦  Sobrescribe el archivo si ya existe.
•  void appendBinary(String fileName, Student student)
◦  Lee el archivo binario existente como List<Student> (si no existe, crea uno nuevo).
◦  Agrega el nuevo estudiante a la lista.
◦  Vuelve a escribir la lista completa en el archivo.
•  void createJson(String fileName, Student student)
◦  Crea un archivo JSON con la estructura:
    {"student": { ... }}
•  Utiliza student.toJson() para generar el contenido.
•  void createJsonList(String fileName, List<Student> students)
◦  Genera un JSON con una lista de estudiantes:
    {"students": [ { ... }, { ... }, ... ]}
•  Escribe cada estudiante llamando a toJson().
•  void createCsv(String fileName, Student student)
◦  Escribe un solo estudiante como una línea CSV (student.toCsv()).
•  void createCsvList(String fileName, List<Student> students)
◦  Escribe varios estudiantes, cada uno en una línea CSV.

### 3.2. Lectura – operations.Reading

Responsabilidad: leer información de estudiantes desde archivos CSV, binarios y JSON.

•  List<Student> readCsv(String fileName)
◦  Lee el archivo línea por línea.
◦  Separa cada campo usando split(",") y asigna:
▪  idStudent, name, lastName, degreeName, semester, promedio, status, edad, correo.
◦  Crea un objeto Student por cada línea y los guarda en una lista.
◦  Imprime todos los estudiantes al final.
•  List<Student> readBinary(String fileName)
◦  Lee un objeto desde el archivo binario usando ObjectInputStream.
◦  Verifica que el objeto sea una List<?> y filtra únicamente las instancias de Student.
◦  Devuelve la lista de estudiantes e imprime su contenido.
•  List<Student> readJson(String fileName)
◦  Lee todo el archivo como un solo String.
◦  Si encuentra la clave "student", lo interpreta como un solo estudiante.
◦  Si encuentra la clave "students", lo interpreta como una lista de estudiantes.
◦  Usa métodos auxiliares (parseSingleStudent, parseStudentArray, parseStudent, extractText, extractInt, extractDouble) para extraer los valores de cada campo del JSON y construir los Student.

### 3.3. Escritura genérica – operations.Writing

Responsabilidad: funciones de escritura reutilizables para CSV, JSON y binario.

CSV:
•  void writeCsv(String fileName, Student student)
◦  Sobrescribe el archivo con un único estudiante (toCsv()).
•  void appendCsv(String fileName, Student student)
◦  Abre el archivo en modo append y agrega una nueva línea al final con los datos del estudiante.
•  void writeCsvList(String fileName, List<Student> students)
◦  Sobrescribe el archivo con toda la lista de estudiantes, cada uno en una línea CSV.

JSON:
•  void writeJson(String fileName, Student student)
◦  Escribe un JSON con la forma:
    {
      "student": { ... }
    }

•  void writeJsonList(String fileName, List<Student> students)
◦  Escribe un JSON con la forma:
    {
      "students": [
        { ... },
        { ... }
      ]
    }

Binario:
•  void writeBinary(String fileName, List<Student> students)
◦  Escribe una lista completa de estudiantes en el archivo binario.
•  void writeBinaryStudent(String fileName, Student student)
◦  Escribe un solo objeto Student en un archivo binario.

### 3.4. Actualización – operations.Update

Responsabilidad: actualizar la información de un estudiante ya existente en archivos binarios o JSON.

•  boolean update(String fileName, Student updatedStudent)
◦  Método general que decide el tipo de actualización según la extensión del archivo:
▪  Si el archivo termina en ".bin" → llama a updateBinary.
▪  Si termina en ".json" → llama a updateJson.
◦  Devuelve true si la actualización fue exitosa, false en caso contrario.
•  boolean updateBinary(String fileName, Student updatedStudent)
◦  Lee la lista de estudiantes desde el archivo binario.
◦  Busca al estudiante con el mismo idStudent.
◦  Si lo encuentra, reemplaza el objeto dentro de la lista y reescribe el archivo completo.
◦  Si no se encuentra el estudiante, muestra un mensaje y devuelve false.
•  boolean updateJson(String fileName, Student updatedStudent)
◦  Lee el archivo JSON línea por línea.
◦  Convierte cada línea a Student usando Student.fromJson(...).
◦  Busca al estudiante con el mismo idStudent y lo reemplaza en la lista.
◦  Sobrescribe el archivo escribiendo cada Student de nuevo usando toJson().
◦  Devuelve true si se actualizó, false si el estudiante no se encontró.

### 3.5. Eliminación – operations.Delete

Responsabilidad: eliminar estudiantes por identificador desde archivos binarios o JSON.

•  void deleteFromBinary(String fileName, int id)
◦  Lee el archivo binario como List<Student>.
◦  Filtra los estudiantes cuyo idStudent no coincide con el id indicado (es decir, elimina al que tenga ese id).
◦  Reescribe el archivo binario con la lista filtrada.
•  void deleteFromJson(String fileName, int id)
◦  Lee un JSON con la estructura {"students": [ { ... }, { ... } ]}.
◦  Convierte cada entrada a Student mediante Student.fromJson(...).
◦  Elimina el estudiante cuyo idStudent coincide con el id.
◦  Vuelve a escribir el JSON con los estudiantes restantes en el mismo formato {"students": [ ... ]}.


## 4. Manual de uso (breve manual de usuario)


### 4.1. Requisitos

•  Java JDK 23 (o compatible).
•  Apache Maven instalado.
•  Estar ubicado en la carpeta del proyecto practica02L.

4.2. Compilación del proyecto

Desde la carpeta raiz de practica02L, ejecutar:

mvn clean package

### 4.3. Ejecución de la demo básica – sanp.App

La clase App realiza el siguiente flujo:

1) Crea un estudiante de ejemplo (Sergio, con datos fijos).
2) Guarda al estudiante en:
•  studentsDB.csv
•  studentsDB.json
•  studentsDB.dat
3) Lee e imprime la información desde cada uno de estos archivos.

Comando para ejecutar:

mvn exec:java -Dexec.mainClass="sanp.App"

Salida esperada:
•  Mensajes en consola indicando la creación de los archivos CSV, JSON y binario.
•  Impresión del estudiante leído desde cada formato.

### 4.4. Ejecución de la demo completa – sanp.Demo

La clase Demo muestra todas las operaciones principales con varios estudiantes:

1) Crea tres estudiantes de ejemplo.
2) BINARIO:
•  Crea un archivo demo_students.bin con el primer estudiante.
•  Agrega los otros dos estudiantes (appendBinary).
•  Lee y muestra todos los estudiantes desde el archivo binario (readBinary).

3) JSON:
•  Crea demo_students.json con la lista completa de estudiantes (createJsonList).
•  Lee el archivo y muestra todos los estudiantes (readJson).

4) CSV:
•  Crea demo_students.csv con todos los estudiantes (createCsvList).
•  Lee el archivo CSV y muestra los estudiantes (readCsv).

5) UPDATE:
•  Modifica el promedio (promedio) de uno de los estudiantes.
•  Actualiza ese estudiante dentro del archivo demo_students.bin (update sobre BIN).
•  Vuelve a leer el archivo binario y muestra la lista actualizada.

Comando para ejecutar:

mvn exec:java -Dexec.mainClass="sanp.Demo"

Salida esperada:
•  Secciones impresas en la consola como:
◦  "=== DEMOSTRACIÓN BINARIO ==="
◦  "=== DEMOSTRACIÓN JSON ==="
◦  "=== DEMOSTRACIÓN CSV ==="
◦  "=== DEMOSTRACIÓN UPDATE ==="
•  Listas de estudiantes leídos desde cada archivo.
•  En la sección de actualización, se observa el cambio en el promedio del estudiante modificado.


## 5. Resumen
•  El proyecto implementa un flujo completo de CRUD sobre estudiantes usando archivos binarios, JSON y CSV.
•  El modelo está definido por las clases Student, DegreeFI y el enum Status.
•  Las operaciones de creación, lectura, escritura, actualización y eliminación se encapsulan en las clases Create, Reading, Writing, Update y Delete.
•  Las clases App y Demo funcionan como ejemplos prácticos y como un pequeño manual ejecutable del sistema.