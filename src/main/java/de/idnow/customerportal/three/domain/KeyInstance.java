package de.idnow.customerportal.three.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A KeyInstance.
 */
@Entity
@Table(name = "key_instance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KeyInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = "keyInstances", allowSetters = true)
    private KeyDefinition definition;

    @ManyToOne
    @JsonIgnoreProperties(value = "keyInstances", allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "keyInstances", allowSetters = true)
    private Template template;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public KeyInstance value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public KeyDefinition getDefinition() {
        return definition;
    }

    public KeyInstance definition(KeyDefinition keyDefinition) {
        this.definition = keyDefinition;
        return this;
    }

    public void setDefinition(KeyDefinition keyDefinition) {
        this.definition = keyDefinition;
    }

    public Customer getCustomer() {
        return customer;
    }

    public KeyInstance customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Template getTemplate() {
        return template;
    }

    public KeyInstance template(Template template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyInstance)) {
            return false;
        }
        return id != null && id.equals(((KeyInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyInstance{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
