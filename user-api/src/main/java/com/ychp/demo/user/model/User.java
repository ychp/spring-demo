package com.ychp.demo.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* @author yingchengpeng
* @date 2018/08/08
*/
@ToString
@EqualsAndHashCode(of = { "name", "nickName", "mobile", "email", "password", "salt", "status" })
public class User implements Serializable {

    private static final long serialVersionUID = -2796900462003393210L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String nickName;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String salt;

    @Getter
    @Setter
    private Integer status;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}