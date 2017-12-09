package cn.codekong.ichatclient.activities;

import cn.codekong.common.app.Activity;
import cn.codekong.common.app.Fragment;
import cn.codekong.ichatclient.R;
import cn.codekong.ichatclient.frags.user.UpdateInfoFragment;

public class UserActivity extends Activity {
    private Fragment mCurFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction().add(R.layout.fragment_update_info, mCurFragment);
    }
}
