package com.ambientdigitalgroup.ambchat.networks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class SeverRequest extends AsyncTask<Map<String, String>, Void, Map<String, String>> {
    public interface SeverRequestListener {

        void completed(Object obj);
    }

    Response response;
    OkHttpClient client = new OkHttpClient.Builder().build();
    SeverRequestListener mListener;

    Request mRequest;

    public SeverRequest(SeverRequestListener listener) {
        mListener = listener;
    }


    @Override
    protected Map<String, String> doInBackground(Map<String, String>... params) {
        Map<String, String> parameter = params[0];
        Map<String, String> result = new HashMap<>();

        mRequest = prepare(parameter);

        try {
            response = client.newCall(mRequest).execute();
            result.put("data", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected abstract Request prepare(Map<String, String> parameter);

    protected abstract Object process(String data);


    @Override
    protected void onPostExecute(Map<String, String> result) {
        String data = result.get("data");

        Object obj = process(data);
        mListener.completed(obj);
    }

    static String URL = "https://ambchat.herokuapp.com/api/";
}
