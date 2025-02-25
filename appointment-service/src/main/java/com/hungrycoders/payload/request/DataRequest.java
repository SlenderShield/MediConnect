package com.hungrycoders.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic wrapper class for request payloads that include a message and associated data.
 *
 * @param <T> the type of data being wrapped in the request.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataRequest<T> {

        /**
         * A message describing the context or status of the request.
         */
        private String message;

        /**
         * The generic data object associated with the request.
         */
        private T data;
}
