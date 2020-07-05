package com.example.demo.models;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student extends Person {
    @Id
    @NotNull
    @Type(type = "uuid-binary")
    @Column(name = "uuid", length = 16)
    private UUID uuid;

    private String label;

    private String description;

    @Version
    private int version;

    public UUID getUuid() {
        return this.uuid;
    }

}
