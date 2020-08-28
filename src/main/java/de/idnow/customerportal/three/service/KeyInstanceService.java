package de.idnow.customerportal.three.service;

import de.idnow.customerportal.three.domain.KeyInstance;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link KeyInstance}.
 */
public interface KeyInstanceService {

    /**
     * Save a keyInstance.
     *
     * @param keyInstance the entity to save.
     * @return the persisted entity.
     */
    KeyInstance save(KeyInstance keyInstance);

    /**
     * Get all the keyInstances.
     *
     * @return the list of entities.
     */
    List<KeyInstance> findAll();


    /**
     * Get the "id" keyInstance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KeyInstance> findOne(Long id);

    /**
     * Delete the "id" keyInstance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
