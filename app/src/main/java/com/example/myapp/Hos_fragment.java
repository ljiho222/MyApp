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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Hos_fragment extends Fragment implements View.OnClickListener {

    private Button Btn_hospital;
    private RecyclerView recyclerView;
    private CustomAdapter2 adapter;
    private LinearLayoutManager manager;

    ArrayList<HosItem> list_hos = null;
    HosItem item = null;
    //private String mykey = "40I6nK0Cvu8WLc2NOY6VwEnJ4Cc3slnxdvwfzUc2NdTOZD2gesbtgF%2FiVzqXPwAyvQ%2FzqBf%2BzuEkb6tftkL2FQ%3D%3D";
    private String mykey = "rg0WWVl2HctjIbFCkWtKMl801PsVp/14tDW83rzRoLQ58R7SWNhglK2eScq9AeL2d3/sdGmrsNacu3Z4RPNsuA==";

    private Spinner spinnerCity, spinnerSigungu;
    private ArrayAdapter<String> arrayAdapter;
    public static final String EXTRA_ADDRESS = "address";

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_hos_fragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        spinnerCity = (Spinner) v.findViewById(R.id.spin_city);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_region));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(arrayAdapter);

        spinnerSigungu = (Spinner) v.findViewById(R.id.spin_sigungu);

        initAddressSpinner();

        Btn_hospital = (Button) v.findViewById(R.id.btn_hospital);
        Btn_hospital.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hospital:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (list_hos.isEmpty() == false || list_hos.size() != 0) {
                                    Log.d("list_checkcheck", list_hos.size() + "");
                                    adapter = new CustomAdapter2(getActivity().getApplicationContext(), list_hos);
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

    private void getXmlData() {
        StringBuffer buffer = new StringBuffer();

        String queryUrl = "http://data.ulsan.go.kr/rest/ulsananimal/getUlsananimalList?ServiceKey=" + mykey + "&numOfRows=30";
        //String queryUrl = "http://openapi.jeonju.go.kr/rest/dongmulhospitalservice/getDongMulHospital?ServiceKey=" + mykey + "&pageNo=1&numOfRows=10";
        Log.d("TAG", queryUrl);

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        list_hos = new ArrayList<HosItem>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기
                        String test = xpp.getText();
                        if (tag.equals("list")) {
                            Log.d("TAG_list", tag);
                            item = new HosItem();
                        } /*else if (tag.equals("gugun")) {
                            xpp.next();
                            item.setGugun(xpp.getText());
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                            Log.d("item_check_gugun", item.getGugun());
                        } */else if (tag.equals("address")) {
                            xpp.next();
                            item.setAddress(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("entId")) {
                            xpp.next();
                            item.setEntId(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("title")) {
                            xpp.next();
                            item.setTitle(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("tel")) {
                            xpp.next();
                            item.setTel(xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("list") && item != null) {
                            Log.d("adapter_address_check", item.getTitle());
                            list_hos.add(item);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
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

        spinnerSigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 서울특별시 선택시
                if (spinnerCity.getSelectedItemPosition() == 1 && spinnerSigungu.getSelectedItemPosition() > -1) {
                    switch (position) {
                        //25
                        case 0:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangnam);
                            break;
                        case 1:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangdong);
                            break;
                        case 2:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangbuk);
                            break;
                        case 3:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangseo);
                            break;
                        case 4:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gwanak);
                            break;
                        case 5:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gwangjin);
                            break;
                        case 6:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_guro);
                            break;
                        case 7:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_geumcheon);
                            break;
                        case 8:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_nowon);
                            break;
                        case 9:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dobong);
                            break;
                        case 10:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dongdaemun);
                            break;
                        case 11:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dongjag);
                            break;
                        case 12:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_mapo);
                            break;
                        case 13:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seodaemun);
                            break;
                        case 14:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seocho);
                            break;
                        case 15:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seongdong);
                            break;
                        case 16:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seongbuk);
                            break;
                        case 17:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_songpa);
                            break;
                        case 18:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yangcheon);
                            break;
                        case 19:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yeongdeungpo);
                            break;
                        case 20:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yongsan);
                            break;
                        case 21:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_eunpyeong);
                            break;
                        case 22:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jongno);
                            break;
                        case 23:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jung);
                            break;
                        case 24:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jungnanggu);
                            break;
                    }
                } else {

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

    private void setDongSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            //spinnerDong.setAdapter(null);
            arrayAdapter = null;
        }

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerDong.setAdapter(arrayAdapter);
    }

}