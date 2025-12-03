# 🏴‍☠️ Pirate's Movie Treasure Chest - Spring Boot Demo Application

Ahoy matey! Welcome to the finest movie catalog web application on the seven seas! Built with Spring Boot and featuring a swashbuckling pirate theme, this application demonstrates Java development best practices while providing a treasure trove of movie information.

## ⚓ Features

- **🎬 Movie Catalog**: Browse 12 classic movies with detailed information, ye landlubber!
- **🔍 Advanced Movie Search**: Search for treasure (movies) by name, ID, or genre like a true pirate navigator
- **📋 Movie Details**: View comprehensive information including captain (director), year sailed, adventure type (genre), journey length (duration), and description
- **⭐ Customer Reviews**: Each movie includes authentic customer reviews with ratings and avatars
- **📱 Responsive Design**: Mobile-first design that works on all devices, from ship to shore
- **🎨 Modern Pirate UI**: Dark theme with gradient backgrounds, smooth animations, and pirate-themed styling
- **🚀 REST API**: JSON endpoints for programmatic access to the movie treasure chest

## 🛠️ Technology Stack

- **☕ Java 8** - The foundation of our ship
- **🌱 Spring Boot 2.7.18** - Our trusty sailing framework
- **📦 Maven** - For managing our treasure dependencies
- **📝 Log4j 2** - For keeping a ship's log
- **🧪 JUnit 5.8.2** - For testing our sea-worthiness
- **🎭 Thymeleaf** - For crafting beautiful HTML templates

## 🚀 Quick Start

### Prerequisites

- Java 8 or higher (ye need a proper compass!)
- Maven 3.6+ (for managing yer treasure)

### Run the Application

```bash
git clone https://github.com/<youruser>/sample-qdev-movies.git
cd sample-qdev-movies
mvn spring-boot:run
```

The application will start on `http://localhost:8080` - ready to set sail!

### Access the Application

- **🏴‍☠️ Movie Treasure Chest**: http://localhost:8080/movies
- **🗺️ Movie Details**: http://localhost:8080/movies/{id}/details (where {id} is 1-12)
- **🔍 Search Movies**: Use the search form on the main page or API endpoints

## 🔍 Movie Search Features

### Web Interface Search
Navigate to `/movies` and use the search form to find yer treasure:

- **Movie Name**: Search by partial movie name (case-insensitive)
- **Movie ID**: Find a specific movie by its unique ID
- **Genre**: Filter movies by adventure type (genre)

**Example Searches:**
- Search for "Prison" to find "The Prison Escape"
- Search by ID "2" to find "The Family Boss"
- Search by genre "Drama" to find all dramatic adventures

### REST API Search
For ye programmatic pirates, use the JSON API:

```bash
# Search by movie name
curl "http://localhost:8080/movies/search?name=Prison"

# Search by ID
curl "http://localhost:8080/movies/search?id=2"

# Search by genre
curl "http://localhost:8080/movies/search?genre=Drama"

# Combine multiple criteria
curl "http://localhost:8080/movies/search?name=Family&genre=Crime"
```

## 🏗️ Building for Production

```bash
mvn clean package
java -jar target/sample-qdev-movies-0.1.0.jar
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/amazonaws/samples/qdevmovies/
│   │       ├── movies/
│   │       │   ├── MoviesApplication.java    # Main Spring Boot application
│   │       │   ├── MoviesController.java     # REST controller with search endpoints
│   │       │   ├── MovieService.java         # Business logic with search functionality
│   │       │   ├── Movie.java                # Movie data model
│   │       │   ├── Review.java               # Review data model
│   │       │   └── ReviewService.java        # Review business logic
│   │       └── utils/
│   │           ├── MovieIconUtils.java       # Movie icon utilities
│   │           └── MovieUtils.java           # Movie validation utilities
│   └── resources/
│       ├── application.yml                   # Application configuration
│       ├── movies.json                       # Movie treasure data
│       ├── mock-reviews.json                 # Mock review data
│       ├── log4j2.xml                        # Logging configuration
│       ├── templates/
│       │   ├── movies.html                   # Main movie list with search form
│       │   └── movie-details.html            # Movie details page
│       └── static/css/
│           ├── movies.css                    # Pirate-themed styling
│           └── movie-details.css             # Detail page styling
└── test/                                     # Comprehensive unit tests
    └── java/
        └── com/amazonaws/samples/qdevmovies/movies/
            ├── MovieServiceTest.java         # Service layer tests
            ├── MoviesControllerTest.java     # Controller tests
            └── MovieTest.java                # Model tests
```

