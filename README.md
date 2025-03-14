# Notes Application

## Содержание

1. [Описание проекта](#1-описание-проекта)
2. [Основные возможности](#2-основные-возможности)
3. [Стек технологий](#3-стек-технологий)
4. [Установка и запуск](#4-установка-и-запуск)
5. [Структура проекта](#5-структура-проекта)
6. [Описание моделей](#6-описание-моделей)
7. [Взаимодействие с UI](#7-взаимодействие-с-ui)
8. [Тестирование](#8-тестирование)
9. [Логирование и мониторинг](#9-логирование-и-мониторинг)
10. [Дополнительная информация](#10-дополнительная-информация)
11. [Контакты](#11-контакты)

## 1. Описание проекта
Notes Application — веб-приложение для создания, хранения и управления текстовыми заметками.
Приложение разработано с использованием Spring Boot, Spring Data JPA, Thymeleaf и Hibernate.
Данные хранятся в базе данных H2 (по умолчанию), но могут быть легко перенесены в другую СУБД.

## 2. Основные возможности
- Создание, редактирование и удаление заметок
- Автоматическая запись времени создания и последнего обновления заметки
- Фильтрация заметок по содержимому (поиск)
- Создание первой заметки при запуске приложения
- Форматирование текста (жирный, курсив, подчеркивание, заголовки, списки и ссылки)
- Выбор шрифта и размера текста
- Добавление изображений в заметки

## 3. Стек технологий
- **Java 17**
- **Spring Boot 3.3.9 (Web, Data JPA, Thymeleaf, Lombok)**
- **Hibernate** (с аннотациями @CreationTimestamp и @UpdateTimestamp)
- **H2 Database** (по умолчанию)
- **Bootstrap 5** (для UI)
- **Quill.js** (WYSIWYG-редактор для форматирования текста и вставки изображений)

## 4. Установка и запуск
### Клонирование репозитория
```sh
git clone https://github.com/your-repository/notes-app.git
cd notes-app
```

### Сборка и запуск
- Используйте Maven для сборки проекта:
```sh
./mvnw spring-boot:run
```
- или (если Maven установлен в системе):
```sh
mvn spring-boot:run
```
### После запуска приложение будет доступно по адресу:
http://localhost:8080/notes

## 5. Структура проекта
```
src/main/java/com/example/notes
├── annotation      # Аннотация для логирования выбранных методов
│   ├── Loggable.java
├── aspects         # АОП-логирование
│   ├── WebServiceLogger.java
├── controller      # Контроллеры Spring MVC
│   ├── NoteController.java
├── model           # Модель данных
│   ├── Note.java
├── repository      # Репозиторий JPA
│   ├── NoteRepository.java
├── service         # Логика работы с заметками (если потребуется расширение)
│   ├── NoteService.java
└── Application.java  # Точка входа в приложение
```

## 6. Описание моделей
Note.java
Сущность, представляющая заметку:
- id (Long) — идентификатор (последовательная генерация значений)
- text (String) — содержимое заметки (HTML)
- createdAt (Timestamp) — время создания
- updatedAt (Timestamp) — время последнего обновления

## 7. Взаимодействие с UI
### Список заметок (index.html) 
- Отображаются все заметки
- Поиск заметок по содержимому
- Создание новой заметки
- Отображается только первая строка текста
- Отображается время сохранения заметки
- Кнопки "Редактировать" и "Удалить"

### Форма редактирования (edit.html)
- Используется WYSIWYG-редактор (Quill.js)
- Позволяет форматировать текст и вставлять изображения, ссылки

## 8. Тестирование
### Mock-тестирование:
Mockito применяется для подмены зависимостей в сервисном слое, позволяя изолировать бизнес-логику.

### Запуск тестов:
```sh
mvn clean test
```

## 9. Логирование и мониторинг
### Аспектное логирование:
Аспект WebServiceLogger (в пакете com.example.notes.aspects) перехватывает вызовы методов сервиса, помеченных аннотацией @Loggable, и логирует имя метода, параметры и возвращаемое значение.

### Настройка логгирования:
Используются **SLF4J** с Logback для удобного и конфигурируемого вывода логов.

## 10. Дополнительная информация
- Проект разработан с учетом лучших практик архитектуры MVC.
- Используется H2 в качестве in-memory базы данных (для разработки), но можно переключиться на PostgreSQL/MySQL, изменив настройки в application.properties.
- В index.html и edit.html загружаются ресурсы (Bootstrap и Quill.js) из CDN.

### Переключение базы данных
Пример для PostgreSQL:
```sh
spring.datasource.url=jdbc:postgresql://localhost:5432/notes_db
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 11. Контакты
- **Автор проекта:** Колодкин Александр
- **Telegram:** @kolobook146
