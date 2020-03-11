package ranggacikal.com.uploadimage.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    private static ConfigRetrofit mInstance;

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.43.25/backend_belajar/index.php/API_belajar/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static synchronized ConfigRetrofit getInstance(){
        if (mInstance==null){
            mInstance = new ConfigRetrofit();
        }
        return mInstance;
    }

    public static ApiService service = retrofit.create(ApiService.class);
}
