package com.haodong.veerdemo.activities;


import android.content.Intent;
import com.haodong.veerdemo.R;
import com.haodong.veerdemo.fragments.PermissionsFragment;

public class LaunchActivity extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsFragment.haveAll(this,getSupportFragmentManager())){
            startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            finish();
        }
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;

    }
}
