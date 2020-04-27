package com.example.week10java;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    EditText url;
    Button urlChange, refresh, runShoutout, initialize, pageBackward, pageForward;
    ArrayList<String> pageHistory;
    ListIterator <String> pageIterator;
    String currentURl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        pageHistory = new ArrayList<>();

        url = findViewById(R.id.editText);
        urlChange = findViewById(R.id.goUrl);
        runShoutout = findViewById(R.id.javascriptButton);
        refresh = findViewById(R.id.refreshButton);
        initialize = findViewById(R.id.initialize);
        pageBackward = findViewById(R.id.pageBackward);
        pageForward = findViewById(R.id.pageForward);

        webView.loadUrl("file:///android_asset/index.html");
        pageHistory.add(webView.getUrl());
        currentURl = webView.getUrl();

        pageBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIterator = pageHistory.listIterator();
                try {
                    while (pageIterator.hasNext()) {
                        if (pageIterator.next().equals(currentURl)) {
                            pageIterator.previous();
                            webView.loadUrl(pageIterator.previous());
                            currentURl = webView.getUrl();
                            break;
                        }
                    }
                } catch (NoSuchElementException e) {
                }
            }
        });

        pageForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIterator = pageHistory.listIterator();
                try {
                    while (pageIterator.hasNext()) {
                        if (pageIterator.next().equals(currentURl)) {
                            webView.loadUrl(pageIterator.next());
                            currentURl = webView.getUrl();
                            break;
                        }
                    }
                } catch (NoSuchElementException e) {
                }
            }
        });

        initialize.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                webView.evaluateJavascript("javascript:initialize()", null);
            }
        });

        runShoutout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                webView.evaluateJavascript("javascript:shoutOut()", null);
            }
        });

        urlChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIterator = pageHistory.listIterator();
                while (pageIterator.hasNext()){
                    pageIterator.next();
                }
                while (pageIterator.hasPrevious()) {
                    if (currentURl.equals(pageIterator.previous())) {
                        break;
                    }
                    pageIterator.remove();
                }
                if (url.getText().equals("index.html")){
                    webView.loadUrl("file:///android_asset/index.html");
                }else {
                    webView.loadUrl("http:///" + url.getText());

                }

                pageHistory.add(webView.getUrl());
                currentURl = webView.getUrl();


            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(webView.getUrl());
            }
        });
    }
}
