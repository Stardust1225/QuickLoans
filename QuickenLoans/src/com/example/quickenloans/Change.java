package com.example.quickenloans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Change extends Activity {

	SharedPreferences sharepre;
	SharedPreferences.Editor editor;

	EditText name, tele, email;
	Button savechange;
	Spinner career, education, marrage;
	Handler handle;
	@Override
	public void onCreate(Bundle save) {
		super.onCreate(save);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change);

		sharepre = getSharedPreferences("setting", Context.MODE_WORLD_WRITEABLE);
		editor = sharepre.edit();

		savechange = (Button) findViewById(R.id.change_save);

		name = (EditText) findViewById(R.id.change_name);
		tele = (EditText) findViewById(R.id.change_tele);
		email = (EditText) findViewById(R.id.change_email);
		
		name.setText(sharepre.getString("name", ""));
		tele.setText(sharepre.getString("tele", ""));
		email.setText(sharepre.getString("email",""));
		
		career=(Spinner)findViewById(R.id.change_career);
		education=(Spinner)findViewById(R.id.change_edu);
		marrage=(Spinner)findViewById(R.id.change_marr);
		
		
		List list = new ArrayList<String>();
		list.add("个体户");
		list.add("私企");
		list.add("国企");
		list.add("事业单位");
		list.add("政府机关");
		career.setAdapter(new ArrayAdapter(Change.this, R.layout.spinnerview, list));

		list = new ArrayList<String>();
		list.add("未婚");
		list.add("已婚");
		list.add("离异");
		marrage.setAdapter(new ArrayAdapter(Change.this, R.layout.spinnerview, list));

		list = new ArrayList<String>();
		list.add("小学");
		list.add("初中");
		list.add("高中");
		list.add("专科");
		list.add("本科");
		list.add("研究生");
		education.setAdapter(new ArrayAdapter(Change.this, R.layout.spinnerview, list));
		
		savechange.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				
				new Thread() {
					String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Change?";
					@Override
					public void run() {
						try {
							URL url = new URL(url1+"n="+name.getText().toString()+"&a="+sharepre.getString("account", "")+"&p="+sharepre.getString("password", "")
											+"&m="+sharepre.getString("MAC","")+"&edu="+String.valueOf(education.getSelectedItemPosition())
											+"&e="+email.getText().toString()+"&t="+tele.getText().toString()+"&i="+sharepre.getString("id", "")
											+"&n="+URLEncoder.encode(name.getText().toString(), "UTF-8")
											+"&c="+String.valueOf(career.getSelectedItemPosition())+"&marr="+String.valueOf(marrage.getSelectedItemPosition()));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s=buffer.readLine();
							Message msg=new Message();
							msg.what=Integer.parseInt(s);
							handle.sendMessage(msg);
						} catch (Exception e) {}
					}
				}.start();
			}	
		});
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==1){
					editor.putString("name", name.getText().toString());
					editor.putString("tele", tele.getText().toString());
					editor.putString("email", email.getText().toString());
					editor.putString("edu", String.valueOf(education.getSelectedItemPosition()));
					editor.putString("career",String.valueOf(career.getSelectedItemPosition()));
					editor.putString("marrage",String.valueOf(marrage.getSelectedItemPosition()));
					editor.commit();
					Toast.makeText(Change.this,"修改成功",Toast.LENGTH_SHORT).show();
					startActivity(new Intent(Change.this,Normal.class));
				}
				
				else
					Toast.makeText(Change.this,"修改失败",Toast.LENGTH_SHORT).show();
			}
		};
		
	}

}
