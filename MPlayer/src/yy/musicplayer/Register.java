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
				Toast.makeText(getApplicationContext(), "����ǰ��ƥ�䣡", Toast.LENGTH_SHORT).show();
			}
			else{
			//����һ��DatabaseHelper����
			DatabaseHelper dbHelper = new DatabaseHelper(Register.this,"musicplayer_user_db",2);
			//ֻ�е�����DatabaseHelper�����getReadableDatabase()������������getWritableDatabase()����֮�󣬲Żᴴ�������һ�����ݿ�
			SQLiteDatabase db = dbHelper.getWritableDatabase();	
	
			//����ContentValues����
			ContentValues values = new ContentValues();
			//��ö����в����ֵ�ԣ����м���������ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ��
			values.put("id", i++);
			values.put("name",username.getText().toString());
			values.put("password", password.getText().toString());
			//����insert�������Ϳ��Խ����ݲ��뵽���ݿ⵱��
			db.insert("user", null, values);
			Toast.makeText(this, "ע��ɹ���", Toast.LENGTH_SHORT).show();
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
