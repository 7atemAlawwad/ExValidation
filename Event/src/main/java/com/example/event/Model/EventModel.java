package com.example.event.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventModel {

    @NotEmpty(message = "ID is required")
    @Size(min = 2, message = "ID must be greater than 2")
    private String ID ;

    @NotEmpty(message = "Description is required")
    @Size(min = 15, message = "Description length must be greater than 15")
    private String description;


    @Min(value = 25, message = "Capacity must be at least 25")
    @Positive(message = "Capacity must be a positive number")
    private int capacity;



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "startDate is required")
    @PastOrPresent(message = "startDate cannot be in the future")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "endDate is required")
    @FutureOrPresent(message = "endDate cannot be before today")
    private LocalDate endDate;
}
