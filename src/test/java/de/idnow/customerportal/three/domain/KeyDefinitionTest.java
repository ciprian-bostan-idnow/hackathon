package de.idnow.customerportal.three.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.idnow.customerportal.three.web.rest.TestUtil;

public class KeyDefinitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyDefinition.class);
        KeyDefinition keyDefinition1 = new KeyDefinition();
        keyDefinition1.setId(1L);
        KeyDefinition keyDefinition2 = new KeyDefinition();
        keyDefinition2.setId(keyDefinition1.getId());
        assertThat(keyDefinition1).isEqualTo(keyDefinition2);
        keyDefinition2.setId(2L);
        assertThat(keyDefinition1).isNotEqualTo(keyDefinition2);
        keyDefinition1.setId(null);
        assertThat(keyDefinition1).isNotEqualTo(keyDefinition2);
    }
}
