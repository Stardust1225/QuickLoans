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
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {

	EditText account, password, againpassword, name, id, tele, email;
	Button next, moreinfo, register, back;
	ImageView qq, weibo, weixin, zhifubao;
	View v1, v2;
	LinearLayout layout;
	SharedPreferences sharepre;
	SharedPreferences.Editor editor;
	Spinner career, education, marrage;
	Handler handle;
	@Override
	public void onCreate(Bundle save) {

		super.onCreate(save);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		v1 = getLayoutInflater().inflate(R.layout.register1, null);
		v2 = getLayoutInflater().inflate(R.layout.register2, null);

		sharepre = getSharedPreferences("setting", Context.MODE_WORLD_WRITEABLE);
		editor = sharepre.edit();

		marrage = (Spinner) v2.findViewById(R.id.register_marrage);
		career = (Spinner) v2.findViewById(R.id.register_career);
		education = (Spinner) v2.findViewById(R.id.register_education);

		setContentView(v1);

		account = (EditText) v1.findViewById(R.id.register_account);
		password = (EditText) v1.findViewById(R.id.register_password);
		againpassword = (EditText) v1.findViewById(R.id.register_againpass);

		name = (EditText) v2.findViewById(R.id.register_name);
		id = (EditText) v1.findViewById(R.id.register_id);
		tele = (EditText) v1.findViewById(R.id.register_tele);
		email = (EditText) v2.findViewById(R.id.register_email);

		next = (Button) v1.findViewById(R.id.register_next);
		moreinfo = (Button) v2.findViewById(R.id.register_more);
		register = (Button) v2.findViewById(R.id.register_register);
		back = (Button) v2.findViewById(R.id.register_back);

		qq = (ImageView) v2.findViewById(R.id.register_qq);
		weixin = (ImageView) v2.findViewById(R.id.register_weixin);
		weibo = (ImageView) v2.findViewById(R.id.register_weibo);
		zhifubao = (ImageView) v2.findViewById(R.id.register_zhifubao);

		layout = (LinearLayout) v2.findViewById(R.id.register_other);
		layout.setVisibility(View.GONE);

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (account.getText().toString().equals("") | password.getText().toString().equals("")
						| againpassword.getText().toString().equals("") | id.getText().toString().equals("")
						| tele.getText().toString().equals(""))
					Toast.makeText(Register.this, "未输入完成", Toast.LENGTH_SHORT).show();
				else {
					if (!password.getText().toString().equals(againpassword.getText().toString()))
						Toast.makeText(Register.this, "两次密码不同ͬ", Toast.LENGTH_SHORT).show();
					else {
						if(tele.length()!=11&&tele.length()!=7)
							Toast.makeText(Register.this, "手机号码格式错误ͬ", Toast.LENGTH_SHORT).show();
						else{
							if(id.length()!=18)
								Toast.makeText(Register.this, "身份证格式错误ͬ", Toast.LENGTH_SHORT).show();
							else{
								editor.putString("tele", tele.getText().toString());
								editor.putString("id", id.getText().toString());
								editor.putString("account", account.getText().toString());
								editor.putString("password", password.getText().toString());
								editor.commit();
								v2.setAnimation(AnimationUtils.loadAnimation(Register.this, android.R.anim.fade_in));
								setContentView(v2);
							}
						}
					}
				}
			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v1.setAnimation(AnimationUtils.loadAnimation(Register.this, android.R.anim.fade_in));
				setContentView(v1);
				account.setText(sharepre.getString("account", ""));
				password.setText("");
				againpassword.setText("");
			}
		});

		moreinfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (layout.getVisibility() == View.GONE)
					layout.setVisibility(View.VISIBLE);
				else
					layout.setVisibility(View.GONE);
			}
		});

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.putString("name", name.getText().toString());
				editor.putString("email", email.getText().toString());
				editor.putString("edu", String.valueOf(education.getSelectedItemPosition()));
				editor.putString("career",String.valueOf(career.getSelectedItemPosition()));
				editor.putString("marrage",String.valueOf(marrage.getSelectedItemPosition()));
				editor.commit();
				
				new Thread() {
					String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Register?";
					@Override
					public void run() {
						try {
							URL url = new URL(url1+"a="+sharepre.getString("account","")+"&p="+sharepre.getString("password","")
											+"&m="+sharepre.getString("MAC","")+"&edu="+String.valueOf(education.getSelectedItemPosition())
											+"&e="+sharepre.getString("email","")+"&t="+sharepre.getString("tele","")
											+"&i="+sharepre.getString("id","")+"&n="+URLEncoder.encode(name.getText().toString(), "UTF-8")
											+"&c="+String.valueOf(career.getSelectedItemPosition())+"&marr="+String.valueOf(marrage.getSelectedItemPosition()));
							Log.i("zhangxin", url.toString());
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

		List list = new ArrayList<String>();
		list.add("个体户");
		list.add("私企");
		list.add("国企");
		list.add("事业单位");
		list.add("政府机关");
		career.setAdapter(new ArrayAdapter(Register.this, R.layout.spinnerview, list));

		list = new ArrayList<String>();
		list.add("未婚");
		list.add("已婚");
		list.add("离异");
		marrage.setAdapter(new ArrayAdapter(Register.this, R.layout.spinnerview, list));

		list = new ArrayList<String>();
		list.add("小学");
		list.add("初中");
		list.add("高中");
		list.add("专科");
		list.add("本科");
		list.add("研究生");
		education.setAdapter(new ArrayAdapter(Register.this, R.layout.spinnerview, list));
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				switch(msg.what){
					case 1:Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
							startActivity(new Intent(Register.this,Normal.class));
							finish();
							break;
					case 2:Toast.makeText(Register.this,"已注册",Toast.LENGTH_SHORT).show();
							editor.putString("account","");
							editor.putString("password","");
							editor.commit();
							startActivity(new Intent(Register.this,First.class));
							finish();
							break;
				}
			}
		};
	}

}
