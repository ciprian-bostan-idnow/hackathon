package de.idnow.customerportal.three.web.rest;

import de.idnow.customerportal.three.customerportalhackathonApp;
import de.idnow.customerportal.three.domain.KeyInstance;
import de.idnow.customerportal.three.repository.KeyInstanceRepository;
import de.idnow.customerportal.three.service.KeyInstanceService;

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
 * Integration tests for the {@link KeyInstanceResource} REST controller.
 */
@SpringBootTest(classes = customerportalhackathonApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KeyInstanceResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private KeyInstanceRepository keyInstanceRepository;

    @Autowired
    private KeyInstanceService keyInstanceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeyInstanceMockMvc;

    private KeyInstance keyInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyInstance createEntity(EntityManager em) {
        KeyInstance keyInstance = new KeyInstance()
            .value(DEFAULT_VALUE);
        return keyInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyInstance createUpdatedEntity(EntityManager em) {
        KeyInstance keyInstance = new KeyInstance()
            .value(UPDATED_VALUE);
        return keyInstance;
    }

    @BeforeEach
    public void initTest() {
        keyInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyInstance() throws Exception {
        int databaseSizeBeforeCreate = keyInstanceRepository.findAll().size();
        // Create the KeyInstance
        restKeyInstanceMockMvc.perform(post("/api/key-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyInstance)))
            .andExpect(status().isCreated());

        // Validate the KeyInstance in the database
        List<KeyInstance> keyInstanceList = keyInstanceRepository.findAll();
        assertThat(keyInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        KeyInstance testKeyInstance = keyInstanceList.get(keyInstanceList.size() - 1);
        assertThat(testKeyInstance.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createKeyInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyInstanceRepository.findAll().size();

        // Create the KeyInstance with an existing ID
        keyInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyInstanceMockMvc.perform(post("/api/key-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyInstance)))
            .andExpect(status().isBadRequest());

        // Validate the KeyInstance in the database
        List<KeyInstance> keyInstanceList = keyInstanceRepository.findAll();
        assertThat(keyInstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyInstanceRepository.findAll().size();
        // set the field null
        keyInstance.setValue(null);

        // Create the KeyInstance, which fails.


        restKeyInstanceMockMvc.perform(post("/api/key-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyInstance)))
            .andExpect(status().isBadRequest());

        List<KeyInstance> keyInstanceList = keyInstanceRepository.findAll();
        assertThat(keyInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKeyInstances() throws Exception {
        // Initialize the database
        keyInstanceRepository.saveAndFlush(keyInstance);

        // Get all the keyInstanceList
        restKeyInstanceMockMvc.perform(get("/api/key-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getKeyInstance() throws Exception {
        // Initialize the database
        keyInstanceRepository.saveAndFlush(keyInstance);

        // Get the keyInstance
        restKeyInstanceMockMvc.perform(get("/api/key-instances/{id}", keyInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keyInstance.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingKeyInstance() throws Exception {
        // Get the keyInstance
        restKeyInstanceMockMvc.perform(get("/api/key-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyInstance() throws Exception {
        // Initialize the database
        keyInstanceService.save(keyInstance);

        int databaseSizeBeforeUpdate = keyInstanceRepository.findAll().size();

        // Update the keyInstance
        KeyInstance updatedKeyInstance = keyInstanceRepository.findById(keyInstance.getId()).get();
        // Disconnect from session so that the updates on updatedKeyInstance are not directly saved in db
        em.detach(updatedKeyInstance);
        updatedKeyInstance
            .value(UPDATED_VALUE);

        restKeyInstanceMockMvc.perform(put("/api/key-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKeyInstance)))
            .andExpect(status().isOk());

        // Validate the KeyInstance in the database
        List<KeyInstance> keyInstanceList = keyInstanceRepository.findAll();
        assertThat(keyInstanceList).hasSize(databaseSizeBeforeUpdate);
        KeyInstance testKeyInstance = keyInstanceList.get(keyInstanceList.size() - 1);
        assertThat(testKeyInstance.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyInstance() throws Exception {
        int databaseSizeBeforeUpdate = keyInstanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyInstanceMockMvc.perform(put("/api/key-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyInstance)))
            .andExpect(status().isBadRequest());

        // Validate the KeyInstance in the database
        List<KeyInstance> keyInstanceList = keyInstanceRepository.findAll();
        assertThat(keyInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKeyInstance() throws Exception {
        // Initialize the database
        keyInstanceService.save(keyInstance);

        int databaseSizeBeforeDelete = keyInstanceRepository.findAll().size();

        // Delete the keyInstance
        restKeyInstanceMockMvc.perform(delete("/api/key-instances/{id}", keyInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyInstance> keyInstanceList = keyInstanceRepository.findAll();
        assertThat(keyInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
