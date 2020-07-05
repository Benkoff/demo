package com.example.demo.models;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "document")
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Type(type = "uuid-binary")
    @Column(name = "uuid", length = 16)
    private UUID uuid;

    private String label;

    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student")
    private Student student;

    @Version
    private int version;
}
