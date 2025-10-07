package com.learn.biblioteca.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.Year;

public record LibroRequest(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El autor es obligatorio")
        String autor,

        @Min(value = 1500, message = "El año no puede ser anterior a 1500")
        @Max(value = Year.MAX_VALUE, message = "El año no es válido")
        int anio
) {}
