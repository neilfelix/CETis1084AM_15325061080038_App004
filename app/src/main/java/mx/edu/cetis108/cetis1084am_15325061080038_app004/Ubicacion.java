package mx.edu.cetis108.cetis1084am_15325061080038_app004;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Created by Neil on 04/05/2017.
 */

public class Ubicacion {
    Alumno Alumno = null;
    String sUrl="";
    double Lat=0;
    double Lng=0;

    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    public Ubicacion(String aUrl, double Lat, double Lng, Alumno Alumno){
        this.Alumno=Alumno;
        this.sUrl=aUrl;
        this.Lat=Lat;
        this.Lng=Lng;
    }
    public String Nueva(){

        String aRespuesta="";
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("NoControl", Alumno.NoControl));
        nameValuePairs.add(new BasicNameValuePair("Lat", String.valueOf(Lat)));
        nameValuePairs.add(new BasicNameValuePair("Lng", String.valueOf(Lng)));

        try{
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost(sUrl);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response=httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is=entity.getContent();
            Log.e("pass 1", "connection success");
        }catch(Exception e){
            Log.e("Fail 1", e.toString());
        }
        try{
            BufferedReader reader= new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb=new StringBuilder();
            while((line=reader.readLine())!=null){
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success");
        }catch(Exception e){
            Log.e("Fail 2", e.toString());
        }
        try
        {
            JSONObject json_data = new JSONObject(result);
            code = (json_data.getInt("code"));

            if(code==1)
            {
                aRespuesta = "Ubicación registrada";
            }
        }
        catch (Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
        return aRespuesta;
    }
}
