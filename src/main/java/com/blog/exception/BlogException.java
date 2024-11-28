package com.blog.exception;

public class BlogException extends RuntimeException {
    private final String errorCode;

    public BlogException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static class BlogNotFoundException extends BlogException {
        public BlogNotFoundException(Long id) {
            super("Blog not found with id: " + id, "BLOG_NOT_FOUND");
        }
    }

    public static class UnauthorizedAccessException extends BlogException {
        public UnauthorizedAccessException() {
            super("You are not authorized to perform this action", "UNAUTHORIZED_ACCESS");
        }
    }

    public static class InvalidBlogDataException extends BlogException {
        public InvalidBlogDataException(String message) {
            super(message, "INVALID_BLOG_DATA");
        }
    }
}
