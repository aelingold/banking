# banking
Para correr la app, ejecutar la clase BankingApplication. Levanta en el puerto 8080. Por default tiene precargadas 4 cuentas bancarias.

La bitacora guarda un archivo transactionlog.txt en la carpeta /tmp que es configurable por properties.

La clase TransactionTaxServiceTest tiene el Unit Test solicitado.

Para acceder a la API se puede invocar a los siguientes servicios:
- http://localhost:8080/api/accounts (GET)
- http://localhost:8080/api/transaction (POST)
JSON: {"originAccountId":"S1","destinationAccountId":"S2","transferAmount":1.0}

La clase TransactionController recibe el request y se encarga de encolar de manera asincronica la solicitud invocando al metodo queue de TransactionQueueService. Esta clase, por otro lado tiene un thread pool de consumers que se encargan de procesar las transacciones de manera concurrente. El consumer esta representado por la clase TransactionConsumer. Esta toma un pedido e invoca al servicio TransactionService mediante el metodo process para ejecutar la operacion. Por temas de concurrencia, para este solucion, se utilizo un metodo synchronized. Por default, los datos se graban en una base de datos en memoria en H2.