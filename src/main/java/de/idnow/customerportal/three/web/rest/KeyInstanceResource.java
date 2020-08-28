package de.idnow.customerportal.three.web.rest;

import de.idnow.customerportal.three.domain.KeyInstance;
import de.idnow.customerportal.three.service.KeyInstanceService;
import de.idnow.customerportal.three.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.idnow.customerportal.three.domain.KeyInstance}.
 */
@RestController
@RequestMapping("/api")
public class KeyInstanceResource {

    private final Logger log = LoggerFactory.getLogger(KeyInstanceResource.class);

    private static final String ENTITY_NAME = "keyInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyInstanceService keyInstanceService;

    public KeyInstanceResource(KeyInstanceService keyInstanceService) {
        this.keyInstanceService = keyInstanceService;
    }

    /**
     * {@code POST  /key-instances} : Create a new keyInstance.
     *
     * @param keyInstance the keyInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyInstance, or with status {@code 400 (Bad Request)} if the keyInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-instances")
    public ResponseEntity<KeyInstance> createKeyInstance(@Valid @RequestBody KeyInstance keyInstance) throws URISyntaxException {
        log.debug("REST request to save KeyInstance : {}", keyInstance);
        if (keyInstance.getId() != null) {
            throw new BadRequestAlertException("A new keyInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyInstance result = keyInstanceService.save(keyInstance);
        return ResponseEntity.created(new URI("/api/key-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /key-instances} : Updates an existing keyInstance.
     *
     * @param keyInstance the keyInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyInstance,
     * or with status {@code 400 (Bad Request)} if the keyInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-instances")
    public ResponseEntity<KeyInstance> updateKeyInstance(@Valid @RequestBody KeyInstance keyInstance) throws URISyntaxException {
        log.debug("REST request to update KeyInstance : {}", keyInstance);
        if (keyInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyInstance result = keyInstanceService.save(keyInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /key-instances} : get all the keyInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyInstances in body.
     */
    @GetMapping("/key-instances")
    public List<KeyInstance> getAllKeyInstances() {
        log.debug("REST request to get all KeyInstances");
        return keyInstanceService.findAll();
    }

    /**
     * {@code GET  /key-instances/:id} : get the "id" keyInstance.
     *
     * @param id the id of the keyInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-instances/{id}")
    public ResponseEntity<KeyInstance> getKeyInstance(@PathVariable Long id) {
        log.debug("REST request to get KeyInstance : {}", id);
        Optional<KeyInstance> keyInstance = keyInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyInstance);
    }

    /**
     * {@code DELETE  /key-instances/:id} : delete the "id" keyInstance.
     *
     * @param id the id of the keyInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-instances/{id}")
    public ResponseEntity<Void> deleteKeyInstance(@PathVariable Long id) {
        log.debug("REST request to delete KeyInstance : {}", id);
        keyInstanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
