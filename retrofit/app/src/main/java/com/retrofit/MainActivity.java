package com.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;


public class MainActivity extends AppCompatActivity {

    public final String api_nasa = "https://api.nasa.gov";
    public final String api_translate = "https://api.cognitive.microsofttranslator.com";
    ImageView space_image;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        space_image = findViewById(R.id.space_image);
        description = findViewById(R.id.description);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_nasa)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpaceService spaceService = retrofit.create(SpaceService.class);
        Call<ResponseSpace> call = spaceService.getSpaceInfo("DEMO_KEY");
        call.enqueue(new SpaceCallback());

    }
class SpaceCallback implements Callback<ResponseSpace>{


    @Override
    public void onResponse(Call<ResponseSpace> call, Response<ResponseSpace> response) {
        if (response.isSuccessful()){
            String s = response.body().explanation;
            //TODO подготовка данных к отправке
            TranslateBody translateBody = new TranslateBody();
            translateBody.Text = s;
            //TODO создать объект retrofit
            Retrofit retrofit = new Retrofit.Builder().baseUrl(api_translate).addConverterFactory(GsonConverterFactory.create()).build();
            TranslateService translateService = retrofit.create(TranslateService.class);
            Call<ResponseTranslate[]> callTranslate = translateService.getTranslate("ru-RU",new TranslateBody[]{translateBody});
            callTranslate.enqueue(new TranslateCallback());
            description.setText(s);
            if (response.body().media_type.equals("image")){
                Picasso.get()
                        .load(response.body().url)
                        .placeholder(R.drawable.apod)
                        .into(space_image);
            }
            else if (response.body().media_type.equals("video")){
                String url = response.body().url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseSpace> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"Ответ не получен",Toast.LENGTH_SHORT).show();
    }

}
class TranslateCallback implements Callback<ResponseTranslate[]>{

    @Override
    public void onResponse(Call<ResponseTranslate[]> call, Response<ResponseTranslate[]> response) {
        if (response.isSuccessful()){
            String s = response.body()[0].translations[0].text;
            description.setText(s);
        }
    }

    @Override
    public void onFailure(Call<ResponseTranslate[]> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"Ответ не получен",Toast.LENGTH_SHORT).show();
    }
}


}

