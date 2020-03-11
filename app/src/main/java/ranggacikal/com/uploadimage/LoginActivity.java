package ranggacikal.com.uploadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ranggacikal.com.uploadimage.api.ApiService;
import ranggacikal.com.uploadimage.api.ConfigRetrofit;
import ranggacikal.com.uploadimage.model.ResponseLoginUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmailLogin, edtPasswordLogin;
    Button btnLogin;
    ApiService apiService;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        apiService = ConfigRetrofit.service;

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login() {

        final String email = edtEmailLogin.getText().toString();
        final String password = edtPasswordLogin.getText().toString();

        if (email.isEmpty()){
            edtEmailLogin.setError("Email Tidak Boleh Kosong");
            edtEmailLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailLogin.setError("Masukan Email Yang Valid !!");
            edtEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPasswordLogin.setError("Password Tidak Boleh Kosong");
            edtPasswordLogin.requestFocus();
            return;
        }

        ConfigRetrofit.service.LoginUser(email, password).enqueue(new Callback<ResponseLoginUser>() {
            @Override
            public void onResponse(Call<ResponseLoginUser> call, Response<ResponseLoginUser> response) {
                if (response.isSuccessful()){
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    String username = response.body().getDataLogin().getUsername();
                    String emailUser = response.body().getDataLogin().getEmail();
                    String image = response.body().getDataLogin().getPhoto();
                    String id = response.body().getDataLogin().getId();

                    if (status == 1){
                        Toast.makeText(context, pesan+" \nSelamat Datang : "+username , Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(context, MainActivity.class);
                        mainActivity.putExtra(MainActivity.EXTRA_USERNAME, username);
                        mainActivity.putExtra(MainActivity.EXTRA_EMAIL, emailUser);
                        mainActivity.putExtra(MainActivity.EXTRA_IMAGE, image);
                        mainActivity.putExtra(MainActivity.EXTRA_ID, id);
                        startActivity(mainActivity);
                    }else{
                        Toast.makeText(context, pesan+", Email / Password Salah !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLoginUser> call, Throwable t) {
                Toast.makeText(context, "Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
