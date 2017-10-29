package cn.codekong.ichatserver.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 用户关系Model
 * 用于用户之间进行好友关系的实现
 * Created by 尚振鸿 on 17-10-29. 16:07
 * mail:szh@codekong.cn
 */

@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollow {
    //这是一个id主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生产存储的类型是UUID
    @GeneratedValue(generator = "uuid")
    //设置uuid的生成器为uuid2,uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //该列不允许更改,不允许为空
    @Column(updatable = false, nullable = false)
    private String id;

    //定义一个发起人,你关注某人
    //多对一,你可以关注很多人,你的每一条关注都是一条记录
    //你可以创建很多个关注的信息
    //这里的多对一是:User 对应多个 UserFollow
    //optional 不可选,必须存储,一条关注记录一定要有一个关注人
    @ManyToOne(optional = false)
    //定义关联表的字段名为originId,对应User.id
    //定义了数据库中的存储字段
    @JoinColumn(name = "originId")
    private User origin;

    //把这个列提取到我们的Model中,不允许为空,不允许更新和插入
    @Column(nullable = false, updatable = false, insertable = false)
    private String originId;

    //定义关注的目标,你关注的人
    //多对一,你可以别很多人关注,每一次关注都是一天记录
    //多个UserFollow对应一个User的情况
    @ManyToOne(optional = false)
    //定义关联表的字段名为targetId,对应User.id
    @JoinColumn(name = "targetId")
    private User target;

    //把这个列提取到我们的Model中,不允许为空,不允许更新和插入
    @Column(nullable = false, updatable = false, insertable = false)
    private String targetId;

    //对target的备注,可以为null
    @Column
    private String alias;

    //定义为创建时间戳,在创建时已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳,在更新时写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
