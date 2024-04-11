# Demo SparkJava - backend


 📖 Dependências de build:

 * Distro OpenJDK 21 (Sugestão: Eclipse Temurin) 
 * Apache Maven 3.9+

## Executando o backend

 1. Gerenciar configurações no arquivo resources/application-dev.properties
 2. Iniciar a aplicação com: 

 * Java Jar:
```sh
java -jar  poc-1.0-SNAPSHOT.jar
```
## Cridando no systemd

sudo systemctl daemon-reload

sudo systemctl start javaapp.service
sudo systemctl status javaapp.service

Para iniciar automaticamente com o sistema use:
sudo systemctl enable javaapp.service


#### License MIT Copyright (c) 2023 Luiz Toni
