package cn.codekong.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 尚振鸿 on 17-10-6. 21:57
 * mail:szh@codekong.cn
 */

public abstract class Activity extends AppCompatActivity {
    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面初始化之前初始化窗口
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindows(){

    }
    /**
     * 初始化参数
     * @param bundle 需要初始化的参数
     * @return 如果参数正确返回true,错误返回false
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 获得当前界面的资源文件Id
     * @return 界面的资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        mUnbinder = ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时,finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //获得当前Activity的所有Fragment
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null && fragmentList.size() > 0){
            for (Fragment fragment : fragmentList) {
                //是否是我们自定义的Fragment
                if (fragment instanceof cn.codekong.common.app.Fragment){
                    if (((cn.codekong.common.app.Fragment)fragment).onBackPressed()){
                        //Fragment处理了返回事件
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