## 🗺️ API Endpoints

### Get All Movies (with optional search)
```
GET /movies?name={name}&id={id}&genre={genre}
```
Returns an HTML page displaying movies with optional search filtering.

**Query Parameters:**
- `name` (optional): Movie name to search for (partial match, case-insensitive)
- `id` (optional): Specific movie ID to find
- `genre` (optional): Genre to filter by (partial match, case-insensitive)

**Examples:**
```
http://localhost:8080/movies
http://localhost:8080/movies?name=Prison
http://localhost:8080/movies?genre=Drama
http://localhost:8080/movies?name=Family&genre=Crime
```

### Search Movies API (JSON Response)
```
GET /movies/search?name={name}&id={id}&genre={genre}
```
Returns a JSON response with search results and pirate-themed messages.

**Query Parameters:**
- `name` (optional): Movie name to search for
- `id` (optional): Specific movie ID to find  
- `genre` (optional): Genre to filter by
- **Note**: At least one parameter must be provided

**Response Format:**
```json
{
  "success": true,
  "movies": [...],
  "totalResults": 2,
  "message": "Found 2 movies in the treasure chest, matey!",
  "pirateCheer": "Shiver me timbers! We found some fine films! 🎬",
  "searchCriteria": {
    "name": "Prison",
    "id": "",
    "genre": ""
  }
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Arrr! Ye need to provide at least one search parameter, matey! (name, id, or genre)",
  "pirateWisdom": "A pirate without a map be lost at sea! 🏴‍☠️"
}
```

### Get Movie Details
```
GET /movies/{id}/details
```
Returns an HTML page with detailed movie information and customer reviews.

**Parameters:**
- `id` (path parameter): Movie ID (1-12)

**Example:**
```
http://localhost:8080/movies/1/details
```

## 🧪 Testing

Run the comprehensive test suite:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=MovieServiceTest

# Run with coverage
mvn test jacoco:report
```

**Test Coverage:**
- **MovieServiceTest**: Tests all search functionality, edge cases, and data loading
- **MoviesControllerTest**: Tests web endpoints, API responses, and error handling
- **MovieTest**: Tests the Movie model

## 🔧 Troubleshooting

### Port 8080 already in use

Run on a different port:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Build failures

Clean and rebuild:
```bash
mvn clean compile
```

### Search not working

Check that:
1. At least one search parameter is provided for API calls
2. Movie data is loaded correctly (check logs for JSON loading errors)
3. Search terms are not empty or whitespace-only

## 🎯 Usage Examples

### Web Interface Examples

1. **Find all Drama movies:**
   - Navigate to http://localhost:8080/movies
   - Select "Drama" from the Genre dropdown
   - Click "⚓ Search the Seas!"

2. **Search for movies with "Hero" in the name:**
   - Enter "Hero" in the Movie Name field
   - Click search to find "The Masked Hero"

3. **Find a specific movie by ID:**
   - Enter "5" in the Movie ID field
   - Click search to find "Life Journey"

### API Examples

```bash
# Find all Sci-Fi movies
curl "http://localhost:8080/movies/search?genre=Sci-Fi"

# Search for movies with "War" in the name
curl "http://localhost:8080/movies/search?name=War"

# Get movie with ID 10
curl "http://localhost:8080/movies/search?id=10"

# Complex search: Crime movies with "Boss" in the name
curl "http://localhost:8080/movies/search?name=Boss&genre=Crime"
```

## 🤝 Contributing

This project welcomes contributions from fellow pirates! Feel free to:
- Add more movies to the treasure chest
- Enhance the pirate-themed UI/UX
- Add new search features (by director, year, rating)
- Improve the responsive design
- Add more pirate language and themes
- Enhance the API with additional endpoints

## 📜 License

This sample code is licensed under the MIT-0 License. See the LICENSE file.

---

⚓ *May fair winds fill yer sails and great movies fill yer evenings!* ⚓
