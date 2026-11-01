package com.fluxit.project_multitasking.dto;

public record ProcessingResult<T>(
    String strategy,
    long elapsedTimeMs,
    long processedRecords,
    T result
) {}
