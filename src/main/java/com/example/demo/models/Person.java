package com.example.demo.models;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Parent Class for Student and similar entities providing information that is out of scope of this story
 * e.g. names, address, data on parents etc.
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

}
