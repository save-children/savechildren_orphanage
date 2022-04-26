package com.supung.orphanage.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
public class ApiError {
	private HttpStatus status;
	private String message;
}
