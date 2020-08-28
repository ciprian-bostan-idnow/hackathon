package de.idnow.customerportal.three.web.rest;

import de.idnow.customerportal.three.domain.ActiveConfiguration;
import de.idnow.customerportal.three.service.ActiveConfigurationService;
import de.idnow.customerportal.three.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.idnow.customerportal.three.domain.ActiveConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class ActiveConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ActiveConfigurationResource.class);

    private static final String ENTITY_NAME = "activeConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActiveConfigurationService activeConfigurationService;

    public ActiveConfigurationResource(ActiveConfigurationService activeConfigurationService) {
        this.activeConfigurationService = activeConfigurationService;
    }

    /**
     * {@code POST  /active-configurations} : Create a new activeConfiguration.
     *
     * @param activeConfiguration the activeConfiguration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activeConfiguration, or with status {@code 400 (Bad Request)} if the activeConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/active-configurations")
    public ResponseEntity<ActiveConfiguration> createActiveConfiguration(@RequestBody ActiveConfiguration activeConfiguration) throws URISyntaxException {
        log.debug("REST request to save ActiveConfiguration : {}", activeConfiguration);
        if (activeConfiguration.getId() != null) {
            throw new BadRequestAlertException("A new activeConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActiveConfiguration result = activeConfigurationService.save(activeConfiguration);
        return ResponseEntity.created(new URI("/api/active-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /active-configurations} : Updates an existing activeConfiguration.
     *
     * @param activeConfiguration the activeConfiguration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeConfiguration,
     * or with status {@code 400 (Bad Request)} if the activeConfiguration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activeConfiguration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/active-configurations")
    public ResponseEntity<ActiveConfiguration> updateActiveConfiguration(@RequestBody ActiveConfiguration activeConfiguration) throws URISyntaxException {
        log.debug("REST request to update ActiveConfiguration : {}", activeConfiguration);
        if (activeConfiguration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActiveConfiguration result = activeConfigurationService.save(activeConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /active-configurations} : get all the activeConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activeConfigurations in body.
     */
    @GetMapping("/active-configurations")
    public List<ActiveConfiguration> getAllActiveConfigurations() {
        log.debug("REST request to get all ActiveConfigurations");
        return activeConfigurationService.findAll();
    }

    /**
     * {@code GET  /active-configurations/:id} : get the "id" activeConfiguration.
     *
     * @param id the id of the activeConfiguration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activeConfiguration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/active-configurations/{id}")
    public ResponseEntity<ActiveConfiguration> getActiveConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ActiveConfiguration : {}", id);
        Optional<ActiveConfiguration> activeConfiguration = activeConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activeConfiguration);
    }

    /**
     * {@code DELETE  /active-configurations/:id} : delete the "id" activeConfiguration.
     *
     * @param id the id of the activeConfiguration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/active-configurations/{id}")
    public ResponseEntity<Void> deleteActiveConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete ActiveConfiguration : {}", id);
        activeConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
