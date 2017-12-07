package cn.codekong.ichatserver.app;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

import cn.codekong.ichatserver.provider.AuthRequestFilter;
import cn.codekong.ichatserver.provider.GsonProvider;
import cn.codekong.ichatserver.service.AccountService;

/**
 * Created by 尚振鸿 on 17-10-6. 14:20
 * mail:szh@codekong.cn
 */

public class Application extends ResourceConfig{
    public Application(){

        //账号相关逻辑处理的包名
        packages(AccountService.class.getPackage().getName());

        //注册我们的全局请求拦截器
        register(AuthRequestFilter.class);

        //注册json解析器
        //register(JacksonJsonProvider.class);
        //替换为Gson解析器
        register(GsonProvider.class);
        //注册日志打印
        register(Logger.class);
    }
}
