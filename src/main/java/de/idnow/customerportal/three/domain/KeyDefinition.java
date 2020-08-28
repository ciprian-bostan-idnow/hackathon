package de.idnow.customerportal.three.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import de.idnow.customerportal.three.domain.enumeration.KeyType;

/**
 * A KeyDefinition.
 */
@Entity
@Table(name = "key_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KeyDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "is_required")
    private Boolean isRequired;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private KeyType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public KeyDefinition key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public KeyDefinition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public KeyDefinition defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean isIsRequired() {
        return isRequired;
    }

    public KeyDefinition isRequired(Boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public KeyType getType() {
        return type;
    }

    public KeyDefinition type(KeyType type) {
        this.type = type;
        return this;
    }

    public void setType(KeyType type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyDefinition)) {
            return false;
        }
        return id != null && id.equals(((KeyDefinition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyDefinition{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", description='" + getDescription() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", isRequired='" + isIsRequired() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
