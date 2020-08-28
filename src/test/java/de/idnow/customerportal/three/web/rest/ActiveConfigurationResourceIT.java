package de.idnow.customerportal.three.web.rest;

import de.idnow.customerportal.three.CustomerportalmissingversionApp;
import de.idnow.customerportal.three.domain.ActiveConfiguration;
import de.idnow.customerportal.three.repository.ActiveConfigurationRepository;
import de.idnow.customerportal.three.service.ActiveConfigurationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ActiveConfigurationResource} REST controller.
 */
@SpringBootTest(classes = CustomerportalmissingversionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActiveConfigurationResourceIT {

    @Autowired
    private ActiveConfigurationRepository activeConfigurationRepository;

    @Autowired
    private ActiveConfigurationService activeConfigurationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActiveConfigurationMockMvc;

    private ActiveConfiguration activeConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveConfiguration createEntity(EntityManager em) {
        ActiveConfiguration activeConfiguration = new ActiveConfiguration();
        return activeConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveConfiguration createUpdatedEntity(EntityManager em) {
        ActiveConfiguration activeConfiguration = new ActiveConfiguration();
        return activeConfiguration;
    }

    @BeforeEach
    public void initTest() {
        activeConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createActiveConfiguration() throws Exception {
        int databaseSizeBeforeCreate = activeConfigurationRepository.findAll().size();
        // Create the ActiveConfiguration
        restActiveConfigurationMockMvc.perform(post("/api/active-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activeConfiguration)))
            .andExpect(status().isCreated());

        // Validate the ActiveConfiguration in the database
        List<ActiveConfiguration> activeConfigurationList = activeConfigurationRepository.findAll();
        assertThat(activeConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        ActiveConfiguration testActiveConfiguration = activeConfigurationList.get(activeConfigurationList.size() - 1);
    }

    @Test
    @Transactional
    public void createActiveConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activeConfigurationRepository.findAll().size();

        // Create the ActiveConfiguration with an existing ID
        activeConfiguration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiveConfigurationMockMvc.perform(post("/api/active-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activeConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the ActiveConfiguration in the database
        List<ActiveConfiguration> activeConfigurationList = activeConfigurationRepository.findAll();
        assertThat(activeConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActiveConfigurations() throws Exception {
        // Initialize the database
        activeConfigurationRepository.saveAndFlush(activeConfiguration);

        // Get all the activeConfigurationList
        restActiveConfigurationMockMvc.perform(get("/api/active-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeConfiguration.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getActiveConfiguration() throws Exception {
        // Initialize the database
        activeConfigurationRepository.saveAndFlush(activeConfiguration);

        // Get the activeConfiguration
        restActiveConfigurationMockMvc.perform(get("/api/active-configurations/{id}", activeConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activeConfiguration.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingActiveConfiguration() throws Exception {
        // Get the activeConfiguration
        restActiveConfigurationMockMvc.perform(get("/api/active-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActiveConfiguration() throws Exception {
        // Initialize the database
        activeConfigurationService.save(activeConfiguration);

        int databaseSizeBeforeUpdate = activeConfigurationRepository.findAll().size();

        // Update the activeConfiguration
        ActiveConfiguration updatedActiveConfiguration = activeConfigurationRepository.findById(activeConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedActiveConfiguration are not directly saved in db
        em.detach(updatedActiveConfiguration);

        restActiveConfigurationMockMvc.perform(put("/api/active-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActiveConfiguration)))
            .andExpect(status().isOk());

        // Validate the ActiveConfiguration in the database
        List<ActiveConfiguration> activeConfigurationList = activeConfigurationRepository.findAll();
        assertThat(activeConfigurationList).hasSize(databaseSizeBeforeUpdate);
        ActiveConfiguration testActiveConfiguration = activeConfigurationList.get(activeConfigurationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingActiveConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = activeConfigurationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiveConfigurationMockMvc.perform(put("/api/active-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activeConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the ActiveConfiguration in the database
        List<ActiveConfiguration> activeConfigurationList = activeConfigurationRepository.findAll();
        assertThat(activeConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActiveConfiguration() throws Exception {
        // Initialize the database
        activeConfigurationService.save(activeConfiguration);

        int databaseSizeBeforeDelete = activeConfigurationRepository.findAll().size();

        // Delete the activeConfiguration
        restActiveConfigurationMockMvc.perform(delete("/api/active-configurations/{id}", activeConfiguration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActiveConfiguration> activeConfigurationList = activeConfigurationRepository.findAll();
        assertThat(activeConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
