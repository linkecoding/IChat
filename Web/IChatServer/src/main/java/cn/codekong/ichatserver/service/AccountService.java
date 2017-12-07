package cn.codekong.ichatserver.service;

import com.google.common.base.Strings;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.codekong.ichatserver.bean.api.account.AccountRspModel;
import cn.codekong.ichatserver.bean.api.account.LoginModel;
import cn.codekong.ichatserver.bean.api.account.RegisterModel;
import cn.codekong.ichatserver.bean.api.base.ResponseModel;
import cn.codekong.ichatserver.bean.db.User;
import cn.codekong.ichatserver.factory.UserFactory;

/**
 * Created by 尚振鸿 on 17-11-27. 19:25
 * mail:szh@codekong.cn
 */

@Path("/account")
public class AccountService extends BaseService {

    /**
     * 注册
     *
     * @param model
     * @return
     */
    @POST
    @Path("/register")
    //请求与返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        if (!RegisterModel.check(model)) {
            //参数异常
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            //账户已存在
            return ResponseModel.buildHaveAccountError();
        }

        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            //已有用户名
            return ResponseModel.buildHaveNameError();
        }

        //开始注册逻辑
        user = UserFactory.register(model.getAccount(),
                model.getPassword(),
                model.getName());

        if (user != null) {
            //如果有携带pushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            return ResponseModel.buildRegisterError();
        }
    }

    /**
     * 登录
     *
     * @param model
     * @return
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            //参数错误
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }

            //返回当前账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            //登录失败
            return ResponseModel.buildLoginError();
        }
    }


    /**
     * 绑定设备Id
     *
     * @param token
     * @param pushId
     * @return
     */
    @POST
    @Path("/bind/{pushId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //从请求头中获取token字段
    //pushId从url中获取
    public ResponseModel<AccountRspModel> bind(@HeaderParam("token") String token,
                                               @PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(token) ||
                Strings.isNullOrEmpty(pushId)) {
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        // 拿到自己的个人信息
        // User user = UserFactory.findByToken(token);
        // if (user != null) {
        //     return bind(user, pushId);
        // } else {
        //     //token无效,无法绑定
        //     return ResponseModel.buildAccountError();
        // }
        User self = getSelf();
        return bind(self, pushId);
    }


    /**
     * 绑定pushId操作
     *
     * @param self   自己
     * @param pushId 设备Id
     * @return User
     */
    private ResponseModel<AccountRspModel> bind(User self, String pushId) {
        //进行pushId绑定操作
        User user = UserFactory.bindPushId(self, pushId);
        if (user == null) {
            //如果绑定失败,则是服务器异常
            return ResponseModel.buildServiceError();
        }

        //返回当前的账户,并且已经绑定成功
        AccountRspModel rspModel = new AccountRspModel(user, true);
        return ResponseModel.buildOk(rspModel);
    }

}
