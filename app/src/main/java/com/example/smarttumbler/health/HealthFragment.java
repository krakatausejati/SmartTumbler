package com.example.smarttumbler.health;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttumbler.R;
import com.example.smarttumbler.notifikasi.AlertReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import id.co.telkom.iot.AntaresHTTPAPI;
import id.co.telkom.iot.AntaresResponse;

public class HealthFragment extends Fragment implements AntaresHTTPAPI.OnResponseListener{

    private TextView txtData;
    private String TAG = "ANTARES-API";
    private AntaresHTTPAPI antaresAPIHTTP;
    private String dataDevice;
    private ImageButton btnRefresh, btnNotify;


    private final String KEY = "96298b1c6436e39f:7bb2ceaf29a16e52";
    private final String APPLICATION_NAME = "Tumblerion";
    private final String DEVICE_NAME ="SensorBerat";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_health, container, false);

        btnRefresh = root.findViewById(R.id.btnRefresh);
        txtData = root.findViewById(R.id.keteranganAir3);
        btnNotify = root.findViewById(R.id.btnNotify);

        // --- Inisialisasi API Antares --- //
        //antaresAPIHTTP = AntaresHTTPAPI.getInstance();
        antaresAPIHTTP = new AntaresHTTPAPI();
        antaresAPIHTTP.addListener(this);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Example runnable = new Example(10);
                new Thread(runnable).start();
            }
        });

//        startAlarm();
//        Toast.makeText(getActivity(),"Notification Will Be Send",Toast.LENGTH_SHORT).show();
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
                Toast.makeText(getActivity(),"Notification Will Be Send",Toast.LENGTH_SHORT).show();
            }
        });

    return root;
    }

    @Override
    public void onResponse(AntaresResponse antaresResponse) {
// --- Cetak hasil yang didapat dari ANTARES ke System Log --- //
        //Log.d(TAG,antaresResponse.toString());
        Log.d(TAG,Integer.toString(antaresResponse.getRequestCode()));
        if(antaresResponse.getRequestCode()==0){
            try {
                JSONObject body = new JSONObject(antaresResponse.getBody());
                dataDevice = body.getJSONObject("m2m:cin").getString("con");
                JSONObject obj = new JSONObject(dataDevice);
                Double test = obj.getDouble("weight");
                String weight = String.valueOf(test);
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        txtData.setText(weight);
                    }
                });
                Log.d(TAG,weight);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class Example implements Runnable{
        int seconds;
        Example(int seconds){
            this.seconds = seconds;
        };

        @Override
        public void run() {
            for (int i= 0;i<seconds;i++){
                antaresAPIHTTP.getLatestDataofDevice(KEY,APPLICATION_NAME,DEVICE_NAME);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startAlarm() {
        int seconds = 60;
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, seconds, pendingIntent);
    }
}