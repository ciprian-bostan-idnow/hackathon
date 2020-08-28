package de.idnow.customerportal.three.service.impl;

import de.idnow.customerportal.three.service.KeyDefinitionService;
import de.idnow.customerportal.three.domain.KeyDefinition;
import de.idnow.customerportal.three.repository.KeyDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link KeyDefinition}.
 */
@Service
@Transactional
public class KeyDefinitionServiceImpl implements KeyDefinitionService {

    private final Logger log = LoggerFactory.getLogger(KeyDefinitionServiceImpl.class);

    private final KeyDefinitionRepository keyDefinitionRepository;

    public KeyDefinitionServiceImpl(KeyDefinitionRepository keyDefinitionRepository) {
        this.keyDefinitionRepository = keyDefinitionRepository;
    }

    @Override
    public KeyDefinition save(KeyDefinition keyDefinition) {
        log.debug("Request to save KeyDefinition : {}", keyDefinition);
        return keyDefinitionRepository.save(keyDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KeyDefinition> findAll(Pageable pageable) {
        log.debug("Request to get all KeyDefinitions");
        return keyDefinitionRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<KeyDefinition> findOne(Long id) {
        log.debug("Request to get KeyDefinition : {}", id);
        return keyDefinitionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeyDefinition : {}", id);
        keyDefinitionRepository.deleteById(id);
    }
}
