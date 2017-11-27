package cn.codekong.ichatserver.factory;

import org.hibernate.Session;

import cn.codekong.ichatserver.bean.db.User;
import cn.codekong.ichatserver.utils.Hib;
import cn.codekong.ichatserver.utils.TextUtil;

/**
 * Created by 尚振鸿 on 17-11-27. 19:44
 * mail:szh@codekong.cn
 */

public class UserFactory {
    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());
    }

    public static User findByName(String name) {
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    /**
     * 用户注册
     * 将注册信息写入数据库,并返回数据库中的User信息
     *
     * @param account  账号
     * @param password 密码
     * @param name     用户名
     * @return User
     */
    public static User register(String account, String password, String name) {
        account = account.trim();
        //处理密码
        password = encodePassword(password);

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account);

        //进行数据库存储
        Session session = Hib.session();
        session.beginTransaction();
        try {
            //保存
            session.save(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    /**
     * 对密码进行MD5加密之后进行Base64处理
     *
     * @param password
     * @return
     */
    private static String encodePassword(String password) {
        password = password.trim();
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }
}
