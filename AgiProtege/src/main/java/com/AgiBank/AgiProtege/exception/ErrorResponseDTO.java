package com.AgiBank.AgiProtege.exception;

public record ErrorResponseDTO(String message, int status, String timestamp, String path) {
}
