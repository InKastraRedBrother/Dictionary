package ru.dictionary.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SuccessMessage {
    private boolean isSuccessful;
    private String message;
}
