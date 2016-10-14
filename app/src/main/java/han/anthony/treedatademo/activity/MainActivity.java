package han.anthony.treedatademo.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import han.anthony.treedatademo.R;
import han.anthony.treedatademo.bean.FileBean;
import han.anthony.treedatademo.adapter.TreeViewAdapter;

public class MainActivity extends AppCompatActivity implements TreeViewAdapter.OnItemClickListener {
    private RecyclerView mTreeView;
    private List<FileBean> mDatas;
    private TreeViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTreeView = (RecyclerView) findViewById(R.id.tree_view);
        mDatas = initDatas();
        try {
            mAdapter=new TreeViewAdapter(this, mDatas, 2);
            mTreeView.setAdapter(mAdapter);
            mTreeView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
            mAdapter.setOnItemClickListener(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<FileBean> initDatas() {
        List<FileBean> datas = new ArrayList<>();
        FileBean bean;

        bean = new FileBean(1, 0, "根目录1");
        datas.add(bean);
        bean = new FileBean(2, 0, "根目录2");
        datas.add(bean);
        bean = new FileBean(3, 0, "根目录3");
        datas.add(bean);
        bean = new FileBean(4, 2, "根目录2-1");
        datas.add(bean);
        bean = new FileBean(5, 2, "根目录2-2");
        datas.add(bean);
        bean = new FileBean(6, 2, "根目录2-3");
        datas.add(bean);
        bean = new FileBean(7, 3, "根目录3-1");
        datas.add(bean);
        bean = new FileBean(8, 3, "根目录3-2");
        datas.add(bean);
        bean = new FileBean(9, 0, "根目录4");
        datas.add(bean);
        bean = new FileBean(10, 4, "根目录2-1-1");
        datas.add(bean);
        bean = new FileBean(11, 4, "根目录2-1-2");
        datas.add(bean);
        return datas;
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    /**
     * 长按增加节点
     */
    @Override
    public void onItemLongClick(View v, final int position) {
        final EditText et=new EditText(this);
        //设置对话框
        AlertDialog.Builder buider = new AlertDialog.Builder(this);
        buider.setTitle("Add Node?")
                .setView(et)
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.addExtraNode(position,et.getText().toString());
            }
        });
        buider.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        buider.create().show();
    }
}
