package com.example.instanoreels;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView web;

    private static final String UA =
        "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 "
        + "(KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36";

    private static final String JS =
        "(function(){"
        + "if(!document.getElementById('noreels-style')){"
        + "var s=document.createElement('style');s.id='noreels-style';"
        + "s.innerHTML='a[href^=\"/reels\"],a[href^=\"/explore\"]{display:none!important}';"
        + "document.head.appendChild(s);}"
        + "if(!document.getElementById('dark-style')){"
        + "var d=document.createElement('style');d.id='dark-style';"
        + "d.innerHTML='html{background:#fff;filter:invert(1) hue-rotate(180deg)}"
        + "img,video,canvas,picture{filter:invert(1) hue-rotate(180deg)}';"
        + "document.head.appendChild(d);}"
        + "if(!window.__noreels){window.__noreels=1;"
        + "setInterval(function(){var p=location.pathname;"
        + "if(p.indexOf('/reels')===0||p.indexOf('/explore')===0){location.href='/';}"
        + "},500);}"
        + "})();";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        web.setBackgroundColor(Color.BLACK);
        setContentView(web);

        WebSettings st = web.getSettings();
        st.setJavaScriptEnabled(true);
        st.setDomStorageEnabled(true);
        st.setUserAgentString(UA);

        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(true);
        cm.setAcceptThirdPartyCookies(web, true);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(JS, null);
            }
        });

        web.loadUrl("https://www.instagram.com/");
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
