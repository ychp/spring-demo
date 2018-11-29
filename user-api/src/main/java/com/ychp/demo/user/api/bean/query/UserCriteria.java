package com.ychp.demo.user.api.bean.query;

import com.ychp.demo.common.model.paging.PagingCriteria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
@ToString
public class UserCriteria extends PagingCriteria {

    private static final long serialVersionUID = 8815059999975666033L;

    /**
     * 用户名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 昵称
     */
    @Getter
    @Setter
    private String nickName;

    /**
     * 手机号码
     */
    @Getter
    @Setter
    private String mobile;

    /**
     * 邮箱
     */
    @Getter
    @Setter
    private String email;

    /**
     * 状态
     */
    @Getter
    @Setter
    private String status;

}