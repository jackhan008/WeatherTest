package com.example.a76568.weathertest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import cn.edu.pku.hanqin.app.Weatherapplication;
import cn.edu.pku.hanqin.bean.City;

/**
 * Created by 76568 on 2016/10/18 0018.
 */
public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;

    private ListView listView;

    private List<City> data;
    private City tempdata;
    private Weatherapplication App;

    private String SelectedId=null;
    private TextView cityName;

    private TextView selectcity;
    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> cityId = new ArrayList<String>();

    ArrayList<String> mData = new ArrayList<String>();   //用作搜索的城市
    private EditText mSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        mBackBtn = (ImageView) findViewById(R.id.back_normal);
        mBackBtn.setOnClickListener(this);

        mSearch = (EditText) findViewById(R.id.search_bar);

        //

        App = (Weatherapplication) getApplication();
        data = App.getCityList();
        int i = 0;
        while (i < data.size()) {
            city.add(data.get(i).getCity().toString());
            cityId.add(data.get(i++).getNumber().toString());
            tempdata = data.get(i);
            mData.add(tempdata.getFirstPY().toString()+'-'+tempdata.getCity().toString()+'-'+tempdata.getNumber().toString());
        }
        //

        listView = (ListView) findViewById(R.id.city_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mData);
        //missing getData()
        listView.setAdapter(adapter);

        //
        cityName =(TextView)findViewById(R.id. title_city_name);
        selectcity = (TextView)findViewById(R.id.title_city_name);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SelectCity.this, "您单击了:" + city.get(i), Toast.LENGTH_SHORT).show();
                SelectedId = cityId.get(i);
                selectcity.setText("您选择的地区："+city.get(i));
            }
        });
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_normal:
                Intent i = new Intent();
                i.putExtra("cityCode", SelectedId);
                setResult(RESULT_OK, i);
                //
                if(SelectedId != null) {
                    SharedPreferences mySharedPreferences = getSharedPreferences("config",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString("main_city_code", SelectedId);
                    editor.commit();
                }
                finish();
                break;
            default:
                break;
        }
    }

}
