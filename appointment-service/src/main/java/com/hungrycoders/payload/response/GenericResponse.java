package com.hungrycoders.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private String message;

    private T data;

    public GenericResponse(String message) {
        this.message = message;
    }
}
