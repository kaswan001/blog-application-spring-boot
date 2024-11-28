package com.blog.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BlogException.class)
    public String handleBlogException(BlogException ex, Model model, HttpServletRequest request) {
        logger.error("Blog exception occurred: {}", ex.getMessage(), ex);
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("errorCode", ex.getErrorCode());
        model.addAttribute("path", request.getRequestURI());
        return "error/custom-error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model, HttpServletRequest request) {
        logger.error("Access denied: {}", ex.getMessage(), ex);
        model.addAttribute("error", "You do not have permission to access this resource");
        model.addAttribute("errorCode", "ACCESS_DENIED");
        model.addAttribute("path", request.getRequestURI());
        return "error/custom-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model, HttpServletRequest request) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        model.addAttribute("errorCode", "INTERNAL_ERROR");
        model.addAttribute("path", request.getRequestURI());
        return "error/custom-error";
    }
}
