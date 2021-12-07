package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Animal_fragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private LinearLayoutManager manager;

    ArrayList<PetItem> list_pet = null;
    PetItem item = null;
    private String mykey = "rg0WWVl2HctjIbFCkWtKMl801PsVp%2F14tDW83rzRoLQ58R7SWNhglK2eScq9AeL2d3%2FsdGmrsNacu3Z4RPNsuA%3D%3D";
    private CardView pet_card;

    private Spinner spinnerCity, spinnerSigungu;
    private ArrayAdapter<String> arrayAdapter;
    private Button Btn_aniSearch;
    private String queryUrl; //="http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20211001&endde=20211201&pageNo=1&numOfRows=30&ServiceKey=" + mykey ;//+ "&clCd=31" + "&numOfRows=20";


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

        spinnerCity = (Spinner) v.findViewById(R.id.spin_city);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_region));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(arrayAdapter);

        spinnerSigungu = (Spinner) v.findViewById(R.id.spin_sigungu);

        initGetData();
        initAddressSpinner();

        Btn_aniSearch = (Button) v.findViewById(R.id.btn_aniSearch);
        Btn_aniSearch.setOnClickListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(), recyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        final Intent intent = new Intent(getActivity(),Pet_Card.class);
                        //intent.putExtra("")
                        intent.putExtra("kindCd",list_pet.get(position).getKindCd());
                        intent.putExtra("happenPlace",list_pet.get(position).getHappenPlace());
                        intent.putExtra("processState",list_pet.get(position).getProcessState());
                        intent.putExtra("sexCd",list_pet.get(position).getSexCd());
                        intent.putExtra("noticeSdt",list_pet.get(position).getNoticeSdt());
                        intent.putExtra("specialMark",list_pet.get(position).getSpecialMark());
                        intent.putExtra("popfile",list_pet.get(position).getPopfile());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));

        return v;
    }

    private void initGetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tempurl = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20211001&endde=20211201&pageNo=2&numOfRows=10&ServiceKey=" + mykey ;
                getXmlData(tempurl);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(list_pet.isEmpty() == false || list_pet.size() != 0) {
                            Log.d("list_check", list_pet.size() + "");
                            adapter = new CustomAdapter(getActivity().getApplicationContext(), list_pet);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.pet_card){
            Intent intent = new Intent(getActivity(),Pet_Card.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.btn_aniSearch){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String tempurl = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20211001&endde=20211201&pageNo=1&numOfRows=30&ServiceKey=" + mykey ;//+ "&clCd=31" + "&numOfRows=20";
                    getXmlData(tempurl);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(list_pet.isEmpty() == false || list_pet.size() != 0) {
                                Log.d("list_check", list_pet.size() + "");
                                adapter = new CustomAdapter(getActivity().getApplicationContext(), list_pet);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private void getXmlData(String qurl){
        StringBuffer buffer=new StringBuffer();

        String queryUrl= qurl; //"http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20211001&endde=20211201&pageNo=1&numOfRows=30&ServiceKey=" + mykey ;//+ "&clCd=31" + "&numOfRows=20";

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
                        list_pet = new ArrayList<PetItem>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        String test = xpp.getText();
                        //final Intent intent = new Intent(getActivity(),Pet_Card.class);
                        if (tag.equals("item")) {
                            item = new PetItem();
                        } else if (tag.equals("happenPlace")) {
                            xpp.next();
                            item.setHappenPlace(xpp.getText());
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
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
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("processState")){
                            xpp.next();
                            item.setProcessState(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("noticeSdt")){
                            xpp.next();
                            item.setNoticeSdt(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if(tag.equals("specialMark")){
                            xpp.next();
                            item.setSpecialMark(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if(tag.equals("noticeNo")){
                            xpp.next();
                            item.setNoticeNo(xpp.getText());
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
                            list_pet.add(item);
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

    private void initAddressSpinner() {
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 시군구, 동의 스피너를 초기화한다.
                switch (position) {
                    case 0:
                        spinnerSigungu.setAdapter(null);
                        break;
                    case 1:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_seoul);
                        break;
                    case 2:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_busan);
                        break;
                    case 3:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daegu);
                        break;
                    case 4:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_incheon);
                        break;
                    case 5:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gwangju);
                        break;
                    case 6:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daejeon);
                        break;
                    case 7:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_ulsan);
                        break;
                    case 8:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_sejong);
                        break;
                    case 9:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeonggi);
                        break;
                    case 10:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gangwon);
                        break;
                    case 11:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_buk);
                        break;
                    case 12:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_nam);

                        break;
                    case 13:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_buk);
                        break;
                    case 14:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_nam);
                        break;
                    case 15:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_buk);
                        break;
                    case 16:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_nam);
                        break;
                    case 17:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeju);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSigunguSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            spinnerSigungu.setAdapter(null);
            arrayAdapter = null;
        }

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSigungu.setAdapter(arrayAdapter);
    }

}