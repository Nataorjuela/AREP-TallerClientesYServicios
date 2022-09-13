[![ProjectDesign](https://www.herokucdn.com/deploy/button.png)](https://clientes-servidor.herokuapp.com/)


# Autor

Natalia Orjuela

Fecha: 11-09-2022

# TALLER CLIENTES Y SERVICIOS

Este taller presenta diferentes retos que loa ayudarán a explorar los conceptos de esquemas de nombres y de clientes y servicios. Adicionalmente, el taller le ayudará a explorar la arquitectura de las aplicaciones distribuidas sobre internet.

# RETO 1

Escriba un servidor web que soporte múlltiples solicitudes seguidas (no concurrentes). El servidor debe retornar todos los archivos solicitados, incluyendo páginas html e imágenes. Construya un sitio web con javascript para probar su servidor. Despliegue su solución en Heroku. NO use frameworks web como Spark o Spring, use solo Java y las librerías para manejo de la red.

# PRERREQUISITOS

* Java versión 8 o superior

* Maven versión 3.5 o superior

# COMPILACION

~~~

mvn package

~~~

# EJECUCION LOCAL

~~~
java -cp target/classes:target/dependency/* arep.MultipleServer.HttpServer 

~~~

# PRUEBAS

Después de realizar la ejecución de cualquiera de las dos formas, se accede de forma local abriendo un web browser y dirigiéndose a la dirección http://localhost:4567.

![image](https://user-images.githubusercontent.com/54339107/189791970-dbdc3e18-ed42-4ec1-8793-950c967372f1.png)

Ingresar un nombre para que se muestre el saludo

![image](https://user-images.githubusercontent.com/54339107/189791995-3f5f1f8c-f263-4d9f-a5a5-dd099c5520a2.png)

Seleccionar Mostrar imagen

![image](https://user-images.githubusercontent.com/54339107/189792029-083ccd3a-a9d3-438e-ae81-a7edc0b56bbd.png)

Seleccionar ocultar imagen

![image](https://user-images.githubusercontent.com/54339107/189792050-193f18bf-1c71-47a0-ac3f-e35daf2b780e.png)

