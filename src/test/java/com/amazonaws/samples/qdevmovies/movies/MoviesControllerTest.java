package com.amazonaws.samples.qdevmovies.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ahoy! Unit tests for the MoviesController, ye scurvy dogs!
 * These tests ensure our movie search endpoints work like a well-navigated ship.
 */
public class MoviesControllerTest {

    private MoviesController moviesController;
    private Model model;
    private MockMovieService mockMovieService;
    private MockReviewService mockReviewService;

    // Mock MovieService for testing
    private static class MockMovieService extends MovieService {
        private List<Movie> testMovies;

        public MockMovieService() {
            testMovies = Arrays.asList(
                new Movie(1L, "The Prison Escape", "John Director", 1994, "Drama", "Test description", 142, 5.0),
                new Movie(2L, "The Family Boss", "Michael Filmmaker", 1972, "Crime/Drama", "Test description", 175, 5.0),
                new Movie(3L, "The Masked Hero", "Chris Moviemaker", 2008, "Action/Crime", "Test description", 152, 5.0)
            );
        }

        @Override
        public List<Movie> getAllMovies() {
            return testMovies;
        }

        @Override
        public Optional<Movie> getMovieById(Long id) {
            return testMovies.stream()
                .filter(movie -> movie.getId() == id)
                .findFirst();
        }

        @Override
        public List<Movie> searchMovies(String name, Long id, String genre) {
            List<Movie> results = new ArrayList<>(testMovies);
            
            if (id != null && id > 0) {
                results = results.stream()
                    .filter(movie -> movie.getId() == id)
                    .collect(java.util.stream.Collectors.toList());
            }
            
            if (name != null && !name.trim().isEmpty()) {
                String searchName = name.trim().toLowerCase();
                results = results.stream()
                    .filter(movie -> movie.getMovieName().toLowerCase().contains(searchName))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            if (genre != null && !genre.trim().isEmpty()) {
                String searchGenre = genre.trim().toLowerCase();
                results = results.stream()
                    .filter(movie -> movie.getGenre().toLowerCase().contains(searchGenre))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            return results;
        }

        @Override
        public List<String> getAllGenres() {
            return Arrays.asList("Action/Crime", "Crime/Drama", "Drama");
        }
    }

    // Mock ReviewService for testing
    private static class MockReviewService extends ReviewService {
        @Override
        public List<Review> getReviewsForMovie(long movieId) {
            return new ArrayList<>();
        }
    }

    @BeforeEach
    public void setUp() {
        moviesController = new MoviesController();
        model = new ExtendedModelMap();
        
        mockMovieService = new MockMovieService();
        mockReviewService = new MockReviewService();
        
        // Inject mocks using reflection
        try {
            java.lang.reflect.Field movieServiceField = MoviesController.class.getDeclaredField("movieService");
            movieServiceField.setAccessible(true);
            movieServiceField.set(moviesController, mockMovieService);
            
            java.lang.reflect.Field reviewServiceField = MoviesController.class.getDeclaredField("reviewService");
            reviewServiceField.setAccessible(true);
            reviewServiceField.set(moviesController, mockReviewService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock services", e);
        }
    }

    @Test
    @DisplayName("Should display all movies when no search parameters provided")
    public void testGetMoviesNoSearch() {
        String result = moviesController.getMovies(model, null, null, null);
        
        assertEquals("movies", result);
        assertFalse((Boolean) model.asMap().get("searchPerformed"));
        
        @SuppressWarnings("unchecked")
        List<Movie> movies = (List<Movie>) model.asMap().get("movies");
        assertEquals(3, movies.size());
    }

    @Test
    @DisplayName("Should search movies by name like a treasure hunter")
    public void testGetMoviesSearchByName() {
        String result = moviesController.getMovies(model, "Prison", null, null);
        
        assertEquals("movies", result);
        assertTrue((Boolean) model.asMap().get("searchPerformed"));
        assertEquals("Prison", model.asMap().get("searchName"));
        
        @SuppressWarnings("unchecked")
        List<Movie> movies = (List<Movie>) model.asMap().get("movies");
        assertEquals(1, movies.size());
        assertEquals("The Prison Escape", movies.get(0).getMovieName());
    }

    @Test
    @DisplayName("Should get movie details successfully")
    public void testGetMovieDetails() {
        String result = moviesController.getMovieDetails(1L, model);
        
        assertEquals("movie-details", result);
        assertNotNull(model.asMap().get("movie"));
        
        Movie movie = (Movie) model.asMap().get("movie");
        assertEquals("The Prison Escape", movie.getMovieName());
    }

    @Test
    @DisplayName("Should handle movie not found gracefully")
    public void testGetMovieDetailsNotFound() {
        String result = moviesController.getMovieDetails(999L, model);
        
        assertEquals("error", result);
        assertEquals("Movie Not Found", model.asMap().get("title"));
        assertNotNull(model.asMap().get("message"));
    }

    @Test
    @DisplayName("Should return successful API search response")
    public void testSearchMoviesApiSuccess() {
        ResponseEntity<Map<String, Object>> response = moviesController.searchMoviesApi("Prison", null, null);
        
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue((Boolean) body.get("success"));
        assertEquals(1, body.get("totalResults"));
        assertNotNull(body.get("movies"));
        assertNotNull(body.get("message"));
        assertNotNull(body.get("pirateCheer"));
    }

    @Test
    @DisplayName("Should return bad request when no search parameters provided to API")
    public void testSearchMoviesApiNoParameters() {
        ResponseEntity<Map<String, Object>> response = moviesController.searchMoviesApi(null, null, null);
        
        assertEquals(400, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertFalse((Boolean) body.get("success"));
        assertNotNull(body.get("message"));
        assertNotNull(body.get("pirateWisdom"));
    }
}
