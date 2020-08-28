package de.idnow.customerportal.three.repository;

import de.idnow.customerportal.three.domain.Template;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Template entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
}
