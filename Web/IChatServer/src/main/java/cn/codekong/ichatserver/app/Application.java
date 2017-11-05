package cn.codekong.ichatserver.app;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

import cn.codekong.ichatserver.provider.GsonProvider;

/**
 * Created by 尚振鸿 on 17-10-6. 14:20
 * mail:szh@codekong.cn
 */

public class Application extends ResourceConfig{
    public Application(){

        //注册逻辑处理的包名
        //packages(AccountService.class.getPackage().getName());
        //注册json解析器
        //register(JacksonJsonProvider.class);
        //替换为Gson解析器
        register(GsonProvider.class);
        //注册日志打印
        register(Logger.class);
    }
}
