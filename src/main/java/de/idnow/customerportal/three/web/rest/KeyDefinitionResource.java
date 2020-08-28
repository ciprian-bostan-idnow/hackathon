package de.idnow.customerportal.three.web.rest;

import de.idnow.customerportal.three.domain.KeyDefinition;
import de.idnow.customerportal.three.service.KeyDefinitionService;
import de.idnow.customerportal.three.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.idnow.customerportal.three.domain.KeyDefinition}.
 */
@RestController
@RequestMapping("/api")
public class KeyDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(KeyDefinitionResource.class);

    private static final String ENTITY_NAME = "keyDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyDefinitionService keyDefinitionService;

    public KeyDefinitionResource(KeyDefinitionService keyDefinitionService) {
        this.keyDefinitionService = keyDefinitionService;
    }

    /**
     * {@code POST  /key-definitions} : Create a new keyDefinition.
     *
     * @param keyDefinition the keyDefinition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyDefinition, or with status {@code 400 (Bad Request)} if the keyDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-definitions")
    public ResponseEntity<KeyDefinition> createKeyDefinition(@Valid @RequestBody KeyDefinition keyDefinition) throws URISyntaxException {
        log.debug("REST request to save KeyDefinition : {}", keyDefinition);
        if (keyDefinition.getId() != null) {
            throw new BadRequestAlertException("A new keyDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyDefinition result = keyDefinitionService.save(keyDefinition);
        return ResponseEntity.created(new URI("/api/key-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /key-definitions} : Updates an existing keyDefinition.
     *
     * @param keyDefinition the keyDefinition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyDefinition,
     * or with status {@code 400 (Bad Request)} if the keyDefinition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyDefinition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-definitions")
    public ResponseEntity<KeyDefinition> updateKeyDefinition(@Valid @RequestBody KeyDefinition keyDefinition) throws URISyntaxException {
        log.debug("REST request to update KeyDefinition : {}", keyDefinition);
        if (keyDefinition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyDefinition result = keyDefinitionService.save(keyDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyDefinition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /key-definitions} : get all the keyDefinitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyDefinitions in body.
     */
    @GetMapping("/key-definitions")
    public ResponseEntity<List<KeyDefinition>> getAllKeyDefinitions(Pageable pageable) {
        log.debug("REST request to get a page of KeyDefinitions");
        Page<KeyDefinition> page = keyDefinitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /key-definitions/:id} : get the "id" keyDefinition.
     *
     * @param id the id of the keyDefinition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyDefinition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-definitions/{id}")
    public ResponseEntity<KeyDefinition> getKeyDefinition(@PathVariable Long id) {
        log.debug("REST request to get KeyDefinition : {}", id);
        Optional<KeyDefinition> keyDefinition = keyDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyDefinition);
    }

    /**
     * {@code DELETE  /key-definitions/:id} : delete the "id" keyDefinition.
     *
     * @param id the id of the keyDefinition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-definitions/{id}")
    public ResponseEntity<Void> deleteKeyDefinition(@PathVariable Long id) {
        log.debug("REST request to delete KeyDefinition : {}", id);
        keyDefinitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
