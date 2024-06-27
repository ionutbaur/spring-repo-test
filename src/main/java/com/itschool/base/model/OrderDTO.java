package com.itschool.base.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        String description) {
}
