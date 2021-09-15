package com.fengyun.mail.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuTreeDTO {

    private String id;

    private String title;

    private boolean checked = false;

    private List<MenuTreeDTO> children;


}
