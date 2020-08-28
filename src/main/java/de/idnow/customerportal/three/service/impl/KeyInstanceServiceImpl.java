package de.idnow.customerportal.three.service.impl;

import de.idnow.customerportal.three.service.KeyInstanceService;
import de.idnow.customerportal.three.domain.KeyInstance;
import de.idnow.customerportal.three.repository.KeyInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link KeyInstance}.
 */
@Service
@Transactional
public class KeyInstanceServiceImpl implements KeyInstanceService {

    private final Logger log = LoggerFactory.getLogger(KeyInstanceServiceImpl.class);

    private final KeyInstanceRepository keyInstanceRepository;

    public KeyInstanceServiceImpl(KeyInstanceRepository keyInstanceRepository) {
        this.keyInstanceRepository = keyInstanceRepository;
    }

    @Override
    public KeyInstance save(KeyInstance keyInstance) {
        log.debug("Request to save KeyInstance : {}", keyInstance);
        return keyInstanceRepository.save(keyInstance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeyInstance> findAll() {
        log.debug("Request to get all KeyInstances");
        return keyInstanceRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<KeyInstance> findOne(Long id) {
        log.debug("Request to get KeyInstance : {}", id);
        return keyInstanceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeyInstance : {}", id);
        keyInstanceRepository.deleteById(id);
    }
}
