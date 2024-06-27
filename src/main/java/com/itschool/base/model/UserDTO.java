package com.itschool.base.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserDTO(
        String name,
        String email,
        int age,
        AddressDTO address,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) // this annotation will make the id field read-only, meaning we don't provide it on request when creating a new UserDTO object
        List<OrderDTO> orders) {
}
