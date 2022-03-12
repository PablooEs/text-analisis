# text-analisis

[Spring Boot](http://projects.spring.io/spring-boot/) REST API para analizar un texto ingresado. Separa el mismo en longitudes determinadas por el usuario y cuenta las occurencias de las divisiones. 
Permite crear, buscar textos por id, paginados por parametro de caracteres y eliminarlos.
Los textos son persistidos en una base de datos H2.


## Requerimientos

Para probar la aplicacion es necesario:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Ejecutar la aplicacion de manera local


Usando el [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) ejecutar en la raiz del proyecto:

```shell
mvn spring-boot:run
```


