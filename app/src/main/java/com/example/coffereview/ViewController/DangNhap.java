package com.example.coffereview.ViewController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffereview.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DangNhap extends AppCompatActivity implements  View.OnClickListener, FirebaseAuth.AuthStateListener, GoogleApiClient.OnConnectionFailedListener {
    Button dk;
    Button btndangnhap;
    LoginButton btfb;
    SignInButton btgoogle;
    Button btquenmk;
    TextView tvfaildn;
    EditText taikhoandangnhap, matkhaudangnhap;
    ProgressDialog progressDialog;
    GoogleApiClient apiClient;
    FirebaseFirestore db;
    public static int codegoogle = 1000;
    // Check xem dn bang Google hay  FB
    public static int kiemtradangnhap = 0;
    FirebaseAuth firebaseAuth;
    LoginManager loginManager;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LoginManager.getInstance().logOut();
//       FirebaseAuth.getInstance().signOut();

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_dang_nhap);
        db = FirebaseFirestore.getInstance();
        btquenmk = findViewById(R.id.btquenmk);
        btquenmk.setOnClickListener(this);
        btgoogle  = findViewById(R.id.btgoogle);
        btndangnhap = findViewById(R.id.btndangnhap);
        progressDialog = new ProgressDialog(this);
        taikhoandangnhap = findViewById(R.id.edemail);
        matkhaudangnhap = findViewById(R.id.edmk);
        btndangnhap.setOnClickListener(this);
        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        tvfaildn = findViewById(R.id.tvfaildn);
        callbackManager = CallbackManager.Factory.create();
        btfb = findViewById(R.id.btfb);
        btfb.setReadPermissions("email","public_profile");
   //     btndangnhap.setOnClickListener(this);
        dk = findViewById(R.id.btchuyendangky);
        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
                finish();
            }
        });
        btfb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            // Phai khai bao trong ACtivityResuil moi kich hoat dong nay
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Đăng nhập thành công
                kiemtradangnhap = 2;
                String tokenId = loginResult.getAccessToken().getToken();
                KiemTraDangNhap(null,tokenId);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {


            }
        });
        btgoogle.setOnClickListener(this);
        //2.
        getinfor();
    }
    // Hàm lấy thông tin từ GOOGLE
    private  void getinfor()
    {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Gọi API
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                // Them API vao
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

    }

    private void LoginWithEmailPass()
    {
        String email = taikhoandangnhap.getText().toString();
        String matkhau = matkhaudangnhap.getText().toString();
         firebaseAuth.signInWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful())
                 {
                     progressDialog.dismiss();
                 }
                 else
                 {
                     tvfaildn.setText(" Email Hoặc Mật Khẩu Không Hợp Lệ");
                     progressDialog.dismiss();
                 }
             }
         });

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        String userid = FirebaseAuth.getInstance().getUid();
        switch (id){
            case R.id.btgoogle:
                    logingoogle(apiClient);
                break;
            case R.id.btndangnhap:
                String mailinput = taikhoandangnhap.getText().toString();
                String pass = matkhaudangnhap.getText().toString();
                boolean resultcheck = checkmail(mailinput);
                if(mailinput.equals("") == true ||pass.equals("") == true )
                {
                    tvfaildn.setText("Email Hoặc Mật Khẩu Không Hợp Lệ");
                }
                else if(resultcheck)
                {
                progressDialog.setMessage("Đang xử lý, chờ xíu");
                progressDialog.show();
                LoginWithEmailPass();
                }else
                {
                    tvfaildn.setText("Email Hoặc Mật Khẩu Không Hợp Lệ");
                }
                break;
            case R.id.btquenmk:
                quenmatkhau();
                break;
        }
    }
    //Mo giao dien dang nhap google
    private void logingoogle(GoogleApiClient apiClient)
    {
        kiemtradangnhap = 1;
        Intent intent  = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(intent, codegoogle);
    }
    private void quenmatkhau()
    {
        Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == codegoogle)
        {
            if(resultCode == RESULT_OK)
            {
                // Lấy Thông Tin Đăng Nhập (Lay Token google)

                GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = googleSignInResult.getSignInAccount();
                String tokenid = account.getIdToken();
                KiemTraDangNhap(tokenid,null);
            }
        }else
        {
            callbackManager.onActivityResult(requestCode, resultCode,data);
        }
    }
    private void KiemTraDangNhap(String tokenid, String tokenId)
    {
        if(kiemtradangnhap == 1)
        {

            AuthCredential credential = GoogleAuthProvider.getCredential(tokenid, null);
            // Kiem Tra Dang Nhap Thanh Cong ??
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        createdocument();
                    }
                }
            });

        }else if(kiemtradangnhap == 2)
        {

                AuthCredential credential = FacebookAuthProvider.getCredential(tokenId);
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        createdocument();
                    }
                });
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser  user = firebaseAuth.getCurrentUser();
        if(user != null)
        {
            Intent intent = new Intent(this, TrangChu.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private boolean checkmail(String inputmail)
    {
        return Patterns.EMAIL_ADDRESS.matcher(inputmail).matches();
    }

    private void createdocument()
    {
        Map<String, Object> data = new HashMap<>();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        data.put("ten", "");
        data.put("chieucao", "");
        data.put("cannag", "");
        db.collection("infomation")
                .document(id).set(data);

    }
}
