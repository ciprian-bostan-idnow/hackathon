package de.idnow.customerportal.three.repository;

import de.idnow.customerportal.three.domain.KeyInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KeyInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyInstanceRepository extends JpaRepository<KeyInstance, Long> {
}
