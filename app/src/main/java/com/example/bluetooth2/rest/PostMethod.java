package com.example.bluetooth2.rest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.bluetooth2.Qrcode;
import com.example.bluetooth2.R;
import com.example.bluetooth2.dao.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import static com.example.bluetooth2.rest.CallAPI.readAPIOtputResult;

public class PostMethod extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    private final WeakReference<ImageView> imageViewReference;
    private String server_response, action = "", errorMessage = "";
    private final int TIMEOUT_MILLISEC = 30000;
    private boolean error = false;
    private Map<String, Object> data_response_map;
    private Order mOrder;
    private Context mContext;

    public PostMethod(Qrcode activity, Order order) {
        dialog = new ProgressDialog(activity);
        mContext = activity;
        imageViewReference = null;
        mOrder = order;
    }

    public PostMethod(Qrcode activity, Order order, ImageView imageView) {
        dialog = new ProgressDialog(activity);
        mContext = activity;
        imageViewReference = new WeakReference<ImageView>(imageView);
        mOrder = order;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage(mContext.getResources().getString(R.string.waitingForConnection));
        dialog.setTitle(mContext.getResources().getString(R.string.waiting));
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            action = strings[0];

            ApiData apiData;

            if ("senim".equals(mOrder.getCompany())) {
                apiData = new ApiDataSenim(action);
            } else {
                apiData = new ApiDataRahmet(action);
            }

            if ("GET".equals(apiData.getRequestMethod())) {
//                callGetApi(apiData.getURL(), apiData.getParams(), apiData.getRequestMethod());
            } else {
                callApi(apiData.getURL(), apiData.getParams(), apiData.getRequestMethod());
                if ("rahmet".equals(mOrder.getCompany()) && "auth".equals(action)) {
                    ApiData.setRahmetToken(server_response);
                }
            }
        } catch (TimeoutException e) {
            System.err.println("TimeoutException. Waiting too long answer from server. doInBackground()");
            e.printStackTrace();
            error = true;
            errorMessage = mContext.getResources().getString(R.string.errorMessageNoAnswer);
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException. URL is invalid. doInBackground()");
            e.printStackTrace();
            error = true;
            errorMessage = mContext.getResources().getString(R.string.errorMessageIncorrectURL);
        } catch (IOException e) {
            System.err.println("IOException. doInBackground()");
            e.printStackTrace();
            error = true;
            errorMessage = mContext.getResources().getString(R.string.errorMessageNotSpecified);
        }

        return server_response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Response from: " + mOrder.getCompany() + "; method: " + action, "" + server_response);

        dialog.cancel();

        if(error) {
            showTextAlert(errorMessage);
        } else {
            if ("create".equals(action)) {
                showQRCodeImageView();
            } else if ("status".equals(action)) {
                showPaidStatusAlert();
            }

            if(error) { //TODO after error
                showTextAlert(errorMessage);
            }
        }

    }


    public void callApi(String httpURL, HashMap<String, String> params, String requestMethod) throws TimeoutException, IOException, MalformedURLException {
        URL url = new URL(httpURL);

        Set set = params.entrySet();
        Iterator i = set.iterator();
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {

            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");


        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);

        conn.setConnectTimeout(TIMEOUT_MILLISEC);
        conn.setReadTimeout(TIMEOUT_MILLISEC);
        if ("senim".equals(mOrder.getCompany())) {
//            conn.setRequestProperty("Content-Type", "application/json"); //TODO tell them!
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("X-Senim-API-Token", "o9mubdh7ri5gdabbjvl89el1c0");
        } else if ("rahmet".equals(mOrder.getCompany())) {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (!"auth".equals(action) && !"".equals(ApiData.token)) {
                conn.setRequestProperty("Authorization", ApiData.token);
            }
        }
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        reader.close();
        conn.disconnect();
        server_response = builder.toString();
        this.checkResponseForError(server_response);
        this.responseMap();
    }

    private void responseMap() {
        try {
            if ("senim".equals(mOrder.getCompany())) {
                data_response_map = CallAPI.getAPIResult(server_response, mOrder.getCompany());
            } else if ("rahmet".equals(mOrder.getCompany())) {
                data_response_map = CallAPI.getAPIResult(server_response, mOrder.getCompany());
            }
        } catch (NullPointerException e) {
            System.err.println("NullPointerException. responseMap()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessageNotCorrectQR);
        } catch (IOException e) {
            System.err.println("IOException. responseMap()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessageNotSpecifiedQR);
        }
    }


    public void showQRCodeImageView() {
        try {
            if(data_response_map == null){
                throw new IOException("data_response_map is null!!! in showImageView() ");
            }
            String base = "";
            if ("senim".equals(mOrder.getCompany())) {
                base = (String) data_response_map.get("image");
            } else if ("rahmet".equals(mOrder.getCompany())) {
                base = (String) data_response_map.get("qr_image_base_64");
            }

            if (!"".equals(base) && !"null".equals(base) && base != null) {
                byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);

                System.out.println("imageViewReference: " + imageViewReference);
                System.out.println("base: " + base);
                System.out.println("imageAsBytes: " + imageAsBytes);
                System.out.println("url: " + (String) CallAPI.getAPIResult(server_response, mOrder.getCompany()).get("url"));

                if (imageViewReference != null) {
                    final ImageView imageView = imageViewReference.get();
                    if (imageView != null) {
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    }
                }
            } else {
                throw new IOException("INCORRECT QR image base code from server");
            }
        } catch (NullPointerException e) {
            System.err.println("NullPointerException. showImageView()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessageNotCorrectQR);
        } catch (IOException e) {
            System.err.println("IOException. showImageView()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessageNotSpecifiedQR);
        }
    }

    public void showTextAlert(String messsage) {
        AlertDialog.Builder ac = new AlertDialog.Builder(mContext);
//        ac.setTitle("Result");
        ac.setMessage(messsage);
        ac.setCancelable(true);

        ac.setPositiveButton(
                mContext.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = ac.create();
        alert.show();
    }

    public void showPaidStatusAlert() {

        try {
            if(data_response_map == null){
                throw new IOException("data_response_map is null!!! in showImageView() ");
            }
            boolean paid = false;
            if ("senim".equals(mOrder.getCompany())) {
                paid = ("2".equals(data_response_map.get("statusId")));
            } else if ("rahmet".equals(mOrder.getCompany())) {
//                paid = (Boolean) data_response_map.get("qr_image_base_64"); TODO
            }
            ImageView image = new ImageView(mContext);
            AlertDialog.Builder ac = new AlertDialog.Builder(mContext);

            ac.setTitle(mContext.getResources().getString(R.string.payment));

            if(paid) {
                image.setImageResource(R.drawable.done);
                ac.setView(image);
                ac.setMessage(mContext.getResources().getString(R.string.orderIsPaid));
            } else{
                image.setImageResource(R.drawable.cancel);
                ac.setView(image);
                ac.setMessage(mContext.getResources().getString(R.string.orderIsNOTPaid));
            }
            ac.setCancelable(true);

            ac.setPositiveButton(
                    mContext.getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
//                        dialog.dismiss();
                        }
                    });

            AlertDialog alert = ac.create();
            alert.show();
        } catch (NullPointerException e) {
            System.err.println("NullPointerException. showPaidStatusAlert()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessagePaymentErrorNULL);
        } catch (IOException e) {
            System.err.println("IOException. showPaidStatusAlert()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessagePaymentNotSpecified);
        }
    }

    public void checkResponseForError(String result) {
        try {
            Map<String,Object> output = readAPIOtputResult(result);
            // rahmet
            if(output.get("status") != null && output.get("error_code") != null) {
                if ("error".equals(output.get("status").toString()) || !"0".equals(output.get("error_code").toString())) {
                    throw new IOException("Error answer from server.");
                }
            }
        } catch (IOException e) {
            System.err.println("IOException. checkForError()");
            error = true;
            e.printStackTrace();
            errorMessage = mContext.getResources().getString(R.string.errorMessageNotCorrectRequest);
        }
    }

}



