package com.nals.test.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.nals.test.domain.enumeration.Status;

/**
 * A DTO for the {@link com.nals.test.domain.Work} entity.
 */
public class WorkDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Instant startingDate;

    @NotNull
    private Instant endingDate;

    @NotNull
    private Status status;


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

    public Instant getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Instant startingDate) {
        this.startingDate = startingDate;
    }

    public Instant getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Instant endingDate) {
        this.endingDate = endingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkDTO workDTO = (WorkDTO) o;
        if (workDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startingDate='" + getStartingDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
