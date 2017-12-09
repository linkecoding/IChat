package cn.codekong.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import cn.codekong.common.R;
import cn.codekong.common.widget.recycler.RecyclerAdapter;

/**
 * Created by 尚振鸿 on 17-12-9. 17:28
 * mail:szh@codekong.cn
 */

public class GalleyView extends RecyclerView {
    private Adapter mAdapter = new Adapter();

    public GalleyView(Context context) {
        super(context);
        init();
    }

    public GalleyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleyView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListener<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image image) {

            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Image image) {

            }
        });
    }

    private static class Image {

    }

    private class Adapter extends RecyclerAdapter<Image> {

        @Override
        public void update(Image image, ViewHolder<Image> holder) {

        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }
    }

    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image>{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Image image){

        }
    }
}
