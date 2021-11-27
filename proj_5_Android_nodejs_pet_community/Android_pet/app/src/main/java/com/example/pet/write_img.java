package com.example.pet;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pet.api.ApiN;
import com.example.pet.dto.postD;
import com.example.pet.dto.resD;
import com.example.pet.interfaces.NInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class write_img extends AppCompatActivity implements View.OnClickListener {
    TextView writer_tv;
    EditText title_et,content_et;
    ImageView imageview;
    Button reg_button;
    NInterface nInterface = ApiN.getApiN().create(NInterface.class);
    String img_url ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_img_activity);
        firstInit();
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String writer=sharedPref.getString("uID", "none");
        writer_tv.setText(writer);

    }

    public void firstInit() {
        title_et = (EditText) findViewById(R.id.title_et);
        writer_tv = (TextView) findViewById(R.id.writer_tv);
        content_et = (EditText) findViewById(R.id.content_et);
        reg_button = (Button) findViewById(R.id.reg_button);
        imageview = (ImageView)findViewById(R.id.imageView);

        reg_button.setOnClickListener(this);
        imageview.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_button:
                File file = new File(img_url);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName()+".jpg", requestFile);
                RequestBody title = RequestBody.create(MediaType.parse("text/plain"),title_et.getText().toString());
                RequestBody content = RequestBody.create(MediaType.parse("text/plain"),content_et.getText().toString());
                RequestBody writer = RequestBody.create(MediaType.parse("text/plain"),writer_tv.getText().toString());
                Call<resD> call2 = nInterface.postiI(body,title,content,writer);

                call2.enqueue(new Callback<resD>() {
                    @Override
                    public void onResponse(Call<resD> call2, Response<resD> response) {
                        resD resD = response.body();
                        Log.d(TAG, "글 쓰기: 성공 , 결과 \n" + resD);
                        Call<ArrayList<postD>> call = nInterface.lookupI(1,10);
                        Intent intent = new Intent(v.getContext(), board_img.class);
                        startActivity(intent);

                    }
                    @Override
                    public void onFailure(Call<resD> call2, Throwable t) {
                        Log.d(TAG, "글 쓰기: 실패"+t.getMessage());
                    }
                });
                break;
            case R.id.imageView:

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
        }

    }
    ActivityResultLauncher<Intent> launcher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.e(TAG, "result : " + result);
                        Intent intent = result.getData();
                        Log.e(TAG, "intent : " + intent);
                        Uri uri = intent.getData();
                        Log.e(TAG, "uri : " + uri);
//                        imageview.setImageURI(uri);
                        Glide.with(write_img.this)
                                .load(uri)
                                .into(imageview);
                        img_url=createCopyAndReturnRealPath(write_img.this,uri);
                        //new AlertDialog.Builder(write_img.this).setMessage(uri.toString()+"\n"+img_url).create().show();
                        Log.e(TAG, "img_url : " + img_url);
                    }
                }
            });
    @Nullable
    public static String createCopyAndReturnRealPath(@NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;
        // 파일 경로를 만듬
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();
        File file = new File(filePath);
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }
        //String r = file.getAbsolutePath()+".jpg";
        return file.getAbsolutePath();
    }


}