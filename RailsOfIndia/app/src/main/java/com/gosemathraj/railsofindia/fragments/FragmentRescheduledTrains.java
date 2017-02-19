package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class FragmentRescheduledTrains extends Fragment {

    @BindView(R.id.webview)
    WebView webView;

    private String URL = "http://enquiry.indianrail.gov.in/ntes/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_webview,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.rescheduled_trains));
        Utils.getInstance().showProgressDialog(getActivity(),getString(R.string.loading));
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Utils.getInstance().closeProgressDialog();
            }
        });
    }
}
