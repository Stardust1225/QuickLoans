package com.example.quickenloans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class First extends Activity {

	Mytextview openlogin, register;
	Button login, cancel;
	Handler handler;
	TextView account, password;
	CheckBox remeacc, autologin;
	SharedPreferences sharepre;
	SharedPreferences.Editor editor;
	AlertDialog dialog;
	TextView setip;
	EditText ip;
	Handler handle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first);

		openlogin = (Mytextview) findViewById(R.id.first_openlogin);
		register = (Mytextview) findViewById(R.id.first_register);

		sharepre = getSharedPreferences("setting", MODE_WORLD_WRITEABLE);
		editor = sharepre.edit();
		
		editor.putString("ip","10.133.9.207");
		editor.commit();

		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		
		/*
		editor.putString("MAC","0");
		String mac1=info.getMacAddress();
		String mac=mac1.replace(":","");
		editor.putString("MAC",mac);
		editor.commit();
		*/
		
		openlogin.setVisibility(View.INVISIBLE);
		register.setVisibility(View.INVISIBLE);
		
		setip=(TextView)findViewById(R.id.first_setip);
		ip=(EditText)findViewById(R.id.first_ip);
		ip.setVisibility(View.GONE);
		
		setip.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(ip.getVisibility()==View.GONE){
					ip.setVisibility(View.VISIBLE);
					setip.setText("确定");
				}
				else{
					editor.putString("ip", ip.getText().toString());
					editor.commit();
					ip.setVisibility(View.GONE);
					setip.setText("设置ip");
				}
			}
		});
		

		new Thread() {
			@Override
			public void run() {
					if(sharepre.getString("autologin","").equals("true")){
						try{
							String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Quicklogin?";
							URL url = new URL(url1+"a="+sharepre.getString("account","")+"&p="+sharepre.getString("password","")
									+"&i="+sharepre.getString("MAC",""));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s=buffer.readLine();
							Message msg=new Message();
							msg.what=Integer.parseInt(s);
							Thread.sleep(3000);
							handle.sendMessage(msg);
						}catch(Exception e){}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
				handler.sendMessage(new Message());
			}

		}.start();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				openlogin.setVisibility(View.VISIBLE);
				register.setVisibility(View.VISIBLE);
			}
		};

		openlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Builder build = new AlertDialog.Builder(First.this);

				View view = getLayoutInflater().inflate(R.layout.login, null);
				build.setView(view);
				build.setCancelable(false);

				login = (Button) view.findViewById(R.id.login_login);
				cancel = (Button) view.findViewById(R.id.login_cancel);

				remeacc = (CheckBox) view.findViewById(R.id.login_remeacc);
				autologin = (CheckBox) view.findViewById(R.id.login_autologin);

				account = (EditText) view.findViewById(R.id.login_account);
				password = (EditText) view.findViewById(R.id.login_password);

				dialog = build.create();
				dialog.show();

				login.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String s = account.getText().toString();
						if (s.equals(""))
							Toast.makeText(First.this, "用户名为空", Toast.LENGTH_SHORT).show();
						else {
							editor.putString("account", s);
							editor.commit();
							s = password.getText().toString();
							if (s.equals(""))
								Toast.makeText(First.this, "密码为空", Toast.LENGTH_SHORT).show();
							else {
								editor.putString("password", s);
								editor.commit();
								if (remeacc.isChecked())
									editor.putString("remeacc", "true");
								if (autologin.isChecked())
									editor.putString("autologin", "true");
								editor.commit();
								new Thread(){
									String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Login?";

									@Override
									public void run() {
										try {
											URL url = new URL(url1+"a="+sharepre.getString("account","")+"&p="+sharepre.getString("password","")
															+"&i="+sharepre.getString("MAC",""));
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
						}
						
					}
				});
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(First.this, Register.class));
			}
		});
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				switch(msg.what){
					case 1:Toast.makeText(First.this, "登陆成功", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(First.this,Normal.class));
							finish();
							new Thread(){
								String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Getinfo?";
								@Override
								public void run() {
									try {
										URL url = new URL(url1+"a="+sharepre.getString("account",""));
										HttpURLConnection conn = (HttpURLConnection) url.openConnection();
										InputStream in = conn.getInputStream();
										BufferedReader buffer = new BufferedReader(new InputStreamReader(in,"utf-8"));
										String s;
										s=buffer.readLine();
										editor.putString("id",s);
										s=buffer.readLine();
										editor.putString("email",s);
										s=buffer.readLine();
										editor.putString("tele",s);
										s=buffer.readLine();
										editor.putString("name",s);
										s=buffer.readLine();
										editor.putString("edu",s);
										s=buffer.readLine();
										editor.putString("career",s);
										s=buffer.readLine();
										editor.putString("marrage",s);
										editor.commit();
									} catch (Exception e) {}
								}
							}.start();
							break;
					case 2:Toast.makeText(First.this, "密码错误", Toast.LENGTH_SHORT).show();
							break;
					case 3:Toast.makeText(First.this, "用户名不存在", Toast.LENGTH_SHORT).show();
							break;
					case 4:Toast.makeText(First.this, "用户已登陆", Toast.LENGTH_SHORT).show();
							break;
				}
			}
		};

	}

}
