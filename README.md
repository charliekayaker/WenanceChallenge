# WenanceChallenge
Microservicios sobre la cotización de monedas digitales primeras en el ranking

##### Para poder utilizar este sistema usted debe copiar el proyecto, por ejemplo , {system.user}/Desktop/ luego hacer "cd  {system.user}/Desktop/DigitalCurrencies", ejecutar el comando "mvn clean install" 
##### En DigitalCurrencies/target obtendrá DigitalCurrencies.jar el cual puede copiar en otro lugar o allí mismo ejecutar java -jar DigitalCurrencies.jar, también puede ejecutarlo con $ docker run.


##### Necesitarás también tener una base de datos MongoDB escuchando en el puerto 27017 y asegurarte que el puerto 9800 está disponible para que tomcat escuche. De todas maneras puedes modificar el archivo
##### "application.properties" para ajustar la configuración que más se adecúe a tus necesidades.

##### [Obtener estadísticas](http://localhost:9800/api/getStatistics/01-09-2021.10:46:40/to/07-09-2021.18:48:40/BTC)

##### [Obtener datos guardados paginados](http://localhost:9800/api/getPrices/10?currencie=ETH&from=03-09-2021.00:26:00&to=06-09-2021.15:00:00)

![Alt text](src/main/resources/postConvertToUsd.PNG?raw=true "Test SOAP-UI")

![Alt text](src/main/resources/testPostman.PNG?raw=true "Test Postman")

![Alt text](src/main/resources/test2.PNG?raw=true "SOAP-UI 2")

![Alt text](src/main/resources/test3.PNG?raw=true "SOAP-UI 3")

![Alt text](src/main/resources/test4.PNG?raw=true "SOAP-UI 4")