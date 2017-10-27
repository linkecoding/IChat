package cn.codekong.common.widget.recycler;

/**
 * Created by 尚振鸿 on 17-10-9. 13:19
 * mail:szh@codekong.cn
 */

public interface AdapetrCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
