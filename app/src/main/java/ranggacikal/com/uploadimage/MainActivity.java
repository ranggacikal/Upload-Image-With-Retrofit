package ranggacikal.com.uploadimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ranggacikal.com.uploadimage.api.ConfigRetrofit;
import ranggacikal.com.uploadimage.model.DataLogin;
import ranggacikal.com.uploadimage.model.ResponseUploadPhoto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView txtUsername, txtEmailUser, test;
    CircleImageView imgUser, imgUserFix;
    Button btnChangeImage, btnUpload;
    private Bitmap bitmap;


    private static final int IMG_REQUES = 777;

    public static final String EXTRA_USERNAME = "extra_username";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_IMAGE = "extra_image";
    public static final String EXTRA_ID = "extra_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtUsername = findViewById(R.id.textUsername);
        txtEmailUser = findViewById(R.id.textEmailUser);
        imgUser = findViewById(R.id.imgUser);
        imgUserFix = findViewById(R.id.imgUserFix);
        btnChangeImage = findViewById(R.id.buttonChangeImage);
        btnUpload = findViewById(R.id.buttonUpload);
        test = findViewById(R.id.txtTest);

        String username = getIntent().getStringExtra(EXTRA_USERNAME);
        String emailUser = getIntent().getStringExtra(EXTRA_EMAIL);
        String imageUser = getIntent().getStringExtra(EXTRA_IMAGE);
//
//
        Glide.with(MainActivity.this)
                .load(imageUser)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgUserFix);

        txtUsername.setText(username);
        txtEmailUser.setText(emailUser);

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
    }

    private void UploadImage() {
        String id = getIntent().getStringExtra(EXTRA_ID);
        String photo = imageToString();

        ConfigRetrofit.service.Upload(Integer.parseInt(id), photo).enqueue(new Callback<ResponseUploadPhoto>() {
            @Override
            public void onResponse(Call<ResponseUploadPhoto> call, Response<ResponseUploadPhoto> response) {
                if (response.isSuccessful()){
                    String sukses = response.body().getSukses();

                    if (sukses.equals("1")){
                        Toast.makeText(MainActivity.this, "Gambar Berhasil Di Upload", Toast.LENGTH_SHORT).show();
                        btnUpload.setVisibility(View.GONE);
                        btnChangeImage.setVisibility(View.VISIBLE);
                        imgUser.setVisibility(View.GONE);
                        imgUserFix.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(MainActivity.this, "Gambar Gagal Di Upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUploadPhoto> call, Throwable t) {
                Toast.makeText(MainActivity.this, "PERIKSA JARINGAN ANDA", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SelectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMG_REQUES && resultCode==RESULT_OK && data != null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgUser.setImageBitmap(bitmap);
                imgUserFix.setVisibility(View.GONE);
                imgUser.setVisibility(View.VISIBLE);
                btnChangeImage.setVisibility(View.GONE);
                btnUpload.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}
