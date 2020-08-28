package de.idnow.customerportal.three.repository;

import de.idnow.customerportal.three.domain.KeyDefinition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KeyDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyDefinitionRepository extends JpaRepository<KeyDefinition, Long> {
}
