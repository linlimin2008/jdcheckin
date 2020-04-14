package com.zerocm.jdcheckin.pojo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Cookie实体类
 */
@Data
public class CookieEntity implements Serializable {
    private Integer id;
    private String jdUsername;
    private String jdCookie;
    private String remark;
    private String field1;
    private String field2;
    private String field3;
}
