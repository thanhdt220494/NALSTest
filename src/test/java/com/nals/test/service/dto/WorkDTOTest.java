package com.nals.test.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nals.test.web.rest.TestUtil;

public class WorkDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDTO.class);
        WorkDTO workDTO1 = new WorkDTO();
        workDTO1.setId(1L);
        WorkDTO workDTO2 = new WorkDTO();
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO2.setId(workDTO1.getId());
        assertThat(workDTO1).isEqualTo(workDTO2);
        workDTO2.setId(2L);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO1.setId(null);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
    }
}
