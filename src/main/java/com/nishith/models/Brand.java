package com.nishith.models;

import java.io.Serial;
import java.io.Serializable;

public class Brand implements Serializable {

    @Serial
    private static final long serialVersionUID = -3793821753351703642L;

    private Long id;

    private String name;

    private String description;

    public Brand() {
    }

    public Brand(Long id, String name, String description) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return name.equals(brand.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
