package cn.codekong.ichatserver.service;

import com.google.common.base.Strings;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.codekong.ichatserver.bean.api.base.ResponseModel;
import cn.codekong.ichatserver.bean.api.user.UpdateInfoModel;
import cn.codekong.ichatserver.bean.card.UserCard;
import cn.codekong.ichatserver.bean.db.User;
import cn.codekong.ichatserver.factory.UserFactory;

/**
 * 用户信息处理的Service
 * Created by 尚振鸿 on 17-12-5. 20:58
 * mail:szh@codekong.cn
 */

//127.0.0.1/api/user/..
@Path("/user")
public class UserService {

    /**
     * 修改用户信息
     * 此处不写@Path(),就是当前目录
     * @param token
     * @param model
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(@HeaderParam("token") String token,
                                          UpdateInfoModel model) {
        if (Strings.isNullOrEmpty(token) || !UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        //拿到自己的个人信息
        User user = UserFactory.findByToken(token);
        if (user != null) {
            //更新用户信息
            user = model.updateToUser(user);
            user = UserFactory.update(user);

            //架构自己的用户信息
            UserCard card = new UserCard(user, true);
            return ResponseModel.buildOk(card);
        } else {
            //token无效
            return ResponseModel.buildAccountError();
        }

    }
}
