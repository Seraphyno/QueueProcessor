## QueueProcessor

A very simple Kafka application that consumes messages from a Kafka topic and produces a feedback message to another topic.

### Technical requirements:

- Java 17 or later
- Maven 3.6 or later
- Spring Boot 3.4.6 or later
- Docker (for running dependencies like Kafka)

### Configuration

Kafka topics and other properties are externalized in `application.yml` for flexibility and environment-specific overrides.

### Building and running

- Package the application by running `mvn clean package`
- Run the `build-image.sh` script in the `docker` folder

Run the configuration by using the docker compose file in the project root, which starts the application
along Kafka, Zookeeper and Kafka UI (an open-source web-based tool for managing and monitoring Apache Kafka clusters).
