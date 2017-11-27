package cn.codekong.ichatserver.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.codekong.ichatserver.bean.api.account.RegisterModel;
import cn.codekong.ichatserver.bean.card.UserCard;
import cn.codekong.ichatserver.bean.db.User;
import cn.codekong.ichatserver.factory.UserFactory;

/**
 * Created by 尚振鸿 on 17-11-27. 19:25
 * mail:szh@codekong.cn
 */

@Path("/account")
public class AccountService {

    @POST
    @Path("/register")
    //请求与返回的响应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserCard register(RegisterModel model) {
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            UserCard card = new UserCard();
            card.setName("已有了Phone");
            return card;
        }

        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            UserCard card = new UserCard();
            card.setName("已有了Name");
            return card;
        }

        user = UserFactory.register(model.getAccount(), model.getPassword(), model.getName());

        if (user != null){
            UserCard card = new UserCard();
            card.setName(user.getName());
            card.setPhone(user.getPhone());
            card.setSex(user.getSex());
            card.setFollow(true);
            card.setModifyAt(user.getUpdateAt());
            return card;
        }
        return null;
    }
}
