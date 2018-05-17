package edu.cs.okan.examquation3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText username,surname,name,password;
    Button btnSave,btnEnter,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.edtUserName);
        name=(EditText)findViewById(R.id.edtName);
        surname=(EditText)findViewById(R.id.edtSurName);
        password=(EditText)findViewById(R.id.edtPass);
        
        btnEnter=(Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter();                
            }
        });

        btnExit=(Button) findViewById(R.id.btnClear);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temizle();
            }
        });
        
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        Database db = new Database(getApplicationContext());
        String user_name=username.getText().toString();
        String nametxt=name.getText().toString();
        String surnametxt=surname.getText().toString();
        String passwordtxt=password.getText().toString();
        if(user_name.isEmpty() || nametxt.isEmpty() || surnametxt.isEmpty() || passwordtxt.isEmpty())
            Toast.makeText(this,"Lütfen tüm alanları doldurunuz",Toast.LENGTH_LONG).show();
        else {
            db.UserEkle(user_name, nametxt, surnametxt, passwordtxt);
            db.close();
        }
      temizle();

    }

    private void temizle() {
        username.setText("");
        name.setText("");
        surname.setText("");
        password.setText("");
    }

    private void enter() {
        String user_name=username.getText().toString();
        String passwordtxt=password.getText().toString();
        if(user_name.isEmpty() ||  passwordtxt.isEmpty())
            Toast.makeText(this,"Lütfen tüm alanları doldurunuz",Toast.LENGTH_LONG).show();
        else {
            Database db = new Database(getApplicationContext());
            HashMap<String, String> map = db.getUser(user_name, passwordtxt);
            if (map.isEmpty())
                Toast.makeText(this, "Bu Kayıt Bulunamadı", Toast.LENGTH_LONG).show();
            else {

                username.setText(map.get("username"));
                name.setText(map.get("name"));
                surname.setText(map.get("surname"));
                password.setText(map.get("password"));
                Toast.makeText(this, "Giriş Başarılı", Toast.LENGTH_LONG).show();

            }
        }
    }
}
