package com.blog.service;

import org.springframework.stereotype.Service;
import com.blog.entity.Blog;
import com.blog.repository.BlogRepository;
import com.blog.model.WordFrequency;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordFrequencyService {
    private final Set<String> stopWords;
    private final BlogRepository blogRepository;
    private static final int TOP_WORDS_LIMIT = 5;

    public WordFrequencyService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
        // Initialize common English stop words
        stopWords = new HashSet<>(Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
            "has", "he", "in", "is", "it", "its", "of", "on", "that", "the",
            "to", "was", "were", "will", "with", "the", "this", "but", "they",
            "have", "had", "what", "when", "where", "who", "which", "why", "how",
            "all", "any", "both", "each", "few", "more", "most", "other", "some",
            "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "can", "cannot", "could", "would", "should", "while"
        ));
    }

    public List<WordFrequency> analyzeUserBlogsWordFrequency(String username) {
        // Get all blogs for the user
        List<Blog> userBlogs = blogRepository.findByUserUsername(username);
        
        // Combine all blog content
        String combinedText = userBlogs.stream()
            .map(Blog::getBody)
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));

        // Get word frequencies
        Map<String, Integer> wordFreqMap = analyzeWordFrequency(combinedText);

        // Sort by frequency and get top 5
        return wordFreqMap.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(TOP_WORDS_LIMIT)
            .map(entry -> new WordFrequency(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    private Map<String, Integer> analyzeWordFrequency(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new HashMap<>();
        }

        // First handle possessive forms, then remove other punctuation
        String[] words = text.toLowerCase()
                           .replaceAll("'s\\b", "")  // Remove possessive 's
                           .replaceAll("s'\\b", "s") // Handle plural possessive
                           .replaceAll("[^a-zA-Z\\s]", "") // Remove remaining punctuation
                           .split("\\s+");

        // Count word frequencies excluding stop words
        return Arrays.stream(words)
                    .filter(word -> !word.isEmpty())
                    .filter(word -> !stopWords.contains(word))
                    .collect(Collectors.groupingBy(
                        word -> word,
                        Collectors.collectingAndThen(
                            Collectors.counting(),
                            Long::intValue
                        )
                    ));
    }
}
