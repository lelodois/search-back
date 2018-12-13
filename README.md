## Serviço Rest client que consome o Solr

### Build docker
- mvn install
- docker build -t back-docker .
- docker run --net searchnet --ip 172.18.0.30 -d -p 8981:8981 back-docker
https://hub.docker.com/r/lelodois/back-docker/

### Tecnologias

- Java 8 (rest)
- Maven
- Spring boot
- Oauth

Autor
Leo costa - Initial work 
