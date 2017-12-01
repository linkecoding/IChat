package cn.codekong.ichatserver.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
public class AccountService {

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
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            return ResponseModel.buildRegisterError();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
       if (!LoginModel.check(model)){
           //参数错误
           return ResponseModel.buildParameterError();
       }

       User user = UserFactory.login(model.getAccount(), model.getPassword());
       if (user != null){
           //返回当前账户
           AccountRspModel rspModel = new AccountRspModel(user);
           return ResponseModel.buildOk(rspModel);
       }else{
           //登录失败
           return ResponseModel.buildLoginError();
       }
    }
}
