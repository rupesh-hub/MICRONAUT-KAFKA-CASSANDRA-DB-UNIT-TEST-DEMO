package com.rupesh.mdel;

import io.micronaut.serde.annotation.SerdeImport;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;

@SerdeImport(GlobalResponse.class)
@Serdeable
public class GlobalResponse<T> {

    private Instant timestamp;
    private String message;
    private String status;
    private T data;

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {
        private Instant timestamp;
        private String message;
        private String status;
        private T data;
        private Builder() {
        }

        public Builder<T> timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> status(String status) {
            this.status = status;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public GlobalResponse<T> build() {
            GlobalResponse<T> globalResponse = new GlobalResponse<>();
            globalResponse.timestamp = this.timestamp;
            globalResponse.message = this.message;
            globalResponse.data = this.data;
            globalResponse.status = this.status;
            return globalResponse;
        }
    }


}
