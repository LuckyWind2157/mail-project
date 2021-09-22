package com.fengyun.mail.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fengyun.mail.convert.MenuConverter;
import com.fengyun.mail.dto.MenuDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.entity.MenuDo;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.repository.MenuRepository;
import com.fengyun.mail.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public ResponsePageDTO<List<MenuDTO>> findByPage(Integer page, Integer size, String sort, MenuDTO menuDTO) {
        Page<MenuDo> pageDo = menuRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status").as(String.class), StatusEnum.EFFECTIVE.getCode()));
            if (StringUtils.isNotBlank(menuDTO.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), menuDTO.getName()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page-1, size, Sort.by("createdTime").descending()));

        ResponsePageDTO<List<MenuDTO>> dto = new ResponsePageDTO<>();
        dto.setCount(pageDo.getTotalElements());
        dto.setData(MenuConverter.INSTANCE.doToMenuDTO(pageDo.getContent()));
        return dto;

    }

    @Override
    public void saveOrUpdate(MenuDTO menuDTO) {
        if (Objects.isNull(menuDTO.getId())) {
            logger.info("菜单新增");
            MenuDo menuDo = MenuConverter.INSTANCE.dtoToMenuDo(menuDTO);
           // menuDo.setCreatedTime(DateUtil.date());
            //menuDo.setUpdatedTime(DateUtil.date());
            menuRepository.save(menuDo);
        } else {
            logger.info("菜单更新");
            Optional<MenuDo> OptionalMenuDo = menuRepository.findById(menuDTO.getId());
            if (OptionalMenuDo.isPresent()) {
                logger.error("更新的菜单不存在");
                return;
            }
            MenuDo menuDo = OptionalMenuDo.get();
            menuDo.setName(menuDTO.getName());
            menuDo.setCode(menuDTO.getCode());
            menuDo.setRemake(menuDTO.getRemake());
            menuDo.setParentCode(menuDTO.getParentCode());
            menuDo.setStatus(menuDTO.getStatus());
            menuDo.setUpdatedTime(DateUtil.date());
            menuRepository.save(menuDo);
        }

    }

//    @Override
//    public MenuDTO findById(long id) {
//        MenuDo menuDo = menuRepository.findById(id).orElseGet(MenuDo::new);
//        return MenuConverter.INSTANCE.doToMenuDTO(menuDo);
//    }
//
//    @Override
//    public List<MenuDTO> findAllByStatus(String status) {
//        List<MenuDo> list = menuRepository.findAllByStatus(status);
//        if (CollectionUtil.isEmpty(list)) {
//            return CollectionUtil.newArrayList();
//        }
//        return MenuConverter.INSTANCE.doToMenuDTO(list);
//    }
//
//    @Override
//    public List<MenuDTO> findAllByName(String name) {
//        List<MenuDo> list = menuRepository.findAllByNameAndStatus(name, StatusEnum.EFFECTIVE.getCode());
//        return MenuConverter.INSTANCE.doToMenuDTO(list);
//    }
}
