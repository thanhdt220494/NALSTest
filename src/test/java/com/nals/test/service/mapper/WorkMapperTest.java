package com.nals.test.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WorkMapperTest {

    private WorkMapper workMapper;

    @BeforeEach
    public void setUp() {
        workMapper = new WorkMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(workMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workMapper.fromId(null)).isNull();
    }
}
