package com.d.music.mvp.activity;

import android.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d.commen.base.BaseActivity;
import com.d.commen.mvp.MvpBasePresenter;
import com.d.commen.mvp.MvpView;
import com.d.music.R;
import com.d.music.commen.AlertDialogFactory;
import com.d.music.commen.Preferences;
import com.d.music.module.global.MusicCst;
import com.d.music.module.repeatclick.ClickUtil;
import com.d.music.module.skin.SkinUtil;
import com.d.music.mvp.adapter.SkinAdapter;
import com.d.music.mvp.model.RadioModel;
import com.d.music.utils.StatusBarCompat;
import com.d.music.utils.Util;
import com.d.music.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.loader.SkinManager;

/**
 * SkinActivity
 * Created by D on 2017/6/13.
 */
public class SkinActivity extends BaseActivity<MvpBasePresenter> implements MvpView {
    @Bind(R.id.rv_list)
    RecyclerView rvList;

    private SkinAdapter adapter;
    private int index;
    private AlertDialog dialog;

    @OnClick({R.id.iv_title_left, R.id.tv_title_right})
    public void onClickListener(View v) {
        if (ClickUtil.isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_title_right:
                index = adapter.getIndex();
                SkinUtil.load(getApplicationContext(), index, new ILoaderListener() {
                    @Override
                    public void onStart() {
                        showLoading();
                    }

                    @Override
                    public void onSuccess() {
                        closeLoading();
                        finish();
                    }

                    @Override
                    public void onFailed() {
                        closeLoading();
                        Util.toast(SkinActivity.this, "╯﹏╰很抱歉，换肤失败了");
                    }
                });
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_skin;
    }

    @Override
    public MvpBasePresenter getPresenter() {
        return new MvpBasePresenter(getApplicationContext());
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    protected void init() {
        StatusBarCompat.compat(this, SkinManager.getInstance().getColor(R.color.color_main));//沉浸式状态栏
        index = Preferences.getInstance(getApplicationContext()).getSkin();
        adapter = new SkinAdapter(this, getDatas(index), R.layout.adapter_skin);
        adapter.setIndex(index);
        rvList.setLayoutManager(new GridLayoutManager(this, 3));
        rvList.addItemDecoration(new SpaceItemDecoration(Util.dip2px(this, 6)));
        rvList.setAdapter(adapter);
    }

    private void showLoading() {
        if (dialog == null) {
            dialog = AlertDialogFactory.createFactory(this).getLoadingDialog("正在换肤>>");
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void closeLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private List<RadioModel> getDatas(int index) {
        List<RadioModel> datas = new ArrayList<>();
        for (int i = -1; i < MusicCst.SKIN_COUNT; i++) {
            RadioModel model = new RadioModel();
            model.color = getC(i);
            model.isChecked = (i == index);
            datas.add(model);
        }
        return datas;
    }

    private int getC(int index) {
        switch (index) {
            case 0:
                return R.color.color_main_skin0;
            case 1:
                return R.color.color_main_skin1;
            case 2:
                return R.color.color_main_skin2;
            case 3:
                return R.color.color_main_skin3;
            case 4:
                return R.color.color_main_skin4;
            case 5:
                return R.color.color_main_skin5;
            case 6:
                return R.color.color_main_skin6;
            case 7:
                return R.color.color_main_skin7;
            case 8:
                return R.color.color_main_skin8;
            case 9:
                return R.color.color_main_skin9;
            case 10:
                return R.color.color_main_skin10;
            case 11:
                return R.color.color_main_skin11;
            case 12:
                return R.color.color_main_skin12;
            case 13:
                return R.color.color_main_skin13;
            case 14:
                return R.color.color_main_skin14;
            case 15:
                return R.color.color_main_skin15;
            case 16:
                return R.color.color_main_skin16;
            case 17:
                return R.color.color_main_skin17;
            default:
                return R.color.color_main_skin;
        }
    }

    @Override
    public void onThemeUpdate() {
        super.onThemeUpdate();
        StatusBarCompat.compat(this, SkinManager.getInstance().getColor(R.color.color_main));//沉浸式状态栏
    }
}
