# mentiunq-back
Repositorio back-end para mentiUNQ

[![Tests](https://github.com/PaisMariano/mentiunq-back/actions/workflows/build.yml/badge.svg)](https://github.com/PaisMariano/mentiunq-back/actions/workflows/build.yml)
![coverage](.github/badges/jacoco.svg)
![branches coverage](.github/badges/branches.svg)
### Requerimientos recomendados:

    JDK 1.8
    Maven 3.6.3

Revisar que estén bien seteadas las variables JAVA_HOME y MAVEN_HOME en computadora. Para corroborarlo puede probar por consola
```echo $JAVA_HOME``` y también  ```echo $MAVEN_HOME``` deben apuntar a cada carpeta correspondiente.

### Instalación
Una vez descargado el proyecto, al abrirlo con cualquier IDE automáticamente detecterá el POM y buscará instalar todas las dependencias. En caso de que haya algún problema o asegurarse que se instale correctamente, se puede ejecutar el comando
```mvn clean install```

### Setup inicial
Para poder levantar el proyecto, previamente se debe tener configurado el archivo application.properties bajo la ruta ```src/main/resources/```
Alli se deberá configurar los siguientes valores, donde como se puede observar por default se usa H2 como base de datos.

```server.port = {PORT}
spring.datasource.url=jdbc:h2:mem:mentidb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username= sa
spring.datasource.password= password

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

#### SQL DATA
#spring.datasource.username= {USERNAME_DB_MYSQL}
#spring.datasource.password= {PASSWORD_DB_MYSQL}
#spring.datasource.url=jdbc:mysql://localhost:3306/mentiunq

spring.security.oauth2.client.registration.google.clientId={GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.clientSecret={GOOGLE_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.redirectUri="{baseUrl}/oauth2/callback/{registrationId}"

spring.main.allow-bean-definition-overriding=true
project.version = 1.0.0
```

### Levantar proyecto

Para levantar el proyecto se deberá correr el comando
```mvn spring-boot:run```
Se debe esperar que se descarguen dependencias, levante el servidor y visualizará en la consola algo similar a 
```INFO 17392 --- [           main] c.u.e.l.p.mentiUnq.MentiUnqApplication   : Started MentiUnqApplication in 5.374 seconds (JVM running for 5.66)```


Esto levantará el servidor para poder recibir las solicitudes desde el front o postman. Notar que la url final será ```http://localhost:{PORT}/``` donde ```{PORT}``` corresponderá al puerto seteado en application.properties

### Estructura del proyecto

Para una correcta organización, el proyeco se dividió en varias carpetas dentro de la carpeta ```\src\main\java\com\unq\edu\li\pdesa\mentiUnq\```, donde cada una almacena elementos con responsabilidades separadas y se divide en:
- configs: carpeta que contienen toda la configuracíón inicial, por ejemplo la configuración para la utilización de oauth.
- cotrollers: carpeta con endpoints con responsabilidades específicas, por ejemplo SlideController será solo para los slides.
- exceptions: carpeta con las custom exceptions
- handlers: carpeta que interceptan llamados mediante AOP, por ejemplo la clase RestExceptionHandler interceptará las excepciones para mostrarlas mediante un formato normalizado
- models: carpeta con todas las clases propiamente dle negocio.
- protocols: carpeta con el protocolo utilizado para normalizar las respuestas.
- respositories: carpeta con los respositorios para comnunicarse con la base de datos.
- services: carpeta donde se encuentran los servicios para poder recibir las solicitudes desde los controllers y saber como interactuar con el modelo.

### UML
[![UML back mentiunq](https://i.imgur.com/l1QTkYF.png)]