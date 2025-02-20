## TestJava2020

- [Requisitos](#prueba)
- [Solucion](#solucion)

### Prueba
En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuación se muestra un ejemplo de la tabla con los campos relevantes:

**Table Prices**

| BRAND_ID | START_DATE | END_DATE |PRICE_LIST|PRODUCT_ID|PRIORITY|PRICE|CURR
|--|--|--|--|--|--|--|--|
|1|2020-06-14-00.00.00|2020-12-31-23.59.59|1|35455|0|35.50|EUR|
|1|2020-06-14-15.00.00|2020-06-14-18.30.00|2|35455|1|25.45|EUR|
|1|2020-06-15-00.00.00|2020-06-15-11.00.00|3|35455|1|30.50|EUR|
|1|2020-06-15-16.00.00|2020-12-31-23.59.59|4|35455|1|38.95|EUR|

**Campos:**
 
BRAND_ID: foreign key de la cadena del grupo (1 = ZARA).
START_DATE , END_DATE: rango de fechas en el que aplica el precio tarifa indicado.
PRICE_LIST: Identificador de la tarifa de precios aplicable.
PRODUCT_ID: Identificador código de producto.
PRIORITY: Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico).
PRICE: precio final de venta.
CURR: iso de la moneda.

**Se pide:**
Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:
 
Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
 
Se debe utilizar una base de datos en memoria (tipo h2) e inicializar con los datos del ejemplo, (se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere adecuado para los mismos).
              
Desarrollar unos test al endpoint rest que  validen las siguientes peticiones al servicio con los datos del ejemplo:
                                                                                       
-          Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
-          Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
-          Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
-          Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
-          Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)


## Solucion
Dada la naturaleza de la prueba, he decidido implementar la solución con un enfoque MVP (Minimum viable product)
He utilizado JDK 21 (LTS), Spring Boot 3.4.2  y Maven como build automation tool.

## Observaciones

 - **"BRAND_ID: foreign key de la cadena del grupo"**: aunque no se explicite, se supone que existe una tabla Brand, por lo que también se ha creado la Entity Brand y su correspondiente Repository BrandRepository
 
 - **"PRICE_LIST: Identificador de la tarifa de precios aplicable"**: el concepto de tarifa no se define en detalle, sin embargo en el ejemplo parece que se tiene en cuenta para obtener el «precio final a aplicar» que podría ser diferente del campo PRICE
Por esta razón, aunque no se requiere explícitamente, he creado la interfaz Rate y la clase RateStrategy que permiten, si se desea, aplicar una tarifa diferente al precio definido.
A efectos puramente ilustrativos, la prueba unitaria PriceDtoTests muestra el uso de una RateStrategy que aplica un descuento al precio original

- **"PRIORITY: Desambiguador de aplicación de precios."** : Para facilitar cualquier nueva lógica de «lookup», he decidido desvincular la lógica del «desambiguador» a una estrategia. Para ello creé la interfaz PriceLookupStrategy que por ahora tiene como única implementación PriceLookupPriorityStrategy que implementa la elección basada en el campo PRIORITY.
Esta elección también sigue el Single Responsibility Principle, facilitando también el proceso de pruebas  

- **REST Service**: Para implementar un servicio REST real, he utilizado OpenAPI (Swagger), que a través del sistema de 'annotations' permite exponer la especificación del servicio al endpoint: /*v3/api-docs* (por ejemplo *http://localhost:8080/v3/api-docs*)

- **Desarrollar unos test al endpoint rest**: En la prueba unitaria *'PriceControllerTest'*, utilicé **MockMvc** para poder enviar fácilmente consultas al servicio.
Además de las cinco pruebas propuestas, añadí otras pruebas para comprobar si una consulta no proporciona ningún resultado (*getPrice_NotMatchingQueryInputs_returns404*) y consultas con parámetros no válidos (*getPrice_InvalidQueryDate_returns400*, *getPrice_InvalidProductId_returns400*).

- **Base de datos en memoria (tipo h2) e inicializar con los datos del ejemplo**: 
Para simplificar la configuración de la base de datos con los datos del ejemplo, he implementado un ApplicationListener, que ante el evento *'onApplicationEvent'* invoca al método *'init'* de la clase *'DbConfig'*, que se encarga de crear el escenario descrito.
Se optó por esta solución ya que al utilizar las mismas clases de Repositorio que en el proyecto se requiere menos trabajo de mantenimiento y no es necesario realizar cambios en los scripts SQL en caso de cambios en las estructuras de la BD.


- **Docker**: También existe la posibilidad de utilizar Docker para aprovechar todas las ventajas de los «contenedores».
Para lanzar la aplicación a través de Docker, hay que seguir estos pasos:
   1. docker build -t springio/gs-spring-boot-docker .
   2. docker run -p 8080:8080 springio/gs-spring-boot-docker

De este modo, la aplicación se expondrá en el puerto 8080 del localhost

**Ejemplo de llamadas REST al servicio**

http://localhost:8080/price/get-price/brandid/1/productid/35455/querydate/20200614-10.00
