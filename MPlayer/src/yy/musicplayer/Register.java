package yy.musicplayer;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import yy.database.DatabaseHelper;

public class Register extends Activity implements OnClickListener{
	
	private EditText username;
	private EditText password;
	private EditText repassword;
	private EditText address;
	private Button registe;
	private static int i = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		username = (EditText)findViewById(R.id.rname);
		password = (EditText) findViewById(R.id.rword1);
		repassword = (EditText) findViewById(R.id.rword2);
		registe = (Button) findViewById(R.id.registe);
		registe.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		//Toast.makeText(this, "-------------->start!", Toast.LENGTH_SHORT).show();		
		switch (v.getId()) {
		case R.id.registe :
			if (!(password.getText().toString()).equals(repassword.getText().toString())) {
				Toast.makeText(getApplicationContext(), "密码前后不匹配！", Toast.LENGTH_SHORT).show();
			}
			else{
			//创建一个DatabaseHelper对象
			DatabaseHelper dbHelper = new DatabaseHelper(Register.this,"musicplayer_user_db",2);
			//只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
			SQLiteDatabase db = dbHelper.getWritableDatabase();	
	
			//生成ContentValues对象
			ContentValues values = new ContentValues();
			//想该对象当中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
			values.put("id", i++);
			values.put("name",username.getText().toString());
			values.put("password", password.getText().toString());
			//调用insert方法，就可以将数据插入到数据库当中
			db.insert("user", null, values);
			Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
			Intent inte = new Intent();
			inte.setClass(this, MainActivity.class);
			startActivity(inte);
			finish();
			}
			break;

		default:
			break;
		}
	}
	
}
