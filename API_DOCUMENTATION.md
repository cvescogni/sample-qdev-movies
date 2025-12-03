# 🏴‍☠️ Pirate's Movie API Documentation

Ahoy, ye savvy developers! This be the complete API documentation for the Pirate's Movie Treasure Chest search functionality. Use these endpoints to programmatically search through our movie collection like a true digital pirate!

## 🗺️ Base URL

```
http://localhost:8080
```

## 🔍 Search Endpoints

### 1. HTML Movie Search (Web Interface)

**Endpoint:** `GET /movies`

**Description:** Returns an HTML page with movie results and search form. Perfect for web browsers, ye landlubber!

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `name` | String | No | Movie name to search for (partial match, case-insensitive) |
| `id` | Long | No | Specific movie ID to find (1-12) |
| `genre` | String | No | Genre to filter by (partial match, case-insensitive) |

**Response:** HTML page with search results

**Examples:**
```bash
# Get all movies
curl "http://localhost:8080/movies"

# Search by name
curl "http://localhost:8080/movies?name=Prison"

# Search by ID
curl "http://localhost:8080/movies?id=2"

# Search by genre
curl "http://localhost:8080/movies?genre=Drama"

# Multiple criteria
curl "http://localhost:8080/movies?name=Family&genre=Crime"
```

### 2. JSON Movie Search API

**Endpoint:** `GET /movies/search`

**Description:** Returns JSON response with search results and pirate-themed messages. Perfect for API integration, ye scurvy dog!

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `name` | String | No* | Movie name to search for (partial match, case-insensitive) |
| `id` | Long | No* | Specific movie ID to find (1-12) |
| `genre` | String | No* | Genre to filter by (partial match, case-insensitive) |

*At least one parameter must be provided, or ye'll get a 400 error!*

**Response Format:**

**Success Response (200 OK):**
```json
{
  "success": true,
  "movies": [
    {
      "id": 1,
      "movieName": "The Prison Escape",
      "director": "John Director",
      "year": 1994,
      "genre": "Drama",
      "description": "Two imprisoned men bond over a number of years...",
      "duration": 142,
      "imdbRating": 5.0,
      "icon": "🎬"
    }
  ],
  "totalResults": 1,
  "message": "Found 1 movies in the treasure chest, matey!",
  "pirateCheer": "Shiver me timbers! We found some fine films! 🎬",
  "searchCriteria": {
    "name": "Prison",
    "id": "",
    "genre": ""
  }
}
```

**No Results Response (200 OK):**
```json
{
  "success": true,
  "movies": [],
  "totalResults": 0,
  "message": "No movies found matching yer search criteria, ye landlubber!",
  "pirateAdvice": "Try broadening yer search or check the spelling, arrr!",
  "searchCriteria": {
    "name": "NonExistentMovie",
    "id": "",
    "genre": ""
  }
}
```

**Error Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Arrr! Ye need to provide at least one search parameter, matey! (name, id, or genre)",
  "pirateWisdom": "A pirate without a map be lost at sea! 🏴‍☠️"
}
```

**Server Error Response (500 Internal Server Error):**
```json
{
  "success": false,
  "message": "Arrr! Something went wrong during the search, ye scurvy dog!",
  "error": "Detailed error message",
  "pirateApology": "Even the best pirates face storms at sea! Try again, matey! ⚓"
}
```

## 📋 Movie Details Endpoint

**Endpoint:** `GET /movies/{id}/details`

**Description:** Returns detailed HTML page for a specific movie with reviews.

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | Long | Yes | Movie ID (1-12) |

**Response:** HTML page with movie details

**Example:**
```bash
curl "http://localhost:8080/movies/1/details"
```

## 🎯 Search Examples

### By Movie Name
```bash
# Find movies with "Hero" in the name
curl "http://localhost:8080/movies/search?name=Hero"

# Case-insensitive search
curl "http://localhost:8080/movies/search?name=hero"

# Partial name search
curl "http://localhost:8080/movies/search?name=War"
```

### By Movie ID
```bash
# Get specific movie by ID
curl "http://localhost:8080/movies/search?id=5"

# Invalid ID returns empty results
curl "http://localhost:8080/movies/search?id=999"
```

### By Genre
```bash
# Find all Drama movies
curl "http://localhost:8080/movies/search?genre=Drama"

# Find Sci-Fi movies
curl "http://localhost:8080/movies/search?genre=Sci-Fi"

# Partial genre match
curl "http://localhost:8080/movies/search?genre=Action"
```

### Combined Search
```bash
# Name and genre
curl "http://localhost:8080/movies/search?name=Family&genre=Crime"

