package com.denip.arcon;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kaan ERKOÇ on 20.04.2016.
 */
public class FileUploadTask extends AsyncTask<String,Integer,String> {

    Context context;
    int serverResponseCode = 0;
    private String SERVER_URL = "http://94.54.71.45/appstract/arcon/getcrashreports.php";
    private String selectedFilePath="/sdcard/nurolTech/dir/deneme.json";
    private String filename,filedir;
    HttpURLConnection connection;
    DataOutputStream dataOutputStream;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public FileUploadTask(Context ctxt, String fileDir, String fileName){
    	System.out.println("asssssssssssssssssssss");
        this.filedir=fileDir;
        this.filename=fileName;
        this.selectedFilePath=filename;
        this.context=ctxt;
    }
    public FileUploadTask(String fileDir, String fileName){

        this.filedir=fileDir;
        this.filename=fileName;
        this.selectedFilePath=filename;

    }
    int bytesRead,bytesAvailable,bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;
    File file;



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        file = new File(filedir,filename);
        if(!file.exists()){
            Log.e("FILE UPLOAD","Dosya bulunamadý");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {

        try{
            File f = new File(filedir+filename);
            FileInputStream fileInputStream = new FileInputStream(f);
            Log.e("FileSize:",""+f.getName());
            Log.e("asd", selectedFilePath);
            URL url = new URL(SERVER_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//Allow Inputs
            connection.setDoOutput(true);//Allow Outputs
            connection.setUseCaches(false);//Don't use a cached Copy
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("uploaded_file",filename);

            //creating new dataoutputstream
            dataOutputStream = new DataOutputStream(connection.getOutputStream());

            //writing bytes to data outputstream
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + selectedFilePath + "\"" + lineEnd);

            dataOutputStream.writeBytes(lineEnd);

            //returns no. of bytes present in fileInputStream
            bytesAvailable = fileInputStream.available();
            //selecting the buffer size as minimum of available bytes or 1 MB
            bufferSize = Math.min(bytesAvailable,maxBufferSize);
            //setting the buffer as byte array of size of bufferSize
            buffer = new byte[bufferSize];

            //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
            bytesRead = fileInputStream.read(buffer,0,bufferSize);

            //loop repeats till bytesRead = -1, i.e., no bytes are left to read
            while (bytesRead > 0){
                //write the bytes read from inputstream
                dataOutputStream.write(buffer,0,bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer,0,bufferSize);
            }

            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();

            Log.i("SERVER-RESPONSE", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

            //response code of 200 indicates the server status OK
            if(serverResponseCode == 200){

            }
            //closing the input and output streams
            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("FileUploadTask:","File Not Found : " + filedir + "///" +filename);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("FileUploadTask:","URL Error!!");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileUploadTask:","Cannot Read/Write File");
        }

        return String.valueOf(serverResponseCode);
    }
}
