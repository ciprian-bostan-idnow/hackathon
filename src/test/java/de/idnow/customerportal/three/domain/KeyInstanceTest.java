package de.idnow.customerportal.three.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.idnow.customerportal.three.web.rest.TestUtil;

public class KeyInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyInstance.class);
        KeyInstance keyInstance1 = new KeyInstance();
        keyInstance1.setId(1L);
        KeyInstance keyInstance2 = new KeyInstance();
        keyInstance2.setId(keyInstance1.getId());
        assertThat(keyInstance1).isEqualTo(keyInstance2);
        keyInstance2.setId(2L);
        assertThat(keyInstance1).isNotEqualTo(keyInstance2);
        keyInstance1.setId(null);
        assertThat(keyInstance1).isNotEqualTo(keyInstance2);
    }
}
