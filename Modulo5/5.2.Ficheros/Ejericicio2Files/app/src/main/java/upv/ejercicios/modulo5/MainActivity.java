package upv.ejercicios.modulo5;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by migui on 0014.
 */

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private WebView webView;
    private ProgressDialog progressDialog;
    private static final String XML_FILE_NAME = "movies.xml";
    private static final String XML_MOVIES_LIST_TAG = "MovieList";
    private static final String XML_MOVIE_TAG = "Movie";
    private static final String XML_MOVIE_IMG_ATTRIBUTE = "image";
    private static final String XML_MOVIE_NAME_ATTRIBUTE = "name";
    private static final String XML_MOVIE_URL_ATTRIBUTE = "url";

    private static final String FOLLOWING_IMG_RES = "R.drawable.following";
    private static final String MEMENTO_IMG_RES = "R.drawable.memento";
    private static final String BATMAN_BEGINS_IMG_RES = "R.drawable.batman_begins";
    private static final String PRESTIGE_IMG_RES = "R.drawable.the_prestige";
    private static final String DARK_KNIGHT_IMG_RES = "R.drawable.the_dark_knight";
    private static final String DARK_KNIGHT_RISES_IMG_RES = "R.drawable.the_dark_knight_rises";
    private static final String INSOMNIA_IMG_RES = "R.drawable.insomnia";
    private static final String INCEPTION_IMG_RES = "R.drawable.inception";

    private List<Item> populateMoviesDataFromXML() {
        List<Item> movies = new ArrayList<>();

        Log.d("[DEBUG]", "Populate Movies Data From XML");
        try {
            BufferedReader inputFileReader = new BufferedReader(new InputStreamReader(openFileInput(XML_FILE_NAME)));
            Log.d("[DEBUG]", "Input file Reader opened");
            XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
            Log.d("[DEBUG]", "XML Pull Parser instantiated");
            pullParser.setInput(inputFileReader);
            Log.d("[DEBUG]", "XML Parser ready for XML file: " + XML_FILE_NAME);
            int currentEventType = pullParser.getEventType();

            String movieImage;
            String movieName;
            String movieURL;
            while (currentEventType != XmlPullParser.END_DOCUMENT) {

                switch (currentEventType) {
                    case XmlPullParser.START_TAG:
                        movieImage = pullParser.getAttributeValue(null, XML_MOVIE_IMG_ATTRIBUTE);
                        movieName = pullParser.getAttributeValue(null, XML_MOVIE_NAME_ATTRIBUTE);
                        movieURL = pullParser.getAttributeValue(null, XML_MOVIE_URL_ATTRIBUTE);

                        if (movieImage== null || movieName == null || movieURL == null) {
                            Log.d("[DEBUG]","Attributes null found, breaking switch-case");
                            break;
                        }
                        int movieImageRes ;
                        switch (movieImage) {
                            case BATMAN_BEGINS_IMG_RES:
                                movieImageRes = R.drawable.batman_begins;
                                break;

                            case FOLLOWING_IMG_RES:
                                movieImageRes = R.drawable.following;
                                break;

                            case INCEPTION_IMG_RES:
                                movieImageRes = R.drawable.inception;
                                break;

                            case INSOMNIA_IMG_RES:
                                movieImageRes = R.drawable.insomnia;
                                break;

                            case MEMENTO_IMG_RES:
                                movieImageRes = R.drawable.memento;
                                break;

                            case DARK_KNIGHT_IMG_RES:
                                movieImageRes = R.drawable.the_dark_knight;
                                break;

                            case DARK_KNIGHT_RISES_IMG_RES:
                                movieImageRes = R.drawable.the_dark_knight_rises;
                                break;

                            case PRESTIGE_IMG_RES:
                                movieImageRes = R.drawable.the_prestige;
                                break;

                            default:
                                 movieImageRes = R.drawable.batman_begins;
                                break;

                        }

                        Item item = new Item(movieImageRes, movieName, movieURL);
                        Log.d("[DEBUG]" , "Item retrieved from file: " +item.toString());
                        movies.add(item);
                        break;

                    default:
                        break;
                }
                currentEventType =  pullParser.next();
            }

            inputFileReader.close();
        } catch (FileNotFoundException fileNotFoundEx) {
            Log.e("[ERROR] ", "File not found: " + XML_FILE_NAME);
            Log.e("[ERROR]", fileNotFoundEx.getMessage());
        } catch (XmlPullParserException pullParserEx) {
            Log.e("[ERROR]", "XML Parser Exception");
            Log.e("[ERROR]", pullParserEx.getMessage());
        } catch (IOException ioEx) {
            Log.e("[ERROR]", "IO Exception");
            Log.e("[ERROR]", ioEx.getMessage());

        }

        return movies;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main);
        this.webView = (WebView) findViewById(R.id.webView);


        this.listView = (ListView) findViewById(R.id.listView);

        List<Item> items = new ArrayList<Item>();
        // Old - static data population about movies.....
        /*
        items.add(new Item(R.drawable.following, "Following",

                    "http://www.imdb.com/title/tt0154506/"));
        items.add(new Item(R.drawable.memento, "Memento",
                "http://www.imdb.com/title/tt0209144/"));
        items.add(new Item(R.drawable.batman_begins, "Batman Begins",
                "http://www.imdb.com/title/tt0372784/"));
        items.add(new Item(R.drawable.the_prestige, "The Prestige",
                "http://www.imdb.com/title/tt0482571/"));
        items.add(new Item(R.drawable.the_dark_knight, "The Dark Knight",
                "http://www.imdb.com/title/tt0468569/"));
        items.add(new Item(R.drawable.inception, "Inception",
                "http://www.imdb.com/title/tt1375666/"));
        items.add(new Item(R.drawable.the_dark_knight_rises,
                "The Dark Knight Rises", "http://www.imdb.com/title/tt1345836/"));
        */

        // New - dynamic data population reading from an XML...
        items = populateMoviesDataFromXML();
        // Sets the data behind this ListView
        this.listView.setAdapter(new ItemAdapter(this, items));

        // Register a callback to be invoked when an item in this AdapterView
        // has been clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                // Show progress dialog
                progressDialog = ProgressDialog.show(MainActivity.this,
                        "ProgressDialog", "Loading!");

                // Tells JavaScript to open windows automatically.
                webView.getSettings().setJavaScriptEnabled(true);
                // Sets our custom WebViewClient.
                webView.setWebViewClient(new myWebClient());
                // Loads the given URL
                Item item = (Item) listView.getAdapter().getItem(position);
                webView.loadUrl(item.getUrl());
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
            progressDialog.dismiss();
        }
    }
}
