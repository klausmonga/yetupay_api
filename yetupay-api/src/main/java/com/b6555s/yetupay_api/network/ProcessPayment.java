package com.b6555s.yetupay_api.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ProcessPayment {
    private String dev;
    private String bill_to;
    private String p_info;
    private String run_env;
    private JSONObject trans;
    private Context context;
    private String productsList="";
    private JSONObject response;
    public String getDev() {
        return dev;
    }

    protected void setDev(String dev) {
        this.dev = dev;
    }

    protected void setBill_to(String bill_to) {
        this.bill_to = bill_to;
    }

    protected void setP_info(String p_info) {
        this.p_info = p_info;
    }

    protected void setRun_env(String run_env) {
        this.run_env = run_env;
    }

    public void addDev(String client_id, String client_secret, String num, String pwd) {
        this.dev = "\"dev\":{\n" +
                "\t  \t\"num\":\""+num+"\",\n" +
                "\t\t\"pwd\":\""+pwd+"\",\n" +
                "\t\t\"client_id\":\""+client_id+"\",\n" +
                "\t\t\"client_secret\":\""+client_secret+"\"\n" +
                "\t  \t\n" +
                "\t  }";
    }

    public String getBill_to() {
        return bill_to;
    }

    public void addBill_to(String num) {
        this.bill_to = "\"bill_to\":{\n" +
                "\t  \t\"num\":\""+num+"\"}";
    }

    public String getP_info() {
        return p_info;
    }
    public void addProduct(String receiver,double price, int quantity,String name,String description){
        this.productsList+=",{\"receiver\":\""+receiver+"\",\"price\":"+String.valueOf(price)+",\"quantity\":"+String.valueOf(quantity)+",\"name\":\""+name+"\",\"description\":\""+description+"\"}";
    }
    public String getProductsList(){
        return this.productsList;
    }
    public void addP_info(String currency,double tax) {
        this.p_info = " \"p_info\":{\n" +
                "\t  \t\"products\":["+this.getProductsList().replaceFirst(",","")+"],\n" +
                "\t  \t\"currency\":\""+currency+"\",\n" +
                "\t  \t\"tax\":"+String.valueOf(tax)+"\n" +
                "\t  }";
    }

    public String getRun_env() {
        return run_env;
    }

    public void addRun_env(String return_slip_format) {
        this.run_env = " \"run_env\":{\n" +
                "\t  \t\"return_slip_format\":\""+return_slip_format+"\"\n" +
                "\t  }";
    }

    public JSONObject getTrans() throws JSONException {
        return new JSONObject("{\"trans\":{"+this.getDev()+","+this.getBill_to()+","+this.getP_info()+","+this.getRun_env()+"}}");
    }

    public void setTrans(JSONObject trans) {
        this.trans = trans;
    }

    public ProcessPayment(Context context) {
        this.context = context;
    }
    public void commit(){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this.context);
            String URL = new Connect().getDomain();
            // Prepares POST data...
            JSONObject jsonBody = this.getTrans();
            final String mRequestBody = jsonBody.toString();
            // Volley request...
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("YETUPAY API LOG", response);
                    context.sendBroadcast(new Intent("YETUPAY-API").putExtra("response",response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("YETUPAY API LOG", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    120000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject getResponse() {
        return response;
    }

    public final void setResponse(JSONObject response) {
        this.response = response;
    }
}
