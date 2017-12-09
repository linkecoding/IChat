package cn.codekong.ichatclient.activities;

import android.content.Context;
import android.content.Intent;

import cn.codekong.common.app.Activity;
import cn.codekong.common.app.Fragment;
import cn.codekong.ichatclient.R;
import cn.codekong.ichatclient.frags.account.AccountTrigger;
import cn.codekong.ichatclient.frags.account.LoginFragment;
import cn.codekong.ichatclient.frags.account.RegisterFragment;

public class AccountActivity extends Activity implements AccountTrigger {
    private Fragment mCurFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    /**
     * 显示账户Activity的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurFragment = mLoginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    @Override
    public void triggerView() {
        Fragment fragment;
        if (mCurFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                //默认第一次初始化为空
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            //因为默认请求下mLoginFragment已经赋值,此处无需判断null
            fragment = mLoginFragment;
        }
        mCurFragment = fragment;
        //切换页面
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }
}
