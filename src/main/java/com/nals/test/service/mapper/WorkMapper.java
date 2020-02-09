package com.nals.test.service.mapper;

import com.nals.test.domain.*;
import com.nals.test.service.dto.WorkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Work} and its DTO {@link WorkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkMapper extends EntityMapper<WorkDTO, Work> {



    default Work fromId(Long id) {
        if (id == null) {
            return null;
        }
        Work work = new Work();
        work.setId(id);
        return work;
    }
}
