package com.github.jfsql.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Task {

    private final long id;
    private final String description;
    private final boolean completed;
}
