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

