package application.app.custompopupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by lwz on 2018/3/5.
 */

public class RankingPopupWindow extends PopupWindow {
    /**
     * test
     */
    private ListPopupWindowAdapter mListPopupWindowAdapter;
    private Listener mListener;

    public RankingPopupWindow(Activity context, List<String> list, String selectName, Listener listener, View view) {
        super(context);
        mListener = listener;
        //拿到基本的item高度，这里给定每个Item的高度和宽度
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        float titleItemHeight = density*50;  //50dp，高
        float paddingWight = density*12;  //12dp
        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        //拿到要显示的总高度，超过5个条目时只显示5个，其余滑动显示
        int height;
        if(list.size() > 5){
            height = (int)titleItemHeight * 5;
        }else {
            height = (int)titleItemHeight * list.size();
        }

        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_window_ranking_classify, null);
        this.setContentView(contentView);
        ListView listView = (ListView) contentView.findViewById(R.id.lv_ranking_classify);
        LinearLayout.LayoutParams lp;
        lp= (LinearLayout.LayoutParams) listView.getLayoutParams();
        lp.height = height;
        lp.width = width;  //两边有点点空隙
        //设置整个ListView的给定宽高
        listView.setLayoutParams(lp);
        //初始化list适配器，并把数据设置入listView中
        if(mListPopupWindowAdapter != null ){
            mListPopupWindowAdapter = null;
        }
        mListPopupWindowAdapter = new ListPopupWindowAdapter(context,list,selectName,titleItemHeight);
        listView.setAdapter(mListPopupWindowAdapter);

        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimList);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.transparent));
        this.setBackgroundDrawable(dw);
        this.showAsDropDown(view);

        //点击Item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                mListener.onItemClickListener(position);
            }
        });
        //点击返回键
        listView.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                dismiss();
                return true;
            }
        });

        //消失监听
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                mListener.onPopupWindowDismissListener();
            }
        });

        //点击外围
        this.getContentView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setFocusable(false);
                dismiss();
                return true;
            }
        });
    }

    public interface Listener{
        void onPopupWindowDismissListener();  //弹框消失监听
        void onItemClickListener(int position);  //条目点击监听
    }

}

