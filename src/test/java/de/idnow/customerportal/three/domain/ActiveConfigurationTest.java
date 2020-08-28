package de.idnow.customerportal.three.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.idnow.customerportal.three.web.rest.TestUtil;

public class ActiveConfigurationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiveConfiguration.class);
        ActiveConfiguration activeConfiguration1 = new ActiveConfiguration();
        activeConfiguration1.setId(1L);
        ActiveConfiguration activeConfiguration2 = new ActiveConfiguration();
        activeConfiguration2.setId(activeConfiguration1.getId());
        assertThat(activeConfiguration1).isEqualTo(activeConfiguration2);
        activeConfiguration2.setId(2L);
        assertThat(activeConfiguration1).isNotEqualTo(activeConfiguration2);
        activeConfiguration1.setId(null);
        assertThat(activeConfiguration1).isNotEqualTo(activeConfiguration2);
    }
}
