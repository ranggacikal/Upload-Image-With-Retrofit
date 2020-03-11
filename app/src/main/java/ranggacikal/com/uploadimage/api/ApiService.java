package ranggacikal.com.uploadimage.api;

import ranggacikal.com.uploadimage.model.ResponseLoginUser;
import ranggacikal.com.uploadimage.model.ResponseRegisterUser;
import ranggacikal.com.uploadimage.model.ResponseUploadPhoto;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("LoginUser")
    Call<ResponseLoginUser> LoginUser(@Field("email") String email,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("Register")
    Call<ResponseRegisterUser> Register(@Field("username") String username,
                                        @Field("email") String email,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("UploadPhoto")
    Call<ResponseUploadPhoto> Upload(@Field("id") int id,
                                     @Field("photo") String photo);

}
