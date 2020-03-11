package ranggacikal.com.uploadimage.model;


import com.google.gson.annotations.SerializedName;


public class ResponseUploadPhoto{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("sukses")
	private String sukses;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSukses(String sukses){
		this.sukses = sukses;
	}

	public String getSukses(){
		return sukses;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUploadPhoto{" + 
			"pesan = '" + pesan + '\'' + 
			",sukses = '" + sukses + '\'' + 
			"}";
		}
}