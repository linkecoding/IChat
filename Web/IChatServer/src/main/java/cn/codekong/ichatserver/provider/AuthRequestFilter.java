package cn.codekong.ichatserver.provider;

import com.google.common.base.Strings;

import org.glassfish.jersey.server.ContainerRequest;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import cn.codekong.ichatserver.bean.api.base.ResponseModel;
import cn.codekong.ichatserver.bean.db.User;
import cn.codekong.ichatserver.factory.UserFactory;

/**
 * 用于所搜的请求接口的过滤和拦截
 * Created by 尚振鸿 on 17-12-5. 21:34
 * mail:szh@codekong.cn
 */

public class AuthRequestFilter implements ContainerRequestFilter{

    //登录相对路径
    public static final String LOGIN_RELATION_PATH = "account/login";
    public static final String REGISTER_RELATION_PATH = "account/register";

    /**
     * 实现接口的过滤方法
     * @param requestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //检查是否是登录注册接口
        String relationPath = ((ContainerRequest)requestContext).getPath(false);
        if (relationPath.startsWith(LOGIN_RELATION_PATH) ||
                relationPath.startsWith(REGISTER_RELATION_PATH)){
            //直接走正常流程,不拦截
            return;
        }

        //从Headers中找到第一个token节点
        String token = requestContext.getHeaders().getFirst("token");
        if (!Strings.isNullOrEmpty(token)){

            //查询自己的信息
            final User self = UserFactory.findByToken(token);
            if (self != null){
                //给当前请求添加一个上下文
                requestContext.setSecurityContext(new SecurityContext() {

                    //主题部分
                    @Override
                    public Principal getUserPrincipal() {
                        //User实现可Principal接口
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        //可以在这里写入用户的权限,role是权限名
                        //可以管理管理员权限
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        //默认为false, HTTTPS
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
                //写入上下文后就返回
                return;
            }
        }

        //直接返回一个账户需要登录的Model
        ResponseModel model = ResponseModel.buildAccountError();
        //构建一个返回
        Response response = Response.status(Response.Status.OK)
                .entity(model)
                .build();
        //拦截,停止一个请求的继续下文,调用该方法后直接返回请求,不走到Service
        requestContext.abortWith(response);
    }
}
