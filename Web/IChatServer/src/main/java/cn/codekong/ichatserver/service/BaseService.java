package cn.codekong.ichatserver.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import cn.codekong.ichatserver.bean.db.User;

/**
 * Created by 尚振鸿 on 17-12-7. 09:28
 * mail:szh@codekong.cn
 */

public class BaseService {

    //添加一个上下文注解,该注解会给mSecurityContext赋值
    //具体的值为我们的拦截器中所返回的SecurityContext
    @Context
    protected SecurityContext mSecurityContext;

    /**
     * 从上下文中直接获取自己的信息
     * @return
     */
    protected User getSelf(){
        return (User) mSecurityContext.getUserPrincipal();
    }
}
