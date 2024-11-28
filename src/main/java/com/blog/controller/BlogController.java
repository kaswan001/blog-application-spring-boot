package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.model.WordFrequency;
import com.blog.service.BlogService;
import com.blog.service.WordFrequencyService;
import com.blog.service.UserService;
import com.blog.exception.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private WordFrequencyService wordFrequencyService;
    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String showNewBlogForm(Model model, Authentication authentication) {
        if (authentication == null) {
            throw new BlogException.UnauthorizedAccessException();
        }
        model.addAttribute("blog", new Blog());
        model.addAttribute("currentUsername", authentication.getName());
        return "blog/new";
    }

    @PostMapping("/new")
    public String createBlog(@Valid @ModelAttribute Blog blog, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            throw new BlogException.InvalidBlogDataException("Please check your input and try again");
        }
        if (authentication == null) {
            throw new BlogException.UnauthorizedAccessException();
        }
        blogService.createBlog(blog, authentication.getName());
        return "redirect:/blog/list";
    }

    @GetMapping("/list")
    public String listBlogs(Model model, Authentication authentication) {
        List<Blog> blogs = blogService.getAllBlogs();
        String currentUsername = authentication != null ? authentication.getName() : null;
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentUsername", currentUsername);
        return "blog/list";
    }

    @GetMapping("/{id}")
    public String viewBlog(@PathVariable Long id, Model model, Authentication authentication) {
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {
            throw new BlogException.BlogNotFoundException(id);
        }
        
        String currentUsername = authentication != null ? authentication.getName() : null;
        model.addAttribute("blog", blog);
        model.addAttribute("currentUsername", currentUsername);
        
        // Get top 5 word frequencies for all user's blogs
        List<WordFrequency> wordFrequencies = wordFrequencyService.analyzeUserBlogsWordFrequency(blog.getUser().getUsername());
        model.addAttribute("wordFrequencies", wordFrequencies);
        
        return "blog/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication == null) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {
            throw new BlogException.BlogNotFoundException(id);
        }
        
        if (!blog.getUser().getUsername().equals(authentication.getName())) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        model.addAttribute("blog", blog);
        model.addAttribute("currentUsername", authentication.getName());
        return "blog/edit";
    }

    @PostMapping("/{id}")
    public String updateBlog(@PathVariable Long id, @Valid @ModelAttribute Blog blog, 
                           BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            throw new BlogException.InvalidBlogDataException("Please check your input and try again");
        }
        
        if (authentication == null) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        Blog existingBlog = blogService.getBlogById(id);
        if (existingBlog == null) {
            throw new BlogException.BlogNotFoundException(id);
        }
        
        if (!existingBlog.getUser().getUsername().equals(authentication.getName())) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        blog.setId(id);
        blog.setUser(existingBlog.getUser());
        blogService.updateBlog(blog);
        return "redirect:/blog/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteBlog(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {
            throw new BlogException.BlogNotFoundException(id);
        }
        
        if (!blog.getUser().getUsername().equals(authentication.getName())) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        blogService.deleteBlog(id);
        return "redirect:/blog/list";
    }

    @GetMapping("/report")
    public String wordFrequencyReport(Model model, Authentication authentication) {
        if (authentication == null) {
            throw new BlogException.UnauthorizedAccessException();
        }
        
        List<WordFrequency> frequentWords = wordFrequencyService.analyzeUserBlogsWordFrequency(authentication.getName());
        model.addAttribute("frequentWords", frequentWords);
        model.addAttribute("currentUsername", authentication.getName());
        return "blog/report";
    }

    @GetMapping("/user/{username}")
    public String listUserBlogs(@PathVariable String username, Model model, Authentication authentication) {
        List<Blog> blogs = blogService.getBlogsByUsername(username);
        model.addAttribute("blogs", blogs);
        String currentUsername = authentication != null ? authentication.getName() : null;
        model.addAttribute("currentUsername", currentUsername);
        return "blog/list";
    }
}
