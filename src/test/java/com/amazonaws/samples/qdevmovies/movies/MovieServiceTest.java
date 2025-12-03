package com.amazonaws.samples.qdevmovies.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ahoy! Unit tests for the MovieService treasure chest, matey!
 * These tests ensure our movie search functionality works like a well-oiled ship.
 */
public class MovieServiceTest {

    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        movieService = new MovieService();
    }

    @Test
    @DisplayName("Should load all movies from the treasure chest")
    public void testGetAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        
        assertNotNull(movies, "Movies list should not be null, ye scurvy dog!");
        assertFalse(movies.isEmpty(), "Should have movies in the treasure chest!");
        
        // Verify we have the expected number of movies from movies.json
        assertEquals(12, movies.size(), "Should have 12 movies in the treasure chest!");
    }

    @Test
    @DisplayName("Should find movie by valid ID like a true pirate navigator")
    public void testGetMovieByValidId() {
        Optional<Movie> movie = movieService.getMovieById(1L);
        
        assertTrue(movie.isPresent(), "Should find the treasure with ID 1!");
        assertEquals("The Prison Escape", movie.get().getMovieName());
        assertEquals("John Director", movie.get().getDirector());
    }

    @Test
    @DisplayName("Should return empty when searching for non-existent treasure")
    public void testGetMovieByInvalidId() {
        Optional<Movie> movie = movieService.getMovieById(999L);
        
        assertFalse(movie.isPresent(), "Should not find treasure that doesn't exist!");
    }

    @Test
    @DisplayName("Should handle null ID like a seasoned sailor")
    public void testGetMovieByNullId() {
        Optional<Movie> movie = movieService.getMovieById(null);
        
        assertFalse(movie.isPresent(), "Should not find treasure with null ID!");
    }

    @Test
    @DisplayName("Should handle negative ID gracefully")
    public void testGetMovieByNegativeId() {
        Optional<Movie> movie = movieService.getMovieById(-1L);
        
        assertFalse(movie.isPresent(), "Should not find treasure with negative ID!");
    }

    @Test
    @DisplayName("Should search movies by name like a true treasure hunter")
    public void testSearchMoviesByName() {
        List<Movie> results = movieService.searchMovies("Prison", null, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(1, results.size(), "Should find one movie with 'Prison' in the name!");
        assertEquals("The Prison Escape", results.get(0).getMovieName());
    }

    @Test
    @DisplayName("Should search movies by name case-insensitively")
    public void testSearchMoviesByNameCaseInsensitive() {
        List<Movie> results = movieService.searchMovies("prison", null, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(1, results.size(), "Should find movie regardless of case!");
        assertEquals("The Prison Escape", results.get(0).getMovieName());
    }

    @Test
    @DisplayName("Should search movies by ID like finding X marks the spot")
    public void testSearchMoviesById() {
        List<Movie> results = movieService.searchMovies(null, 2L, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(1, results.size(), "Should find exactly one movie with ID 2!");
        assertEquals("The Family Boss", results.get(0).getMovieName());
    }

    @Test
    @DisplayName("Should search movies by genre like categorizing treasure")
    public void testSearchMoviesByGenre() {
        List<Movie> results = movieService.searchMovies(null, null, "Drama");
        
        assertNotNull(results, "Search results should not be null!");
        assertTrue(results.size() > 0, "Should find movies in Drama genre!");
        
        // Verify all results contain "Drama" in genre
        for (Movie movie : results) {
            assertTrue(movie.getGenre().toLowerCase().contains("drama"), 
                      "All results should contain 'drama' in genre!");
        }
    }

    @Test
    @DisplayName("Should search movies by partial genre match")
    public void testSearchMoviesByPartialGenre() {
        List<Movie> results = movieService.searchMovies(null, null, "Sci");
        
        assertNotNull(results, "Search results should not be null!");
        assertTrue(results.size() > 0, "Should find movies with 'Sci' in genre!");
        
        // Verify all results contain "Sci" in genre (case-insensitive)
        for (Movie movie : results) {
            assertTrue(movie.getGenre().toLowerCase().contains("sci"), 
                      "All results should contain 'sci' in genre!");
        }
    }

    @Test
    @DisplayName("Should combine multiple search criteria like a master navigator")
    public void testSearchMoviesWithMultipleCriteria() {
        List<Movie> results = movieService.searchMovies("Family", null, "Crime");
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(1, results.size(), "Should find one movie matching both name and genre!");
        assertEquals("The Family Boss", results.get(0).getMovieName());
        assertTrue(results.get(0).getGenre().contains("Crime"));
    }

    @Test
    @DisplayName("Should return empty list when no treasure matches the search")
    public void testSearchMoviesNoResults() {
        List<Movie> results = movieService.searchMovies("NonExistentMovie", null, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertTrue(results.isEmpty(), "Should return empty list when no matches found!");
    }

    @Test
    @DisplayName("Should return all movies when no search criteria provided")
    public void testSearchMoviesNoCriteria() {
        List<Movie> results = movieService.searchMovies(null, null, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(movieService.getAllMovies().size(), results.size(), 
                    "Should return all movies when no criteria provided!");
    }

    @Test
    @DisplayName("Should handle empty string search gracefully")
    public void testSearchMoviesEmptyString() {
        List<Movie> results = movieService.searchMovies("", null, "");
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(movieService.getAllMovies().size(), results.size(), 
                    "Should return all movies when empty strings provided!");
    }

    @Test
    @DisplayName("Should handle whitespace-only search gracefully")
    public void testSearchMoviesWhitespaceOnly() {
        List<Movie> results = movieService.searchMovies("   ", null, "   ");
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(movieService.getAllMovies().size(), results.size(), 
                    "Should return all movies when whitespace-only strings provided!");
    }

    @Test
    @DisplayName("Should get all unique genres from the treasure chest")
    public void testGetAllGenres() {
        List<String> genres = movieService.getAllGenres();
        
        assertNotNull(genres, "Genres list should not be null!");
        assertFalse(genres.isEmpty(), "Should have genres in the list!");
        
        // Verify genres are unique and sorted
        for (int i = 1; i < genres.size(); i++) {
            assertTrue(genres.get(i).compareTo(genres.get(i-1)) >= 0, 
                      "Genres should be sorted alphabetically!");
        }
        
        // Check for expected genres
        assertTrue(genres.contains("Drama"), "Should contain Drama genre!");
        assertTrue(genres.contains("Action/Sci-Fi"), "Should contain Action/Sci-Fi genre!");
    }

    @Test
    @DisplayName("Should search by ID with zero gracefully")
    public void testSearchMoviesByZeroId() {
        List<Movie> results = movieService.searchMovies(null, 0L, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(movieService.getAllMovies().size(), results.size(), 
                    "Should return all movies when ID is zero!");
    }

    @Test
    @DisplayName("Should search by negative ID gracefully")
    public void testSearchMoviesByNegativeId() {
        List<Movie> results = movieService.searchMovies(null, -5L, null);
        
        assertNotNull(results, "Search results should not be null!");
        assertEquals(movieService.getAllMovies().size(), results.size(), 
                    "Should return all movies when ID is negative!");
    }
}