package com.example.quickenloans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Activity{
	
	TextView showlend,showborrow;
	ListView lend,borrow;
	Handler handle;
	List lenddeal,borrowdeal;
	List detaillenddeal,detailborrowdeal;
	SharedPreferences sharepre;
	SharedPreferences.Editor editor;
	@Override
	public void onCreate(Bundle save){
		super.onCreate(save);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history);
		
		sharepre = getSharedPreferences("setting", MODE_WORLD_WRITEABLE);
		editor = sharepre.edit();
		
		lenddeal=new ArrayList<String>();
		borrowdeal=new ArrayList<String>();
		
		lend=(ListView)findViewById(R.id.his_lenddeal);
		borrow=(ListView)findViewById(R.id.his_borrowdeal);
		
		showlend=(TextView)findViewById(R.id.his_showlend);
		showborrow=(TextView)findViewById(R.id.his_showborrow);
		
		lend.setVisibility(View.GONE);
		borrow.setVisibility(View.GONE);
		
		showlend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				lend.setVisibility(View.VISIBLE);
				borrow.setVisibility(View.GONE);
				new Thread(){
					@Override
					public void run(){
					try{
							String url1 = "http://" + sharepre.getString("ip","") + ":8080/Service/Lenddeal?";
							URL url = new URL(url1+"a="+sharepre.getString("account",""));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s="";
							String line="";
							lenddeal=new ArrayList<String>();
							detaillenddeal=new ArrayList<String>();
							int i,j;
							while((s=buffer.readLine())!=null)
							{
								line="";
								detaillenddeal.add(s);
								i=s.indexOf("&");
								j=s.indexOf("&",i+1);
								line+="数额:"+s.substring(i+1,j);
								i=s.indexOf("&",j+1);
								j=s.indexOf("&",i+1);
								line+="     利息：";
								switch(s.substring(i+1,j).charAt(0)){
									case '0':line+="0~0.3";break;
									case '1':line+="0.3~0.5";break;	
									case '2':line+="0.5~0.7";break;
									case '3':line+="0.7~1.0";break;
									case '4':line+="1.0~1.5";break;	
									case '5':line+="1.5~2.0";break;	
									case '6':line+="2.0~3.0";break;	
									case '7':line+="3.0~4.0";break;		
								}
								lenddeal.add(line);
							}
							handle.sendMessage(new Message());
						}catch(Exception e){}
					}
				}.start();
				
			}
		});
		
		lend.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int i,j;
				String s=(String) detaillenddeal.get(position);
				Builder build = new AlertDialog.Builder(History.this);
				View detailview= getLayoutInflater().inflate(R.layout.showdetail, null);
				build.setView(detailview);
				build.setCancelable(false);
				final TextView acc,mon,rat,ris,yea,kin;
				acc=(TextView)detailview.findViewById(R.id.detail_account);				
				mon=(TextView)detailview.findViewById(R.id.detail_money);
				rat=(TextView)detailview.findViewById(R.id.detail_rate);
				ris=(TextView)detailview.findViewById(R.id.detail_risk);
				yea=(TextView)detailview.findViewById(R.id.detail_year);
				kin=(TextView)detailview.findViewById(R.id.detail_kind);
				
				i=s.indexOf("&");
				j=s.indexOf("&",i+1);
				acc.setText(s.substring(0, i));
				mon.setText(s.substring(i+1, j));
				i=s.indexOf("&",j+1);
				final String syear=s.substring(j+1, i);
				switch(s.substring(j+1, i).charAt(0)){
					case '0':yea.setText("0~1");break;	
					case '1':yea.setText("1~3");break;	
					case '2':yea.setText("3~5");break;	
					case '3':yea.setText("5~7");break;	
					case '4':yea.setText("7~9");break;
					case '5':yea.setText("9~10");break;	
				}
				
				j=s.indexOf("&",i+1);
				final String srate=s.substring(i+1, j);
				switch(s.substring(i+1, j).charAt(0)){
					case '0':rat.setText("0~0.3");break;
					case '1':rat.setText("0.3~0.5");break;	
					case '2':rat.setText("0.5~0.7");break;
					case '3':rat.setText("0.7~1.0");break;
					case '4':rat.setText("1.0~1.5");break;	
					case '5':rat.setText("1.5~2.0");break;	
					case '6':rat.setText("2.0~3.0");break;	
					case '7':rat.setText("3.0~4.0");break;		
				}
				
				i=s.indexOf("&",j+1);
				ris.setText(s.substring(j+1, i));
				j=s.indexOf("&",i+1);
				final String skind=s.substring(i+1, j);
				switch(s.substring(i+1, s.length()).charAt(0)){
					case '0':kin.setText("工薪贷（个人）");break;
					case '1':kin.setText("净值贷（企业）");break;
					case '2':kin.setText("网商贷（个体商户）");break;
					case '3':kin.setText("卡易贷（个人）");break;
					case '4':kin.setText("教育贷（个人）");break;
				}
				final int statue=Integer.parseInt(s.substring(j+1, s.length()));
				
				TextView makedeal,dismiss;
				makedeal=(TextView)detailview.findViewById(R.id.detail_makedeal);
				dismiss=(TextView)detailview.findViewById(R.id.detail_dismiss);
				
				final AlertDialog dialog = build.create();
				if(statue==1){
					makedeal.setText("确认收到还款");
					makedeal.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							new Thread(){
								@Override
								public void run(){
								try{
									Time t=new Time();	
									t.setToNow();
									String time=String.valueOf(t.year)+String.valueOf(t.month+1)+String.valueOf(t.monthDay);
									String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Finishdeal?";
										URL url = new URL(url1+"a="+acc.getText().toString()+"&m="+mon.getText().toString()+"&ra="+srate
												+"&ri="+ris.getText().toString()+"&k="+skind+"&y="+syear+"&acc="+sharepre.getString("account","")
												+"&t="+time);
										HttpURLConnection conn = (HttpURLConnection) url.openConnection();
										conn.setRequestMethod("GET");
										conn.setReadTimeout(8000);
										conn.setReadTimeout(8000);
										InputStream in = conn.getInputStream();
								}catch(Exception e){}
								}
							}.start();
							Toast.makeText(History.this,"发送请求成功", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							
						}
						
					});
				}
				else{
					makedeal.setText("已收到还款");
					makedeal.setTextColor(Color.GRAY);
					makedeal.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

				}
				
				
				dialog.show();
				
				
				dismiss.setText("取消");
				dismiss.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				
			}
			
		});
		
		
		showborrow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				lend.setVisibility(View.GONE);
				borrow.setVisibility(View.VISIBLE);
				new Thread(){
					@Override
					public void run(){
					try{
							String url1 = "http://" + sharepre.getString("ip","") + ":8080/Service/Borrowdeal?";
							URL url = new URL(url1+"a="+sharepre.getString("account",""));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s="";
							String line="";
							borrowdeal=new ArrayList<String>();
							detailborrowdeal=new ArrayList<String>();
							int i,j;
							while((s=buffer.readLine())!=null)
							{
									line="";
									detailborrowdeal.add(s);
									i=s.indexOf("&");
									j=s.indexOf("&",i+1);
									line+="数额："+s.substring(i+1,j);
									i=s.indexOf("&",j+1);
									j=s.indexOf("&",i+1);
									switch(s.substring(i+1,j).charAt(0)){
										case '0':line+="     利息"+"0~0.3";break;
										case '1':line+="     利息"+"0.3~0.5";break;	
										case '2':line+="     利息"+"0.5~0.7";break;
										case '3':line+="     利息"+"0.7~1.0";break;
										case '4':line+="     利息"+"1.0~1.5";break;	
										case '5':line+="     利息"+"1.5~2.0";break;	
										case '6':line+="     利息"+"2.0~3.0";break;	
										case '7':line+="     利息"+"3.0~4.0";break;		
									}
									borrowdeal.add(line);
							}
							handle.sendMessage(new Message());
						}catch(Exception e){}
					}
				}.start();
			}
		});
		
		borrow.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int i,j;
				String s=(String) detailborrowdeal.get(position);
				Builder build = new AlertDialog.Builder(History.this);
				View detailview= getLayoutInflater().inflate(R.layout.showdetail1, null);
				build.setView(detailview);
				build.setCancelable(false);
				final TextView mon,rat,yea;			
				mon=(TextView)detailview.findViewById(R.id.detail1_money);
				rat=(TextView)detailview.findViewById(R.id.detail1_rate);
				yea=(TextView)detailview.findViewById(R.id.detail1_year);
				
				
				i=s.indexOf("&");
				j=s.indexOf("&",i+1);
				mon.setText(s.substring(i+1, j));
				i=s.indexOf("&",j+1);
				final String syear=s.substring(j+1, i);
				switch(s.substring(j+1, i).charAt(0)){
					case '0':yea.setText("0~1");break;	
					case '1':yea.setText("1~3");break;	
					case '2':yea.setText("3~5");break;	
					case '3':yea.setText("5~7");break;	
					case '4':yea.setText("7~9");break;
					case '5':yea.setText("9~10");break;	
				}
				
				j=s.indexOf("&",i+1);
				final String srate=s.substring(i+1, j);
				switch(s.substring(i+1, j).charAt(0)){
					case '0':rat.setText("0~0.3");break;
					case '1':rat.setText("0.3~0.5");break;	
					case '2':rat.setText("0.5~0.7");break;
					case '3':rat.setText("0.7~1.0");break;
					case '4':rat.setText("1.0~1.5");break;	
					case '5':rat.setText("1.5~2.0");break;	
					case '6':rat.setText("2.0~3.0");break;	
					case '7':rat.setText("3.0~4.0");break;		
				}
				
				i=s.indexOf("&",j+1);
				j=s.indexOf("&",i+1);
				final int statue=Integer.parseInt(s.substring(j+1, s.length()));
				
				TextView makedeal;
				makedeal=(TextView)detailview.findViewById(R.id.detail1_ok);

				
				final AlertDialog dialog = build.create();
				if(statue==1)
					makedeal.setText("未还款");
				else
					makedeal.setText("已还款");
					
				makedeal.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
				dialog.show();
			}			
		});
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				lend.setAdapter(new ArrayAdapter(History.this,R.layout.itemsytle,lenddeal));
				borrow.setAdapter(new ArrayAdapter(History.this,R.layout.itemsytle,borrowdeal));
			}
		};
	}
}
