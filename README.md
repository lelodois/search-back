## Serviço Rest client que consome api do twitter para buscar hashtags
### (twitter-client-back)

### Build docker
- mvn install
- docker run --name my_solr -d -p 8983:8983 -t solr
- docker exec -it --user=solr my_solr bin/solr create_core -c userdata
- docker build -t twcli-back-docker .
- docker run --net twclientnet --ip 172.18.0.24 -d -p 8094:8094 {tag}

### Tecnologias

- Java 8 (rest)
- Maven
- Spring boot
- Swagger

Em desenvolvimento...

Autor
Leo costa - Initial work 
