package de.idnow.customerportal.three.service.impl;

import de.idnow.customerportal.three.service.TemplateService;
import de.idnow.customerportal.three.domain.Template;
import de.idnow.customerportal.three.repository.TemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Template}.
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public Template save(Template template) {
        log.debug("Request to save Template : {}", template);
        return templateRepository.save(template);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Template> findAll() {
        log.debug("Request to get all Templates");
        return templateRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Template> findOne(Long id) {
        log.debug("Request to get Template : {}", id);
        return templateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.deleteById(id);
    }
}
