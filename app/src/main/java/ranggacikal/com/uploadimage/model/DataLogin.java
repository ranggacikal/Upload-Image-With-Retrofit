package ranggacikal.com.uploadimage.model;


import com.google.gson.annotations.SerializedName;


public class DataLogin{

	@SerializedName("password")
	private String password;

	@SerializedName("photo")
	private String photo;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"DataLogin{" + 
			"password = '" + password + '\'' + 
			",photo = '" + photo + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}