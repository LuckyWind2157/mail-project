package com.fengyun.mail.service.impl;

import com.fengyun.mail.convert.RoleConverter;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.dto.RoleDTO;
import com.fengyun.mail.entity.RoleDo;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.repository.RoleRepository;
import com.fengyun.mail.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class RoleServiceImpl implements RoleService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponsePageDTO<List<RoleDTO>> findByPage(Integer page, Integer size, String sort, RoleDTO roleDTO) {
        Page<RoleDo> pageDo = repository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status").as(String.class), StatusEnum.EFFECTIVE.getCode()));
            if (StringUtils.isNotBlank(roleDTO.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("userName").as(String.class), roleDTO.getName()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page - 1, size, Sort.by("createdTime").descending()));

        ResponsePageDTO<List<RoleDTO>> dto = new ResponsePageDTO<>();
        dto.setCount(pageDo.getTotalElements());
        dto.setData(RoleConverter.INSTANCE.doToDTO(pageDo.getContent()));
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(RoleDTO roleDTO) {
        if (Objects.isNull(roleDTO.getId())) {
            logger.info("新增");
            RoleDo roleDo = RoleConverter.INSTANCE.dtoToDo(roleDTO);
            roleDo.setStatus(StatusEnum.EFFECTIVE.getCode());
            roleDo.setCreatedId(0L);
            roleDo.setUpdatedId(0L);
            repository.save(roleDo);
        } else {
            logger.info("更新");
            Optional<RoleDo> optionalRoleDo = repository.findById(roleDTO.getId());
            if (optionalRoleDo.isEmpty()) {
                logger.error("角色不存在");
                return;
            }
            RoleDo roleDo = optionalRoleDo.get();
            roleDTO.setId(roleDo.getId());
            roleDo.setName(roleDTO.getName());
            roleDo.setRemake(roleDTO.getRemake());
            if (StringUtils.isNotBlank(roleDTO.getStatus())) {
                roleDo.setStatus(roleDTO.getStatus());
            }
            roleDo.setUpdatedId(1L);
            repository.save(roleDo);
        }
    }
}
