package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Animal_fragment extends Fragment implements View.OnClickListener {

    private Button Btn_search;

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private LinearLayoutManager manager;

    ArrayList<PetItem> list = null;
    PetItem item = null;
    private String mykey = "rg0WWVl2HctjIbFCkWtKMl801PsVp%2F14tDW83rzRoLQ58R7SWNhglK2eScq9AeL2d3%2FsdGmrsNacu3Z4RPNsuA%3D%3D";

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_animal_fragment,container,false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        Btn_search = (Button) v.findViewById(R.id.btn_search);
        Btn_search.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_search:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(list.isEmpty() == false || list.size() != 0) {
                                    Log.d("list_check", list.size() + "");
                                    adapter = new CustomAdapter(getActivity().getApplicationContext(), list);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }).start();
                break;

            default:
                break;

        }
    }



    private void getXmlData(){
        StringBuffer buffer=new StringBuffer();

        String queryUrl="http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20140301&endde=20140430&pageNo=1&numOfRows=10&ServiceKey=" + mykey ;//+ "&clCd=31" + "&numOfRows=20";

        //Log.d("TAG",queryUrl);

        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<PetItem>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        String test = xpp.getText();
                        if (tag.equals("item")) {
                            item = new PetItem();
                        } else if (tag.equals("happenPlace")) {
                            xpp.next();
                            item.setHappenPlace(xpp.getText());
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                            Log.d("item_check_address", item.getHappenPlace());
                        } else if (tag.equals("kindCd")) {
                            xpp.next();
                            item.setKindCd(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("sexCd")) {
                            xpp.next();
                            item.setSexCd(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("popfile")){
                            xpp.next();
                            item.setPopfile(xpp.getText());
                            Log.d("image",xpp.getText());
                            //Glide.with(this).load(xpp.getText()).into(popfile);
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item") && item != null){
                            Log.d("adapter_address_check", item.getHappenPlace());
                            list.add(item);
                        }
                        break;
                }
                eventType= xpp.next();
            }
            //Log.d("TAG", list.size() + "");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}