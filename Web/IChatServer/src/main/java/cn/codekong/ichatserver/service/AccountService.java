package cn.codekong.ichatserver.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.codekong.ichatserver.bean.User;

/**
 * Created by 尚振鸿 on 17-10-6. 14:29
 * mail:szh@codekong.cn
 */
@Path("/account")
public class AccountService {

    @GET
    @Path("/login")
    public String login(){
        return "You get the login.";
    }

    @POST
    @Path("getinfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User getInfo(){
        User user = new User();
        user.setPassword("123");
        user.setUsername("xiaohong");
        return user;
    }
}
