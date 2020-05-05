package com.appplepie.riotapitest;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.JsonReader;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.gson.Gson;

        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.URL;
        import java.nio.charset.StandardCharsets;

        import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    final String league_url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/";
    final String summoner_url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private final String API_KEY = "RGAPI-fd8dbf93-0d57-48ed-99de-0f959909683b";
    Button request_button;
    TextView rank_win, rank_lose, summoner_name_text;
    EditText summoner_name_edit;
    String summoner_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request_button = findViewById(R.id.request_button);
        rank_win = findViewById(R.id.rank_win);
        rank_lose = findViewById(R.id.rank_lose);
        summoner_name_text = findViewById(R.id.summoner_name_text);
        summoner_name_edit = findViewById(R.id.summoner_name_edit);

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               summoner_name = summoner_name_edit.getText().toString();
               if (!summoner_name.equals("")){
                   Log.e("summoner_name", "onClick: "+summoner_name );
                   AsyncTask.execute(new Runnable() {
                       @Override
                       public void run() {
                           URL summonerEndpoint = null;
                           try {
                               summonerEndpoint = new URL(summoner_url + summoner_name + "?api_key=" + API_KEY);
                               Log.e("requestUrl", "run: "+summonerEndpoint );

                               HttpsURLConnection myConnection =
                                       (HttpsURLConnection) summonerEndpoint.openConnection();
                               myConnection.setRequestMethod("GET");
                               if (myConnection.getResponseCode() == 200) {
                                   // Success
                                   InputStream responseBody = myConnection.getInputStream();
                                   InputStreamReader reader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                                   SummonerResult result  = new Gson().fromJson(reader, SummonerResult.class);
                                   Log.e("result", "run: "+result.summonerLevel );
                               } else {
                                   Log.e("Failed", "run: error" );
                               }
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   });
               }

            }
        });
    }

}
