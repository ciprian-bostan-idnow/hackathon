package de.idnow.customerportal.three.web.rest;

import de.idnow.customerportal.three.customerportalhackathonApp;
import de.idnow.customerportal.three.domain.KeyDefinition;
import de.idnow.customerportal.three.repository.KeyDefinitionRepository;
import de.idnow.customerportal.three.service.KeyDefinitionService;

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

import de.idnow.customerportal.three.domain.enumeration.KeyType;
/**
 * Integration tests for the {@link KeyDefinitionResource} REST controller.
 */
@SpringBootTest(classes = customerportalhackathonApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KeyDefinitionResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    private static final KeyType DEFAULT_TYPE = KeyType.STRING;
    private static final KeyType UPDATED_TYPE = KeyType.BOOLEAN;

    @Autowired
    private KeyDefinitionRepository keyDefinitionRepository;

    @Autowired
    private KeyDefinitionService keyDefinitionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeyDefinitionMockMvc;

    private KeyDefinition keyDefinition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyDefinition createEntity(EntityManager em) {
        KeyDefinition keyDefinition = new KeyDefinition()
            .key(DEFAULT_KEY)
            .description(DEFAULT_DESCRIPTION)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .isRequired(DEFAULT_IS_REQUIRED)
            .type(DEFAULT_TYPE);
        return keyDefinition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyDefinition createUpdatedEntity(EntityManager em) {
        KeyDefinition keyDefinition = new KeyDefinition()
            .key(UPDATED_KEY)
            .description(UPDATED_DESCRIPTION)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .isRequired(UPDATED_IS_REQUIRED)
            .type(UPDATED_TYPE);
        return keyDefinition;
    }

    @BeforeEach
    public void initTest() {
        keyDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyDefinition() throws Exception {
        int databaseSizeBeforeCreate = keyDefinitionRepository.findAll().size();
        // Create the KeyDefinition
        restKeyDefinitionMockMvc.perform(post("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDefinition)))
            .andExpect(status().isCreated());

        // Validate the KeyDefinition in the database
        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        KeyDefinition testKeyDefinition = keyDefinitionList.get(keyDefinitionList.size() - 1);
        assertThat(testKeyDefinition.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testKeyDefinition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKeyDefinition.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testKeyDefinition.isIsRequired()).isEqualTo(DEFAULT_IS_REQUIRED);
        assertThat(testKeyDefinition.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createKeyDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyDefinitionRepository.findAll().size();

        // Create the KeyDefinition with an existing ID
        keyDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyDefinitionMockMvc.perform(post("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the KeyDefinition in the database
        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyDefinitionRepository.findAll().size();
        // set the field null
        keyDefinition.setKey(null);

        // Create the KeyDefinition, which fails.


        restKeyDefinitionMockMvc.perform(post("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDefinition)))
            .andExpect(status().isBadRequest());

        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyDefinitionRepository.findAll().size();
        // set the field null
        keyDefinition.setDescription(null);

        // Create the KeyDefinition, which fails.


        restKeyDefinitionMockMvc.perform(post("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDefinition)))
            .andExpect(status().isBadRequest());

        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyDefinitionRepository.findAll().size();
        // set the field null
        keyDefinition.setType(null);

        // Create the KeyDefinition, which fails.


        restKeyDefinitionMockMvc.perform(post("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDefinition)))
            .andExpect(status().isBadRequest());

        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKeyDefinitions() throws Exception {
        // Initialize the database
        keyDefinitionRepository.saveAndFlush(keyDefinition);

        // Get all the keyDefinitionList
        restKeyDefinitionMockMvc.perform(get("/api/key-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getKeyDefinition() throws Exception {
        // Initialize the database
        keyDefinitionRepository.saveAndFlush(keyDefinition);

        // Get the keyDefinition
        restKeyDefinitionMockMvc.perform(get("/api/key-definitions/{id}", keyDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keyDefinition.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingKeyDefinition() throws Exception {
        // Get the keyDefinition
        restKeyDefinitionMockMvc.perform(get("/api/key-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyDefinition() throws Exception {
        // Initialize the database
        keyDefinitionService.save(keyDefinition);

        int databaseSizeBeforeUpdate = keyDefinitionRepository.findAll().size();

        // Update the keyDefinition
        KeyDefinition updatedKeyDefinition = keyDefinitionRepository.findById(keyDefinition.getId()).get();
        // Disconnect from session so that the updates on updatedKeyDefinition are not directly saved in db
        em.detach(updatedKeyDefinition);
        updatedKeyDefinition
            .key(UPDATED_KEY)
            .description(UPDATED_DESCRIPTION)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .isRequired(UPDATED_IS_REQUIRED)
            .type(UPDATED_TYPE);

        restKeyDefinitionMockMvc.perform(put("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKeyDefinition)))
            .andExpect(status().isOk());

        // Validate the KeyDefinition in the database
        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeUpdate);
        KeyDefinition testKeyDefinition = keyDefinitionList.get(keyDefinitionList.size() - 1);
        assertThat(testKeyDefinition.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testKeyDefinition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKeyDefinition.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testKeyDefinition.isIsRequired()).isEqualTo(UPDATED_IS_REQUIRED);
        assertThat(testKeyDefinition.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyDefinition() throws Exception {
        int databaseSizeBeforeUpdate = keyDefinitionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyDefinitionMockMvc.perform(put("/api/key-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the KeyDefinition in the database
        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKeyDefinition() throws Exception {
        // Initialize the database
        keyDefinitionService.save(keyDefinition);

        int databaseSizeBeforeDelete = keyDefinitionRepository.findAll().size();

        // Delete the keyDefinition
        restKeyDefinitionMockMvc.perform(delete("/api/key-definitions/{id}", keyDefinition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyDefinition> keyDefinitionList = keyDefinitionRepository.findAll();
        assertThat(keyDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
