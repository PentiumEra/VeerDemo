package com.haodong.veerdemo.fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haodong.veerdemo.R;
import com.haodong.veerdemo.ui.TransStatusBottomSheetDialog;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author LinghaoDo
 * 权限申请弹出框
 */
public class PermissionsFragment extends BottomSheetDialogFragment
        implements EasyPermissions.PermissionCallbacks {


    // 权限回调的标示
    private static final int RC = 0x0100;

    public PermissionsFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 复用即可
        return new TransStatusBottomSheetDialog(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取布局中的控件
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);

        // 找到按钮
        root.findViewById(R.id.btn_submit_permission)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 点击时进行申请权限
                        requestPerm();
                    }
                });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 界面显示的时候进行刷新
        refreshState(getView());
    }

    /**
     * 刷新布局中的（已有权限）标记
     *
     * @param root
     */
    private void refreshState(View root) {
        if (root==null){
            return;
        }
        Context context = getContext();
        root.findViewById(R.id.iv_state_permission_network).setVisibility(haveNework(context) ? View.VISIBLE : View.GONE);
    }

    /**
     * 检查网络权限
     *
     * @param context
     * @return
     */
    private static boolean haveNework(Context context) {
        String[] perms = new String[]{Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_WIFI_STATE};
        return EasyPermissions.hasPermissions(context, perms);
    }


    /**
     * 检查是否具有所有的权限
     *
     * @param context Context
     * @param manager FragmentManager
     * @return 是否有权限
     */
    public static boolean haveAll(Context context, FragmentManager manager) {
        // 检查是否具有所有的权限
        boolean haveAll = haveNework(context);
        // 如果没有则显示当前申请权限的界面
        if (!haveAll) {
            show(manager);
        }

        return haveAll;
    }

    // 私有的show方法
    private static void show(FragmentManager manager) {
        // 调用BottomSheetDialogFragment以及准备好的显示方法
        new PermissionsFragment()
                .show(manager, PermissionsFragment.class.getName());
    }

    /**
     * 申请权限的方法
     */
    @AfterPermissionGranted(RC)
    private void requestPerm() {
        String[] perms = new String[]{Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_WIFI_STATE
                };
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Fragment 中 通过调用getView得到跟布局；前提是在onCreateView方法之后
            refreshState(getView());
        } else {
            EasyPermissions.requestPermissions(this,getString(R.string.title_assist_permissions),RC,perms);
        }

    }


    /**权限申请成功
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    /**
     * 权限申请失败
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // 如果权限有没有申请成功的权限存在，则弹出弹出框，用户点击后去到设置界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    /**
     * 权限申请回调，并将申请后的权限交给easypermission框架
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
