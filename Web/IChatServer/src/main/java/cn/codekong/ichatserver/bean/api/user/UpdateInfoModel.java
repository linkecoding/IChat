package cn.codekong.ichatserver.bean.api.user;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

import cn.codekong.ichatserver.bean.db.User;

/**
 * Created by 尚振鸿 on 17-12-5. 17:19
 * mail:szh@codekong.cn
 */

public class UpdateInfoModel {
    @Expose
    private String name;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public User updateToUser(User user){
        if (!Strings.isNullOrEmpty(name)){
            user.setName(name);
        }

        if (!Strings.isNullOrEmpty(portrait)){
            user.setPortrait(portrait);
        }

        if (!Strings.isNullOrEmpty(desc)){
            user.setDescription(desc);
        }

        if (sex != 0){
            user.setSex(sex);
        }
        return user;
    }

    /**
     * 参数非空检查
     * model不能为null,并且至少一个参数不能为空
     * @param model
     * @return
     */
    public static boolean check(UpdateInfoModel model){
        return model != null
                && (!Strings.isNullOrEmpty(model.name) ||
                !Strings.isNullOrEmpty(model.portrait) ||
                !Strings.isNullOrEmpty(model.desc) ||
                model.sex != 0);
    }
}
