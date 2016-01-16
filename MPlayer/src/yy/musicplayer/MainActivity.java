package yy.musicplayer;


import yy.database.DatabaseHelper;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener {
	
	private EditText username;
	private EditText password;
	private Button log_in;
	private TextView err;
	private TextView reg;
	
	private Button play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		log_in = (Button) findViewById(R.id.log_in_btn);		
		log_in.setOnClickListener(this);
		reg = (TextView) findViewById(R.id.register);
		reg.setOnClickListener(this);
		err = (TextView) findViewById(R.id.error);
		err.setOnClickListener(this);		
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.log_in_btn:
			logIn();
			break;
		case R.id.error:
			err();
			break;
		case R.id.register:
			Intent in = new Intent();
			in.setClass(this, Register.class);
			startActivity(in);
			break;
		case R.id.play:
			startActivity(new Intent(this,PlayerActivity.class));
			
		default:
			break;
		}
		
	}

	private void logIn() {	
		String un = String.valueOf(username.getText());
		String pw = String.valueOf(password.getText());
		DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this,"musicplayer_user_db",2);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("user", new String[]{"name","password"},"name=? and password=?", new String[]{un,pw}, null, null, null);
		if (cursor.moveToFirst() == true){
			Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, FrontActivity.class);
			startActivity(intent);
			}	
		else 
		{
			Toast.makeText(getApplicationContext(), "uncorrect", Toast.LENGTH_SHORT).show();				
		}			
	}
	
	private void err() {
		Uri uri = Uri.parse("smsto://15997447479");
		Intent it = new Intent(Intent.ACTION_SENDTO,uri);
		it.putExtra("sms_body", "我忘记密码了，可以找回么？");
		startActivity(it);	
	}
}
