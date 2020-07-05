package com.example.demo.models.requests;

import java.io.Serializable;

public class IdRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private IdRequest() {
        // empty
    }

    public IdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
