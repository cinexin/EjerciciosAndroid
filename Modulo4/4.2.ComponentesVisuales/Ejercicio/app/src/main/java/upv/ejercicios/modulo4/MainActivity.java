package upv.ejercicios.modulo4;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by migui on 0007.
 */

public class MainActivity extends AppCompatActivity {
    private GridView ssNNGridView;
    private WebView ssNNwebView;
    private ProgressDialog webOpenProgressDialog;



    /*
        auxiliar method that just returns a list of our 4 sample social networks
     */
    private List<SocialNetworkItem>  populateSampleSocialNetworkList() {
        int numOfSocialNetworks = 4;

        List<SocialNetworkItem> socialNetworkList = new ArrayList<>(numOfSocialNetworks);
        socialNetworkList.add(0,new SocialNetworkItem(R.drawable.instagram, "http://instagram.com"));
        socialNetworkList.add(1, new SocialNetworkItem(R.drawable.pinterest,"http://www.pinterest.com"));
        socialNetworkList.add(2, new SocialNetworkItem(R.drawable.twitter, "http://twitter.com"));
        socialNetworkList.add(3, new SocialNetworkItem(R.drawable.youtube, "http://www.youtube.com"));

        return socialNetworkList;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        List<SocialNetworkItem> socialNetworkItemList;
        this.ssNNGridView = (GridView) findViewById(R.id.gridView);
        this.ssNNwebView = (WebView) findViewById(R.id.webView);

        socialNetworkItemList = this.populateSampleSocialNetworkList();
        this.ssNNGridView.setAdapter(new SocialNetworkItemAdapter(this, socialNetworkItemList));

        ssNNGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                webOpenProgressDialog = ProgressDialog.show(MainActivity.this,"Progress Dialog", "Loading!");
                ssNNwebView.getSettings().setJavaScriptEnabled(true);
                ssNNwebView.getSettings().setUseWideViewPort(true);
                ssNNwebView.setWebViewClient(new myWebClient());
                SocialNetworkItem socialNetworkItem = (SocialNetworkItem) ssNNGridView.getAdapter().getItem(position);
                ssNNwebView.loadUrl(socialNetworkItem.getUrl());
            }
        });


    }

    private class myWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Load the given URL on our WebView.
            view.loadUrl(url);
            
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // When the page has finished loading, hide progress dialog and progress bar
            super.onPageFinished(view, url);
            webOpenProgressDialog.dismiss();
        }
    }
}
