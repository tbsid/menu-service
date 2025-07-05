package com.swiggy.menu_service.enums;

public enum ValidationError {

    // 400 Bad Request - Input Validation Errors
    MANDATORY_FIELD_MISSING(400, "ER4001", "Required field is missing", ErrorType.REQUEST_VALIDATION_ERROR),
    INVALID_FIELD_FORMAT(400, "ER4002", "Field format is invalid", ErrorType.REQUEST_VALIDATION_ERROR),
    FIELD_VALUE_OUT_OF_RANGE(400, "ER4003", "Field value is out of acceptable range", ErrorType.REQUEST_VALIDATION_ERROR),
    FIELD_LENGTH_EXCEEDED(400, "ER4004", "Field length exceeds maximum allowed", ErrorType.REQUEST_VALIDATION_ERROR),
    INVALID_DATE_FORMAT(400, "ER4005", "Invalid date format", ErrorType.REQUEST_VALIDATION_ERROR),
    INVALID_EMAIL_FORMAT(400, "ER4006", "Invalid email format", ErrorType.REQUEST_VALIDATION_ERROR),
    INVALID_PHONE_FORMAT(400, "ER4007", "Invalid phone number format", ErrorType.REQUEST_VALIDATION_ERROR),
    INVALID_JSON_FORMAT(400, "ER4008", "Invalid JSON format in request", ErrorType.REQUEST_VALIDATION_ERROR),
    INVALID_REQUEST_PARAMETER(400, "ER4009", "Invalid request parameter", ErrorType.REQUEST_VALIDATION_ERROR),
    REQUEST_BODY_NOT_READABLE(400, "ER4010", "Request body not readable", ErrorType.BAD_REQUEST),

    // 404 Not Found - Resource Not Found Errors
    RESOURCE_NOT_FOUND(404, "ER4401", "Requested resource not found", ErrorType.NOT_FOUND_ERROR),
    ENTITY_NOT_FOUND(404, "ER4402", "Requested entity not found", ErrorType.NOT_FOUND_ERROR),
    USER_NOT_FOUND(404, "ER4403", "Requested user not found", ErrorType.NOT_FOUND_ERROR),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED(405, "ER4501", "HTTP method not allowed for this endpoint", ErrorType.BAD_REQUEST),

    // 429 Too Many Requests
    TOO_MANY_REQUESTS(429, "ER4291", "Too many requests - please try again later", ErrorType.TECHNICAL_ERROR),

    // 500 Internal Server Error - Technical Errors
    INTERNAL_SERVER_ERROR(500, "ER5001", "Internal server error", ErrorType.TECHNICAL_ERROR),
    DATABASE_ERROR(500, "ER5002", "Database operation failed", ErrorType.TECHNICAL_ERROR),
    UNEXPECTED_ERROR(500, "ER5003", "An unexpected error occurred", ErrorType.TECHNICAL_ERROR),
    SERIALIZATION_ERROR(500, "ER5004", "Error serializing response", ErrorType.TECHNICAL_ERROR),
    CACHE_ERROR(500, "ER5005", "Cache operation failed", ErrorType.TECHNICAL_ERROR);

    public final int errorStatus;
    public final String errorCode;
    public final String errorMessage;
    public final ErrorType errorType;

    ValidationError(int errorStatus, String errorCode, String errorMessage, ErrorType errorType) {
        this.errorStatus = errorStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

}
