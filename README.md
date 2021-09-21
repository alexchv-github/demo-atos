Escribire este archivo en Espanol ya que no especificasteis nada en el documento.


He creado este proyecto intentado seguir al 100% vuestros requisitos, que son:
    - Principios SOLID
    - ATDD, en esto no estoy seguro ya que ATDD es una metodologia de equipo.
    - El microservicio podra montarse con docker, dejare instrucciones mas abajo.
   
Nota : Sobre los test, he hecho simplemente los test para la llamada que comprueba el estado de la transaccion,
        ya que en los requisitos comentabais que los requerimientos eran simples, en caso de estar equivocado y 
        esperar los test para las otras llamadas no tengo ningún problema en hacerlos y enviaros la prueba de nuevo.
        

He utilizado JPA y m2 para almacenar todo, dejare ejemplos curl que he usado para que podais anadirlos en postman en
caso de que querais utilizarlos.

He utilizado: Spring boot, mapstruct, maven, jpa, m2, junit, hamcrest 
Nota: Seguramente en caso de repetir la prueba utilizaria swagger tambien para generar las llamadas.


Create Transaction:
    He seguido lo requisitos que anadisteis, he creado una tabla extra llamada ACCOUNTS para poder comprobar el saldo.
    He guardado todas las nuevas transaccion en estado PENDING.
    
    curl --location --request POST 'http://localhost:8080/transactions' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "reference": "",
        "iban": "ES9820385778983000760236",
        "date": "2019-07-16T16:55:42.000Z",
        "amount": 500,
        "fee": 3.18,
        "description": "Restaurant payment"
    }'
    
Search Transaction:
    En el documento senalabais que deberia poder ser filtrable por iban y tener las posibilidad de ordenarlo,
    en el api que he creado el iban es obligatorio para recuperar las transacciones y se puede ordenar escribiendo ASC o DESC,
    se ordenara por fecha.

    curl --location --request GET 'http://localhost:8080/transactions/ES9820385778983000760236/DESC' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "reference": "12345A",
        "iban": "",
        "date": "2019-07-16T16:55:42.000Z",
        "amount": -155.38,
        "fee": 3.18,
        "description": "Restaurant payment"
    }'

Transaction Status:
    Al haber solo reglas de negocio para esta llamada entendi que solo tenia que trabajar en TDD para esta llamada, en caso de estar 
    equivocado como he comentado antes avisarme y lo rehare.
    No se que esperabais por mi parte en el campo Status, yo entiendo que deberia ser otro servicio o batch quien cambiara el status.
    
    curl --location --request GET 'http://localhost:8080/transactions/status/ATM/12345'




Contenedor docker instrucciones:
    - ejecutar el comando package en maven
    - estar dentro de la carpeta del projecto transactionService en la consola de comandos.
    - montar imagen "docker build -t transaction-app ." <---- punto incluido
    - arrancar contenedor "docker run -d -p 8080:8080 -t transaction-app"
    - Ya deberíais estar listos para consumir los endpoint.
