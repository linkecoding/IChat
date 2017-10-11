
package cn.codekong.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.codekong.common.R;

/**
 * Created by 尚振鸿 on 17-10-9. 13:23
 * mail:szh@codekong.cn
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapetrCallback<Data> {
    private final List<Data> mDataList;
    private AdapterListener<Data> mAdapterListener;

    /**
     * 构造函数
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mAdapterListener = listener;
    }


    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型,约定为xml布局的Id
     * @return
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        //将xml布局初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        //设置View的Tag将View和Holder进行关联
        root.setTag(R.id.tag_recycler_holder, holder);
        //设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        //进行界面注解绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.mCallback = this;
        return holder;
    }


    /**
     * 获得一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型,其实就是xml的Id
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 复写默认的布局类型返回
     *
     * @param position 坐标
     * @return 重写后返回的是xml文件的Id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param position 坐标
     * @param data     当前的数据
     * @return xml文件的Id, 用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 绑定数据到Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //获得需要绑定的数据
        Data data = mDataList.get(position);
        //触发绑定方法
        holder.bind(data);
    }

    /**
     * 得到当前集合的数据量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知插入更新
     *
     * @param data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入多条数据,并通知这一段集合更新
     *
     * @param dataList
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入多条数据,并通知这一段集合更新
     *
     * @param dataList
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合,其中包括清空
     *
     * @param dataList
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mAdapterListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            mAdapterListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mAdapterListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            mAdapterListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置监听器
     *
     * @param adapterListener
     */
    public void setAdapterListener(AdapterListener<Data> adapterListener) {
        this.mAdapterListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data> {
        //当Item点击时出发
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        //当Item长按时触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * 自定义的ViewHolder
     *
     * @param <Data> 泛型类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder mUnbinder;
        private AdapetrCallback<Data> mCallback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据
         *
         * @param data 需要绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当数据绑定时进行回调,必须复写
         *
         * @param data 需要绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * holder自己对自己的Data进行更新
         *
         * @param data 需要更新的数据
         */
        public void updateData(Data data) {
            if (mCallback != null) {
                mCallback.update(data, this);
            }
        }
    }
}
