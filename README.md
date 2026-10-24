# studious-disco

Learn Java, Spring Boot and Kubernetes by building one small app: a **Task Tracker** web service.

## What you need

- WSL2 Ubuntu
- Java 21 and Maven:

```bash
sudo apt update
sudo apt install -y openjdk-21-jdk maven
```

Later steps also need Podman, kubectl and minikube. Each lesson tells you when.

## The steps

| Step | Topic | Lesson | Status |
|------|-------|--------|--------|
| 1 | Java basics | [lessons/01-java-basics.md](lessons/01-java-basics.md) | Done |
| 2 | First Spring Boot app | [lessons/02-first-spring-boot.md](lessons/02-first-spring-boot.md) | Done |
| 3 | REST API for tasks | [lessons/03-rest-api.md](lessons/03-rest-api.md) | Done |
| 4 | Database (PostgreSQL + Spring Data) | | Next |
| 5 | Tests (JUnit) | | |
| 6 | Containers with Podman | | |
| 7 | Kubernetes with minikube (Podman driver) | | |
| 8 | Config, secrets, health checks, scaling | | |

## Project layout

```
task-tracker/                  the Spring Boot app (Maven project)
  pom.xml                      libraries and build settings
  src/main/java/...            Java code
  src/main/resources/          settings (application.properties)
lessons/                       one short lesson per step
```
