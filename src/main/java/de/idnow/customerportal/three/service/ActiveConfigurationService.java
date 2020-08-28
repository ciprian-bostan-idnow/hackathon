package de.idnow.customerportal.three.service;

import de.idnow.customerportal.three.domain.ActiveConfiguration;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ActiveConfiguration}.
 */
public interface ActiveConfigurationService {

    /**
     * Save a activeConfiguration.
     *
     * @param activeConfiguration the entity to save.
     * @return the persisted entity.
     */
    ActiveConfiguration save(ActiveConfiguration activeConfiguration);

    /**
     * Get all the activeConfigurations.
     *
     * @return the list of entities.
     */
    List<ActiveConfiguration> findAll();


    /**
     * Get the "id" activeConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActiveConfiguration> findOne(Long id);

    /**
     * Delete the "id" activeConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
