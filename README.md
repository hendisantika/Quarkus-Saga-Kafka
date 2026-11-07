# Quarkus-Saga-Kafka

[![Java CI with Maven](https://github.com/hendisantika/Quarkus-Saga-Kafka/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/Quarkus-Saga-Kafka/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Quarkus](https://img.shields.io/badge/Quarkus-3.40-4695EB?logo=quarkus)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-messaging-231F20?logo=apachekafka)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

Implementing the Saga pattern with Quarkus and Kafka.

### Project description

🚀 This project builds a microservices application that applies the Saga pattern to a seat-reservation flow, using
Quarkus and Kafka throughout. Each service has its own in-memory H2 database and talks to the other services only
through Kafka topics.

## Tech Stack

| Component          | Version / Tool                                  |
|--------------------|-------------------------------------------------|
| Java               | **JDK 25** (`maven.compiler.release=25`)        |
| Framework          | Quarkus 3.40.x                                  |
| Messaging          | Apache Kafka via `quarkus-messaging-kafka`      |
| Persistence        | Hibernate ORM with Panache + H2 (in-memory)     |
| REST               | RESTEasy + Jackson                              |
| Boilerplate        | Lombok (registered as an annotation processor)  |
| Build              | Maven 3.9+                                      |
| CI                 | GitHub Actions (JDK 25 Temurin, per-service matrix) |
| Dependency updates | Dependabot (grouped Maven & GitHub Actions)     |

## Services

| Service            | Port | Responsibility                                       |
|--------------------|------|------------------------------------------------------|
| `order-service`    | 8090 | Receives seat orders and tracks the seat status      |
| `payment-service`  | 8080 | Charges the user and triggers the next step or a compensation |
| `allocate-service` | 8085 | Allocates the seat or asks payment to compensate     |

### Saga flow (Kafka topics)

```
order-service ──(seats)──▶ payment-service ──(payment)──▶ allocate-service
      ▲                          │   ▲                          │
      │                          │   └──────(payment-update)────┤  compensation
      └──────────(status-update)─┴──────────────────────────────┘
```

1. `POST /seats` on **order-service** reserves a seat and publishes to `seats`.
2. **payment-service** consumes `seats`, makes the payment and publishes to `payment`. If the payment fails, it
   publishes the failure to `status-update`.
3. **allocate-service** consumes `payment`, allocates the seat and publishes the result to `status-update`. If the
   allocation fails, it publishes to `payment-update` so that payment-service can reverse the payment (compensation).
4. **order-service** consumes `status-update` and updates the seat status.

### REST endpoints

| Service          | Method | Path              | Description                |
|------------------|--------|-------------------|----------------------------|
| order-service    | POST   | `/seats`          | Create a new seat order    |
| order-service    | GET    | `/seats/{id}`     | Get the seat status        |
| allocate-service | GET    | `/allocates/{id}` | Allocate a seat by id      |

## Getting Started

### Prerequisites

- JDK 25 (e.g. [Eclipse Temurin](https://adoptium.net/))
- Maven 3.9+
- Docker & Docker Compose

### 1. Start Kafka

```bash
docker compose up -d
```

As an alternative, you can run the all-in-one Landoop image:

```bash
docker run --rm -p 2181:2181 -p 3030:3030 -p 8081-8083:8081-8083 -p 9581-9585:9581-9585 -p 9092:9092 -e ADV_HOST=localhost landoop/fast-data-dev:latest
```

### 2. Build & test

```bash
mvn -B verify -f order-service/pom.xml
mvn -B verify -f payment-service/pom.xml
mvn -B verify -f allocate-service/pom.xml
```

### 3. Run in dev mode

Run each service in its own terminal:

```bash
cd order-service    && mvn quarkus:dev
cd payment-service  && mvn quarkus:dev
cd allocate-service && mvn quarkus:dev
```

### 4. Try it

```bash
curl -X POST http://localhost:8090/seats \
  -H "Content-Type: application/json" \
  -d '{"id": 1}'

curl http://localhost:8090/seats/1
```

## Continuous Integration & Dependabot

- **CI** (`.github/workflows/maven.yml`): builds and tests each service on **JDK 25** in a matrix job. On pushes to
  `main` it also submits the Maven dependency graph to GitHub to improve Dependabot alerts.
- **Dependabot** (`.github/dependabot.yml`):
  - Maven: daily checks across all three services, with grouped PRs for Quarkus, Maven plugins, Lombok, and other
    minor/patch updates. Quarkus major upgrades are ignored and done by hand.
  - GitHub Actions: weekly grouped updates.
  - Commits use conventional prefixes (`chore(deps)`, `ci(deps)`) and get the `dependencies` label.

### Quarkus

Quarkus is a Kubernetes-native Java framework built for Java Virtual Machines (JVMs) and native compilation. It
optimizes Java for containers, which makes it a good platform for serverless, cloud, and Kubernetes environments.

### Features of Quarkus

- Container first (fast startup, low memory use, and small images or containers)
- Low memory consumption
- Fast startup

### Saga

Saga is a pattern from 1987, first proposed as an alternative to long-running database transactions in SQL databases.
A modern variation of the pattern also works very well for distributed transactions. A saga is a sequence of local
transactions. Each transaction updates the data store of a single microservice and then publishes an event or message.
An external request (an event or action) starts the first transaction in the saga. When a local transaction finishes
(the data is stored and the message or event is published), that message or event triggers the next local transaction
in the saga.

### Choreography or Orchestration

Choreography: decentralised coordination. Each microservice produces events or messages, listens to other
microservices' events or messages, and decides whether to act.

Orchestration: centralised coordination. An orchestrator tells the participating microservices which local transaction
to run.

### Pros

- Provides consistency through transactions in a highly scalable, loosely coupled, event-driven microservice
  architecture.
- Provides consistency through transactions in a microservice architecture that uses NoSQL databases without 2PC
  support.

### Cons

- You need to handle transient failures and provide idempotency.
- It is hard to debug, and the complexity grows as the number of microservices increases.

### Situations to use SAGA

- Highly scalable, loosely coupled microservice architectures that use event sourcing.
- Systems that use distributed NoSQL databases.

### Situations not to use SAGA

- Low-scale transactional systems that use SQL databases.
- Systems with cyclic dependencies between services.

## License

This project is licensed under the [Apache License 2.0](LICENSE).
