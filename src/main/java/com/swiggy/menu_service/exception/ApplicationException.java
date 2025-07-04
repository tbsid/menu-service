package com.swiggy.menu_service.exception;

import com.swiggy.menu_service.enums.ValidationError;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {

    private final ValidationError validationError;
    private final List<String> details;

    public ApplicationException(ValidationError validationError) {
        super(validationError.errorMessage);
        this.validationError = validationError;
        this.details = new ArrayList<>();
    }

    public ApplicationException(ValidationError validationError, String detail) {
        super(validationError.errorMessage);
        this.validationError = validationError;
        this.details = Collections.singletonList(detail);
    }

    public ApplicationException(ValidationError validationError, List<String> details) {
        super(validationError.errorMessage);
        this.validationError = validationError;
        this.details = details;
    }
}

