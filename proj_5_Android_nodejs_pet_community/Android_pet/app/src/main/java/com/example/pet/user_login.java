package com.example.pet;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pet.api.ApiN;
import com.example.pet.dto.resD;
import com.example.pet.interfaces.NInterface;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class user_login extends AppCompatActivity {

    Toolbar myToolbar;
    EditText id_et;
    EditText password_et;
    InputMethodManager imm;
    TextView new_account_tv;
    Button login_bt;
    CheckBox remember_check_box;
    int nCurrentPermission=0;
    static final int PERMISSIONS_REQUEST = 0x0000001;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);
        OnCheckPermission();
        id_et = (EditText) findViewById(R.id.id_et);
        password_et = (EditText) findViewById(R.id.password_et);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);//툴바

        password_et.setImeOptions(EditorInfo.IME_ACTION_DONE);
        BtnOnEditerAction onEditorActionListener = new BtnOnEditerAction();
        password_et.setOnEditorActionListener(onEditorActionListener);// 키보드 내리기
    }

    class BtnOnEditerAction implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v.getId() == R.id.password_et && actionId == EditorInfo.IME_ACTION_DONE) {
                password_et.clearFocus();
                imm.hideSoftInputFromWindow(password_et.getWindowToken(), 0);
            }
            return false;
        }
    }//키보드 내리기

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar1, menu);
        return true;
    }//툴바 메뉴XML이랑 연결

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_tool:
                Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.suggestion_tool:
                Toast.makeText(this, "문의", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.exit_tool:
                Toast.makeText(this, "종료", Toast.LENGTH_SHORT).show();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }//툴바 클릭 이벤트들

    public void OnClicknewaccount(View v) {
        Intent intent = new Intent(this, user_new_account.class);
        startActivity(intent);

    }//회원가입 버튼 누를시 온클릭 이벤트

    public void OnClicklogin(View v) throws JSONException {
        NInterface nInterface = ApiN.getApiN().create(NInterface.class);
        Call<resD> call = nInterface.loginI(id_et.getText().toString(), password_et.getText().toString());
        call.enqueue(new Callback<resD>() {
            @Override
            public void onResponse(Call<resD> call, Response<resD> response) {
                resD resD = response.body();
                Log.d(TAG, "onResponse: 성공22 , 결과 \n" + resD.getCode());
                SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPref.edit();

                editor.putString("uID",id_et.getText().toString() );
                editor.commit();

                if(resD.getCode().equals("200")) {
                    Intent intent = new Intent(v.getContext(), board_normal.class);
                    startActivity(intent);
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "fail";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<resD> call, Throwable t) {
                Log.d(TAG, "onResponse: 실패"+t.getMessage());

            }

        });
        Toast.makeText(this, "id : " + id_et.getText().toString() + " password: " + password_et.getText().toString(), Toast.LENGTH_SHORT).show();

    }//로그인 버튼 누를 시 온클릭 이벤트

    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(password_et.getWindowToken(), 0);
    }

    public void OnCheckPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "앱 실행을 위한 권환이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST);
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "앱 실행을 위한 권환이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "앱 실행을 위한 권환이 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


}