package de.idnow.customerportal.three.repository;

import de.idnow.customerportal.three.domain.ActiveConfiguration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActiveConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiveConfigurationRepository extends JpaRepository<ActiveConfiguration, Long> {
}
