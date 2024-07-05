# Java Web Application

## Описание

Это Java веб-приложение, использующее PostgreSQL в качестве базы данных. Для тестирования используется TestContainers, а деплой осуществляется на сервер Apache Tomcat.

## Требования

- Java 17+
- Maven 3.6+
- Docker (для TestContainers)
- Apache Tomcat 9.x
- PostgreSQL

## Настройка базы данных

### Локальная разработка с TestContainers

TestContainers автоматически настроит и запустит контейнер с PostgreSQL для интеграционных тестов. Никакой дополнительной настройки не требуется. Необходим только работающий docker на локальной машине.

### Подключение к реальной базе данных

Для подключения к реальной базе данных PostgreSQL создайте файл database.properties в папке src/main/resources и укажите следующие параметры:

```properties
db.url=jdbc:postgresql://<your-database-host>:<port>/<database-name>
db.user=<your-database-username>
db.password=<your-database-password>
```

## Готовый тестовый сервис

#### URL для теста http://185.65.200.46:8085/aston_hw2/api


## Resources

### Authors

- Endpoint: /author
    - GET: Retrieve all authors
    - POST: Create a new author
        - Fields:
            - name: String (required)
            - bio: String (optional, max length 2048)

- Endpoint: /author/{id}
    - GET: Retrieve author by ID
    - PUT: Update author by ID
        - Fields:
            - name: String (optional)
            - bio: String (optional, max length 2048)
    - DELETE: Delete author by ID

- Endpoint: /author/{id}/book
    - GET: Retrieve all books of the author by ID
    - POST: Add a new book with the specified author
        - Fields:
            - title: String (required)
            - description: String (optional, max length 1024)
            - publishedDate: String (required, format: yyyy-MM-dd)
            - isbn: String (required)
            - genres: List<Integer> (required, at least 1 genre ID)

### Books

- Endpoint: /book
    - GET: Retrieve all books (optional filters: authorId, genreId)
    - POST: Create a new book
        - Fields:
            - title: String (required)
            - description: String (optional, max length 1024)
            - publishedDate: String (required, format: yyyy-MM-dd)
            - isbn: String (required)
            - authorId: Integer (required, existing author ID)
            - genres: List<Integer> (required, at least 1 existing genre ID)

- Endpoint: /book/{id}
    - GET: Retrieve book by ID
    - PUT: Update book by ID
        - Fields:
            - title: String (optional)
            - description: String (optional, max length 1024)
            - publishedDate: String (optional, format: yyyy-MM-dd)
            - isbn: String (optional)
            - authorId: Integer (optional, existing author ID)
            - genres: List<Integer> (optional, existing genre IDs)
    - DELETE: Delete book by ID

### Genres

- Endpoint: /genre
    - GET: Retrieve all genres
    - POST: Create a new genre
        - Fields:
            - name: String (required)
            - description: String (optional, max length 1024)

- Endpoint: /genre/{id}
    - GET: Retrieve genre by ID
    - PUT: Update genre by ID
        - Fields:
            - name: String (optional)
            - description: String (optional, max length 1024)
    - DELETE: Delete genre by ID

    
## Usage

### Example Requests

```json
POST /author
{
  "name": "John Doe",
  "bio": "An accomplished author known for his works in fiction."
}

PUT /author
{
  "id": "1",
  "name": "updated name",
  "bio": "updated bio"
}

POST /author/{id}/book
{
  "title": "Руслан и Людмила",
  "description": "Эпическая поэма, основанная на народных русских сказках, повествует о похищении невесты Руслана - Людмилы, дочери князя Владимира."
  "publishedDate": "1820",
  "isbn": "978-0486293356",
  "genreIds": ["1","2","3"...]  
}

POST /book
{
  "title": "Руслан и Людмила",
  "description": "Эпическая поэма, основанная на народных русских сказках, повествует о похищении невесты Руслана - Людмилы, дочери князя Владимира."
  "publishedDate": "1820",
  "isbn": "978-0486293356",
  "authorId": "1",
  "genreIds": ["1","2","3"...]  
}

PUT /book
{
  "id": "5",
  "title": "updated title",
  "description": "updated dedcription"
  "publishedDate": "updated published date",
  "isbn": "updated isbn",
  "authorId": "updated authorId",
  "genreIds": ["updated genres"]  
}

POST /genre
{
  "name": "Фантастика",
  "description": "Фантастика – это любая история, в которой автор нарушил законы реального мира. Например, предположил существование невероятных технологий, удивительных планет или незнакомых форм жизни. Фантастика появилась во времена больших открытий."
}

PUT /genre
{
  "id": "7"
  "name": "updated name",
  "description": "updated description"
}

