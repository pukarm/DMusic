package com.d.music.mvp.fragment;

import android.content.Context;

import com.d.music.R;
import com.d.music.model.AlbumModel;
import com.d.music.model.FolderModel;
import com.d.music.model.SingerModel;
import com.d.music.module.events.MusicModelEvent;
import com.d.music.module.greendao.db.MusicDB;
import com.d.music.module.greendao.music.base.MusicModel;
import com.d.music.mvp.adapter.SingerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-本地歌曲-歌手
 * Created by D on 2017/4/29.
 */
public class LMSingerFragment extends AbstractLMFragment {
    private Context context;
    private SingerAdapter adapter;
    private List<SingerModel> datas;

    @Override
    protected void init() {
        super.init();
        context = getActivity();
    }

    @Override
    protected void lazyLoad() {
        datas = new ArrayList<>();
        adapter = new SingerAdapter(context, datas, R.layout.adapter_singer);
        xrvList.showAsList();
        xrvList.setCanRefresh(false);
        xrvList.setCanLoadMore(false);
        xrvList.setAdapter(adapter);
        mPresenter.getSinger();
    }

    @Override
    public void setSong(List<MusicModel> models) {

    }

    @Override
    public void setSinger(List<SingerModel> models) {
        adapter.setDatas(models);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setAlbum(List<AlbumModel> models) {

    }

    @Override
    public void setFolder(List<FolderModel> models) {

    }

    @Override
    public void setDSState(int state) {
        dslDS.setState(state);
    }

    @Override
    public void notifyDataCountChanged(int count) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MusicModelEvent event) {
        if (event == null || getActivity() == null || getActivity().isFinishing()
                || event.type != MusicDB.LOCAL_ALL_MUSIC || mPresenter == null || !isLazyLoaded) {
            return;
        }
        mPresenter.getSinger();
    }
}