# All three parameters
curl "http://localhost:8080/movies/search?name=Prison&id=1&genre=Drama"
```

## 🚨 Error Handling

### Common Error Scenarios

1. **No Search Parameters (400 Bad Request)**
   ```bash
   curl "http://localhost:8080/movies/search"
   ```

2. **Empty Parameters (400 Bad Request)**
   ```bash
   curl "http://localhost:8080/movies/search?name=&id=&genre="
   ```

3. **Whitespace-only Parameters (400 Bad Request)**
   ```bash
   curl "http://localhost:8080/movies/search?name=   &genre=   "
   ```

4. **Invalid ID Format**
   ```bash
   # Non-numeric ID is ignored
   curl "http://localhost:8080/movies/search?id=abc"
   ```

## 🎬 Available Movies

The treasure chest contains these fine films:

| ID | Movie Name | Director | Year | Genre | Duration |
|----|------------|----------|------|-------|----------|
| 1 | The Prison Escape | John Director | 1994 | Drama | 142 min |
| 2 | The Family Boss | Michael Filmmaker | 1972 | Crime/Drama | 175 min |
| 3 | The Masked Hero | Chris Moviemaker | 2008 | Action/Crime | 152 min |
| 4 | Urban Stories | Quinn Director | 1994 | Crime/Drama | 154 min |
| 5 | Life Journey | Robert Filmmaker | 1994 | Drama/Romance | 142 min |
| 6 | Dream Heist | Chris Moviemaker | 2010 | Action/Sci-Fi | 148 min |
| 7 | The Virtual World | Alex Director | 1999 | Action/Sci-Fi | 136 min |
| 8 | The Wise Guys | Martin Filmmaker | 1990 | Crime/Drama | 146 min |
| 9 | The Quest for the Ring | Peter Moviemaker | 2001 | Adventure/Fantasy | 178 min |
| 10 | Space Wars: The Beginning | George Director | 1977 | Adventure/Sci-Fi | 121 min |
| 11 | The Factory Owner | Steven Filmmaker | 1993 | Drama/History | 195 min |
| 12 | Underground Club | David Moviemaker | 1999 | Drama/Thriller | 139 min |

## 🎭 Available Genres

- Action/Crime
- Action/Sci-Fi
- Adventure/Fantasy
- Adventure/Sci-Fi
- Crime/Drama
- Drama
- Drama/History
- Drama/Romance
- Drama/Thriller

## 🧪 Testing the API

### Using cURL
```bash
# Test basic search
curl -X GET "http://localhost:8080/movies/search?name=Prison" \
     -H "Accept: application/json"

# Test with pretty printing
curl -X GET "http://localhost:8080/movies/search?genre=Drama" \
     -H "Accept: application/json" | jq '.'
```

### Using JavaScript (Fetch API)
```javascript
// Search for movies
async function searchMovies(name, id, genre) {
    const params = new URLSearchParams();
    if (name) params.append('name', name);
    if (id) params.append('id', id);
    if (genre) params.append('genre', genre);
    
    try {
        const response = await fetch(`/movies/search?${params}`);
        const data = await response.json();
        
        if (data.success) {
            console.log(`Found ${data.totalResults} movies!`);
            console.log(data.pirateCheer);
            return data.movies;
        } else {
            console.error(data.message);
            return [];
        }
    } catch (error) {
        console.error('Search failed:', error);
        return [];
    }
}

// Example usage
searchMovies('Prison', null, null);
searchMovies(null, null, 'Drama');
searchMovies('Family', null, 'Crime');
```

### Using Python (requests)
```python
import requests

def search_movies(name=None, movie_id=None, genre=None):
    params = {}
    if name:
        params['name'] = name
    if movie_id:
        params['id'] = movie_id
    if genre:
        params['genre'] = genre
    
    if not params:
        print("Arrr! Ye need at least one search parameter!")
        return []
    
    try:
        response = requests.get('http://localhost:8080/movies/search', params=params)
        data = response.json()
        
        if data['success']:
            print(f"Found {data['totalResults']} movies!")
            print(data.get('pirateCheer', ''))
            return data['movies']
        else:
            print(data['message'])
            return []
    except Exception as e:
        print(f"Search failed: {e}")
        return []

# Example usage
movies = search_movies(name='Prison')
movies = search_movies(genre='Drama')
movies = search_movies(name='Family', genre='Crime')
```

## 🔧 Rate Limiting & Performance

- **No rate limiting** currently implemented (this be a demo, matey!)
- **Response time**: Typically < 100ms for search operations
- **Concurrent requests**: Supported (Spring Boot handles this like a seasoned crew)
- **Caching**: Movies are loaded once at startup and cached in memory

## 🛡️ Security Considerations

- **Input validation**: All search parameters are sanitized
- **SQL injection**: Not applicable (using in-memory data, no database)
- **XSS protection**: HTML responses are properly escaped
- **CORS**: Configure as needed for cross-origin requests

## 📈 Monitoring & Logging

All search operations are logged with pirate-themed messages:
```
INFO  - Ahoy! Searching for treasure... er, movies with criteria - name: Prison, id: null, genre: null
INFO  - Found 1 movies matching the search criteria, ye landlubber!
```

## 🚀 Future Enhancements

Potential improvements for future versions:
- Search by director, year, or rating
- Fuzzy search capabilities
- Pagination for large result sets
- Advanced filtering options
- Search result sorting
- Search analytics and popular searches

---

⚓ *Happy sailing through the movie seas, ye digital pirates!* ⚓