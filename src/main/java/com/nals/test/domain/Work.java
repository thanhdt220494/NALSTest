package com.nals.test.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.nals.test.domain.enumeration.Status;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "starting_date", nullable = false)
    private Instant startingDate;

    @NotNull
    @Column(name = "ending_date", nullable = false)
    private Instant endingDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Work name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartingDate() {
        return startingDate;
    }

    public Work startingDate(Instant startingDate) {
        this.startingDate = startingDate;
        return this;
    }

    public void setStartingDate(Instant startingDate) {
        this.startingDate = startingDate;
    }

    public Instant getEndingDate() {
        return endingDate;
    }

    public Work endingDate(Instant endingDate) {
        this.endingDate = endingDate;
        return this;
    }

    public void setEndingDate(Instant endingDate) {
        this.endingDate = endingDate;
    }

    public Status getStatus() {
        return status;
    }

    public Work status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Work)) {
            return false;
        }
        return id != null && id.equals(((Work) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Work{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startingDate='" + getStartingDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
