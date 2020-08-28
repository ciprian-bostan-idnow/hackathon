package de.idnow.customerportal.three.service.impl;

import de.idnow.customerportal.three.service.ActiveConfigurationService;
import de.idnow.customerportal.three.domain.ActiveConfiguration;
import de.idnow.customerportal.three.repository.ActiveConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ActiveConfiguration}.
 */
@Service
@Transactional
public class ActiveConfigurationServiceImpl implements ActiveConfigurationService {

    private final Logger log = LoggerFactory.getLogger(ActiveConfigurationServiceImpl.class);

    private final ActiveConfigurationRepository activeConfigurationRepository;

    public ActiveConfigurationServiceImpl(ActiveConfigurationRepository activeConfigurationRepository) {
        this.activeConfigurationRepository = activeConfigurationRepository;
    }

    @Override
    public ActiveConfiguration save(ActiveConfiguration activeConfiguration) {
        log.debug("Request to save ActiveConfiguration : {}", activeConfiguration);
        return activeConfigurationRepository.save(activeConfiguration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActiveConfiguration> findAll() {
        log.debug("Request to get all ActiveConfigurations");
        return activeConfigurationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ActiveConfiguration> findOne(Long id) {
        log.debug("Request to get ActiveConfiguration : {}", id);
        return activeConfigurationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActiveConfiguration : {}", id);
        activeConfigurationRepository.deleteById(id);
    }
}
