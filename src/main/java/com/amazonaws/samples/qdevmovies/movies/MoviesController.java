package com.amazonaws.samples.qdevmovies.movies;

import com.amazonaws.samples.qdevmovies.utils.MovieIconUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MoviesController {
    private static final Logger logger = LogManager.getLogger(MoviesController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movies")
    public String getMovies(org.springframework.ui.Model model,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "id", required = false) Long id,
                           @RequestParam(value = "genre", required = false) String genre) {
        logger.info("Ahoy! Fetching movies with search criteria - name: {}, id: {}, genre: {}", name, id, genre);
        
        List<Movie> movies;
        boolean isSearch = (name != null && !name.trim().isEmpty()) || 
                          (id != null && id > 0) || 
                          (genre != null && !genre.trim().isEmpty());
        
        if (isSearch) {
            movies = movieService.searchMovies(name, id, genre);
            model.addAttribute("searchPerformed", true);
            model.addAttribute("searchName", name);
            model.addAttribute("searchId", id);
            model.addAttribute("searchGenre", genre);
            
            if (movies.isEmpty()) {
                model.addAttribute("noResults", true);
                model.addAttribute("pirateMessage", "Arrr! No treasure found with those search terms, matey! Try different criteria or sail back to see all movies.");
            }
        } else {
            movies = movieService.getAllMovies();
            model.addAttribute("searchPerformed", false);
        }
        
        model.addAttribute("movies", movies);
        model.addAttribute("allGenres", movieService.getAllGenres());
        return "movies";
    }

    /**
     * REST API endpoint for movie search - returns JSON response, ye scurvy dog!
     * This be the treasure map for other pirates who want to search programmatically.
     */
    @GetMapping("/movies/search")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchMoviesApi(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "genre", required = false) String genre) {
        
        logger.info("Ahoy! API search request with criteria - name: {}, id: {}, genre: {}", name, id, genre);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate that at least one search parameter is provided
            if ((name == null || name.trim().isEmpty()) && 
                (id == null || id <= 0) && 
                (genre == null || genre.trim().isEmpty())) {
                
                response.put("success", false);
                response.put("message", "Arrr! Ye need to provide at least one search parameter, matey! (name, id, or genre)");
                response.put("pirateWisdom", "A pirate without a map be lost at sea! 🏴‍☠️");
                return ResponseEntity.badRequest().body(response);
            }
            
            List<Movie> searchResults = movieService.searchMovies(name, id, genre);
            
            response.put("success", true);
            response.put("movies", searchResults);
            response.put("totalResults", searchResults.size());
            response.put("searchCriteria", Map.of(
                "name", name != null ? name : "",
                "id", id != null ? id : "",
                "genre", genre != null ? genre : ""
            ));
            
            if (searchResults.isEmpty()) {
                response.put("message", "No movies found matching yer search criteria, ye landlubber!");
                response.put("pirateAdvice", "Try broadening yer search or check the spelling, arrr!");
            } else {
                response.put("message", String.format("Found %d movies in the treasure chest, matey!", searchResults.size()));
                response.put("pirateCheer", "Shiver me timbers! We found some fine films! 🎬");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Blimey! Error during movie search: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Arrr! Something went wrong during the search, ye scurvy dog!");
            response.put("error", e.getMessage());
            response.put("pirateApology", "Even the best pirates face storms at sea! Try again, matey! ⚓");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/movies/{id}/details")
    public String getMovieDetails(@PathVariable("id") Long movieId, org.springframework.ui.Model model) {
        logger.info("Fetching details for movie ID: {}", movieId);
        
        Optional<Movie> movieOpt = movieService.getMovieById(movieId);
        if (!movieOpt.isPresent()) {
            logger.warn("Movie with ID {} not found", movieId);
            model.addAttribute("title", "Movie Not Found");
            model.addAttribute("message", "Movie with ID " + movieId + " was not found.");
            return "error";
        }
        
        Movie movie = movieOpt.get();
        model.addAttribute("movie", movie);
        model.addAttribute("movieIcon", MovieIconUtils.getMovieIcon(movie.getMovieName()));
        model.addAttribute("allReviews", reviewService.getReviewsForMovie(movie.getId()));
        
        return "movie-details";
    }
}