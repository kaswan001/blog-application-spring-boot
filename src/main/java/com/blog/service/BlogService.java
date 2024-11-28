package com.blog.service;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.repository.BlogRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
@Transactional
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    public Blog createBlog(Blog blog, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        blog.setUser(user);
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Blog blog) {
        Blog existingBlog = getBlogById(blog.getId());
        existingBlog.setName(blog.getName());
        existingBlog.setBody(blog.getBody());
        return blogRepository.save(existingBlog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getBlogsByUsername(String username) {
        return blogRepository.findByUserUsername(username);
    }
}
