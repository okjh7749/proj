package com.example.pet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet.api.ApiN;
import com.example.pet.dto.userD;
import com.example.pet.interfaces.NInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class user_new_account extends AppCompatActivity implements View.OnClickListener{

    Toolbar myToolbar;
    EditText new_id_et;
    EditText new_password_et;
    EditText new_password2_et;
    RadioGroup gender_rg;
    Button new_account_bt;
    EditText year_et;
    LinearLayout ll;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_new_account_activity);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        new_id_et=(EditText)findViewById(R.id.new_id_et);
        new_password_et=(EditText)findViewById(R.id.new_password_et);
        new_password2_et=(EditText)findViewById(R.id.new_password2_et);
        new_account_bt=(Button)findViewById(R.id.new_account_bt);
        new_account_bt.setOnClickListener(this);

        BtnOnEditerAction onEditorActionListener = new BtnOnEditerAction();
        new_password2_et.setOnEditorActionListener(onEditorActionListener);// 키보드 내리기
        ll = (LinearLayout)findViewById(R.id.ll);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        
        //RadioButton rb =(RadioButton) findViewById((gender_rg.getCheckedRadioButtonId()));
    }

    class BtnOnEditerAction implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v.getId() == R.id.password_et && actionId == EditorInfo.IME_ACTION_DONE) {
                new_password2_et.clearFocus();
                imm.hideSoftInputFromWindow(new_password2_et.getWindowToken(), 0);
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
    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(ll.getWindowToken(), 0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_account_bt:
                NInterface nInterface = ApiN.getApiN().create(NInterface.class);
                Call<userD> call = nInterface.joinI(new_id_et.getText().toString(), new_password_et.getText().toString(), new_password2_et.getText().toString());
                call.enqueue(new Callback<userD>() {
                    @Override
                    public void onResponse(Call<userD> call, Response<userD> response) {
                        userD userd = response.body();
                        Intent intent = new Intent(v.getContext(), user_login.class);
                        startActivity(intent);
                        Log.d(TAG, "onResponse: 성공 , 결과 \n" + userd.toString());
                    }

                    @Override
                    public void onFailure(Call<userD> call, Throwable t) {
                        Log.d(TAG, "onResponse: 실패"+t.getMessage());

                    }

                });

        }

    }
}