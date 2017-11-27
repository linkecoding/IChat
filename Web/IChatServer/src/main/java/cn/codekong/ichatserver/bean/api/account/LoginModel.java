package cn.codekong.ichatserver.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * Created by 尚振鸿 on 17-11-27. 21:34
 * mail:szh@codekong.cn
 */

public class LoginModel {

    @Expose
    private String account;

    @Expose
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 非空检验
     * @param model
     * @return
     */
    public static boolean check(LoginModel model){
        return model != null
                && !Strings.isNullOrEmpty(model.account)
                && !Strings.isNullOrEmpty(model.password);
    }
}
