package com.fengyun.mail.service.impl;

import com.fengyun.mail.convert.UserConverter;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.dto.UserDTO;
import com.fengyun.mail.entity.UserDo;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.repository.UserRepository;
import com.fengyun.mail.service.UserService;
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
public class UserServiceImpl implements UserService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDTO findById(Long id) {
        Optional<UserDo> userOptional = userRepository.findById(id);
        UserDo user = userOptional.orElseGet(UserDo::new);
        return UserConverter.INSTANCE.doToDTO(user);
    }

    @Override
    public ResponsePageDTO<List<UserDTO>> findByPage(Integer page, Integer size, String sort, UserDTO userDTO) {
        Page<UserDo> pageDo = userRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status").as(String.class), StatusEnum.EFFECTIVE.getCode()));
            if (StringUtils.isNotBlank(userDTO.getUserName())) {
                predicates.add(criteriaBuilder.equal(root.get("userName").as(String.class), userDTO.getUserName()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page - 1, size, Sort.by("createdTime").descending()));

        ResponsePageDTO<List<UserDTO>> dto = new ResponsePageDTO<>();
        dto.setCount(pageDo.getTotalElements());
        dto.setData(UserConverter.INSTANCE.doToDTO(pageDo.getContent()));
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(UserDTO userDTO) {
        if (Objects.isNull(userDTO.getId())) {
            logger.info("新增");
            UserDo userDo = UserConverter.INSTANCE.dtoToDo(userDTO);
            userDo.setStatus(StatusEnum.EFFECTIVE.getCode());
            userDo.setCreatedId(0L);
            userDo.setUpdatedId(0L);
            userRepository.save(userDo);
        } else {
            logger.info("更新");
            Optional<UserDo> optionalUserDo = userRepository.findById(userDTO.getId());
            if (optionalUserDo.isEmpty()) {
                logger.error("用户不存在");
                return;
            }
            UserDo userDo = optionalUserDo.get();
            userDo.setUserName(userDTO.getUserName());
            userDo.setPassword(userDTO.getPassword());
            userDo.setAge(userDTO.getAge());
            userDo.setPhone(userDTO.getPhone());
            userDo.setSex(userDTO.getSex());
            if (StringUtils.isNotBlank(userDTO.getStatus())) {
                userDo.setStatus(userDTO.getStatus());
            }
            userDo.setUpdatedId(1L);
            userRepository.save(userDo);
        }
    }
}
