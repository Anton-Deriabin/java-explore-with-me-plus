# Используем базовый образ с Java 21
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный JAR-файл из target/ в контейнер
COPY target/main-service-0.0.1-SNAPSHOT.jar app.jar

# Указываем порт, который будет открыт (должен совпадать с портом в docker-compose.yml)
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]