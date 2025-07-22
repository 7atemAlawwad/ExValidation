package com.example.trackersystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tracker {

    @NotEmpty(message = "ID is required")
    @Size(min = 2, message = "ID should be at least 2 characters long")
    private String ID;

    @NotEmpty(message = "Title is required")
    @Size(min = 8, message = "Title should be at least 8 characters long")
    private String title;

    @NotEmpty(message = "Description is required")
    @Size(min = 15 , message = "Description should be at least 15 characters long")
    private String description;

    @NotEmpty(message = "Status is required")
    @Pattern(regexp = "^(Not\\sStarted|In\\sProgress|Completed)$", message = "Status must be “Not Started”, “In Progress”, or “Completed”")
    private String status;

    @NotEmpty(message = "Company Name is required")
    @Size(min = 6, message = "Company Name should be at least 6 characters long")
    private String companyName;
}
