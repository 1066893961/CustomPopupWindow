package application.app.custompopupwindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RankingPopupWindow mWindow;
    private TextView tv;
    RankingPopupWindow.Listener listener;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list.add("asda");
        list.add("asda");
        list.add("asda");
        list.add("asda");

        tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//生成Listener和清空
                setPopupWindowListener();
                mWindow = new RankingPopupWindow(MainActivity.this, list, "selectName", listener, tv);
            }
        });
    }

    private void setPopupWindowListener() {
        listener = null;
        listener = new RankingPopupWindow.Listener() {
            @Override
            public void onPopupWindowDismissListener() {

            }

            @Override
            public void onItemClickListener(int position) {

            }
        };
    }


}
