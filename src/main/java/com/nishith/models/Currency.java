package com.nishith.models;

import java.io.Serial;
import java.io.Serializable;

public class Currency implements Serializable {
    @Serial
    private static final long serialVersionUID = 169911805544196686L;

    private Long id;

    private String name;

    private String description;

    public Currency() {
    }

    public Currency(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
