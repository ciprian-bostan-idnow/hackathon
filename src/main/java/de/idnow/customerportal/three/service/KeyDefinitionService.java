package de.idnow.customerportal.three.service;

import de.idnow.customerportal.three.domain.KeyDefinition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link KeyDefinition}.
 */
public interface KeyDefinitionService {

    /**
     * Save a keyDefinition.
     *
     * @param keyDefinition the entity to save.
     * @return the persisted entity.
     */
    KeyDefinition save(KeyDefinition keyDefinition);

    /**
     * Get all the keyDefinitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KeyDefinition> findAll(Pageable pageable);


    /**
     * Get the "id" keyDefinition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KeyDefinition> findOne(Long id);

    /**
     * Delete the "id" keyDefinition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
