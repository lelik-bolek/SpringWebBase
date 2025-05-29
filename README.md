# Учебный проект: REST API для управления Person и Address

## Описание проекта

Это учебное Spring Boot приложение, демонстрирующее реализацию REST API для работы с сущностями Person (Люди) и Address (Адреса). Проект включает полный набор CRUD-операций и примеры связей между сущностями.

## Технологии

- **Spring Boot** (Web, Data JPA)
- **Hibernate ORM**
- **H2 Database** (встроенная база данных)
- **REST API**

## Сущности

### Person
- Хранит информацию о людях (имя, фамилия)
- Связан с Address отношением Many-to-One

### Address
- Хранит адресные данные (город, улица)
- Связан с Person отношением One-to-Many

## API Endpoints

### PersonController (`/persons`)

| Метод | Путь | Описание | HTTP Status |
|-------|------|----------|-------------|
| GET | `/persons` | Получить всех пользователей | 200 OK |
| GET | `/persons/{id}` | Получить пользователя по ID | 200 OK или 404 Not Found |
| GET | `/persons/addresses` | Получить всех пользователей с адресами | 200 OK |
| POST | `/persons` | Создать нового пользователя | 201 Created |
| PUT | `/persons/{id}` | Полное обновление пользователя | 200 OK |
| PATCH | `/persons/{id}` | Частичное обновление пользователя | 200 OK |
| DELETE | `/persons/{id}` | Удалить пользователя | 204 No Content |

### AddressController (`/addresses`)

| Метод | Путь | Описание | HTTP Status |
|-------|------|----------|-------------|
| GET | `/addresses` | Получить все адреса | 200 OK |
| GET | `/addresses/{id}` | Получить адрес по ID | 200 OK или 404 Not Found |
| POST | `/addresses` | Создать новый адрес | 201 Created |
| PUT | `/addresses/{id}` | Полное обновление адреса | 200 OK |
| DELETE | `/addresses/{id}` | Удалить адрес | 204 No Content |

## Тестирование API

Для тестирования API можно использовать:

1. **Talend API Tester** (бесплатное расширение для Chrome):
   - Установка: https://chromewebstore.google.com/detail/talend-api-tester-free-ed/aejoelaoggembcahagimdiliamlcdmfm?hl=ru
   - Примеры запросов приведены в документации к контроллерам

2. **Postman** или аналогичные инструменты

## Задание 1

1. На основе примера `PersonController` реализуйте собственный контроллер для другой сущности
2. Протестируйте все endpoints контроллера
3. Реализуйте `AddressController` с полным набором CRUD-операций
4. Протестируйте связи между Person и Address

## Задание 2: Обработка исключений в Spring Boot

1. Создан класс исключения `PersonNotFoundException
2. Обработка исключения в контроллере `PersonController`
3. Перенос обработчика в глобальный обработчик `GlobalHandlers`
4. Создан класс `ErrorResponse`
5. Модификация глобального обработчика
*Результат:*
   * При обращении к несуществующему `id` в `/persons/{id}` клиент получает понятный JSON-ответ с HTTP-статусом 404:

     ```json
     {
		"message": "Пользователь с id не найден: 3",
		"code": 404,
		"details": "Пользователь с указанным идентификатором не был найден в системе."
	}
     ```
## Задание 3: Интеграция Swagger UI и аннотаций OpenAPI в Spring-приложение

1. **Добавлена зависимость `springdoc-openapi-starter-webmvc-ui` в `pom.xml`:**

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

3. **Добавлены аннотации OpenAPI в контроллер `PersonController` и `AddressController`:**

   * `@OpenAPIDefinition` — общая информация об API.
   * `@Tag` — описание логической группы эндпоинтов.
   * `@Operation(summary = "...")` — краткие описания операций над ресурсами (методы контроллера).

**Открытие документации по адресу:**

`http://localhost:8080/swagger-ui/index.html#`

---

## Настройка базы данных

Приложение использует встроенную H2 Database. Настройки по умолчанию:

```properties
spring.datasource.url=jdbc:h2:file:./data/demo
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

Для доступа к консоли H2 добавьте в браузере:
```
http://localhost:8080/h2-console
```

## Запуск приложения

1. Клонируйте репозиторий
2. Запустите главный класс `SpringRestApp`
3. Приложение будет доступно на http://localhost:8080

При запуске автоматически создаются тестовые данные:
- 2 пользователя (Вася Иванов и Дима Петров)
- 2 адреса (Москва, Плющиха и Москва, Довженко)