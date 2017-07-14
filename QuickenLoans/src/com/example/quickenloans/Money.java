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
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Money extends Activity {

	SharedPreferences sharepre;
	SharedPreferences.Editor editor;
	View mon1, mon2, mon3;
	Button mon12,  mon21 ;
	TextView showsuccess, showaddmoney,getmoney,sendrequest,sendrequest1,flash,flash1;
	TextView  showlendmoney,showsuccess1,welcome;
	LinearLayout addmoney, success,lendmoney,success1;
	EditText money,money1;
	Spinner rate, year, risk,rate1,year1,style;
	ListView successdeal,successdeal1;
	List matchlist,matchlist1,detaillist,detaillist1;
	Handler handle;
	@Override
	public void onCreate(Bundle save) {
		super.onCreate(save);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		sharepre = getSharedPreferences("setting", Context.MODE_WORLD_WRITEABLE);
		editor = sharepre.edit();
		
		matchlist=new ArrayList<String>();
		matchlist1=new ArrayList<String>();
		
		mon1 = getLayoutInflater().inflate(R.layout.money1, null);
		mon2 = getLayoutInflater().inflate(R.layout.money2, null);

		setContentView(mon1);

		mon12 = (Button) mon1.findViewById(R.id.mon1_mon2);
		mon21 = (Button) mon2.findViewById(R.id.mon2_mon1);
		
		money=(EditText)mon1.findViewById(R.id.mon1_money);
		money1=(EditText)mon2.findViewById(R.id.mon2_money);
		
		getmoney=(TextView)mon2.findViewById(R.id.mon2_leftmoney);
		welcome=(TextView)mon1.findViewById(R.id.mon1_welcome);
		welcome.setText("欢迎你，"+sharepre.getString("account",""));
		
		addmoney = (LinearLayout) mon1.findViewById(R.id.mon1_addmoney);
		success = (LinearLayout) mon1.findViewById(R.id.mon1_success);
		addmoney.setVisibility(View.GONE);
		
		lendmoney = (LinearLayout) mon2.findViewById(R.id.mon2_lendmoney);
		success1 = (LinearLayout) mon2.findViewById(R.id.mon2_success);
		lendmoney.setVisibility(View.GONE);
		

		mon12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mon2.setAnimation(AnimationUtils.loadAnimation(Money.this, android.R.anim.fade_in));
				setContentView(mon2);
			}
		});

		mon21.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mon1.setAnimation(AnimationUtils.loadAnimation(Money.this, android.R.anim.fade_in));
				setContentView(mon1);
			}
		});
		showsuccess = (TextView) mon1.findViewById(R.id.mon1_showsuccess);
		showsuccess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addmoney.setVisibility(View.GONE);
				success.setVisibility(View.VISIBLE);	
				successdeal.setAdapter(new ArrayAdapter(Money.this,R.layout.itemsytle,matchlist));
					
			}

		});

		showaddmoney = (TextView) mon1.findViewById(R.id.mon1_showaddmoney);
		showaddmoney.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (addmoney.getVisibility() == View.VISIBLE) {
					addmoney.setVisibility(View.GONE);
					success.setVisibility(View.VISIBLE);
				} else {
					addmoney.setVisibility(View.VISIBLE);
					success.setVisibility(View.GONE);
				}
			}

		});
		
		List list=new ArrayList<String>();
		list.add("0~0.3");
		list.add("0.3~0.5");
		list.add("0.5~0.7");
		list.add("0.5~0.7");
		list.add("0.7~1.0");
		list.add("1.0~1.5");
		list.add("1.5~2.0");
		list.add("2.0~3.0");
		list.add("3.0~4.0");	
		ArrayAdapter<String> adapter = new ArrayAdapter(Money.this,R.layout.spinnerview,list);
		adapter.setDropDownViewResource(R.layout.spinnerview); 
		rate=(Spinner)mon1.findViewById(R.id.mon1_rate);
		rate.setAdapter(adapter);
		rate1=(Spinner)mon2.findViewById(R.id.mon2_rate);
		rate1.setAdapter(adapter);
		
		list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");
		adapter = new ArrayAdapter(Money.this,R.layout.spinnerview,list);
		adapter.setDropDownViewResource(R.layout.spinnerview); 
		risk=(Spinner)mon1.findViewById(R.id.mon1_risk);
		risk.setAdapter(adapter);
		
		list=new ArrayList<String>();
		list.add("0~1");
		list.add("1~3");
		list.add("3~5");
		list.add("5~7");
		list.add("7~9");
		list.add("9~10");
		adapter = new ArrayAdapter(Money.this,R.layout.spinnerview,list);
		adapter.setDropDownViewResource(R.layout.spinnerview); 
		year=(Spinner)mon1.findViewById(R.id.mon1_year);
		year.setAdapter(adapter);
		year1=(Spinner)mon2.findViewById(R.id.mon2_year);
		year1.setAdapter(adapter);
		
		
		successdeal=(ListView)mon1.findViewById(R.id.mon1_successdeal);
		successdeal1=(ListView)mon2.findViewById(R.id.mon2_successdeal);

		
		list=new ArrayList<String>();
		list.add("工薪贷（个人）");
		list.add("净值贷（企业）");
		list.add("网商贷（个体商户）");
		list.add("卡易贷（个人）");
		list.add("教育贷（个人）");
		adapter = new ArrayAdapter(Money.this,R.layout.spinnerview,list);
		adapter.setDropDownViewResource(R.layout.spinnerview); 
		style=(Spinner)mon2.findViewById(R.id.mon2_style);
		style.setAdapter(adapter);
		
		sendrequest=(TextView)mon1.findViewById(R.id.mon1_sendrequest);
		sendrequest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run(){
					try{
							String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Requestdeal?";
							URL url = new URL(url1+"a="+sharepre.getString("account","")+"&m="+money.getText().toString()
									+"&y="+String.valueOf(year.getSelectedItemId())+"&ra="+String.valueOf(rate.getSelectedItemId())
									+"&ri="+String.valueOf(risk.getSelectedItemId()+1)+"&k="+String.valueOf(style.getSelectedItemId()));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s="";
							String line="";
							matchlist=new ArrayList<String>();
							detaillist=new ArrayList<String>();
							int i,j;
							while((s=buffer.readLine())!=null)
							{
									line="";
									detaillist.add(s);
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
									matchlist.add(line);
							}	
						}catch(Exception e){}
					}
				}.start();
				Toast.makeText(Money.this, "发送请求成功", Toast.LENGTH_SHORT).show();
				addmoney.setVisibility(View.GONE);
				success.setVisibility(View.VISIBLE);
			}
		});
		
		flash=(TextView)mon1.findViewById(R.id.mon1_flash);
		flash.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run(){
					try{
							String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Requestdeal?";
							URL url = new URL(url1+"a="+sharepre.getString("account","")+"&m="+money.getText().toString()
									+"&y="+String.valueOf(year.getSelectedItemId())+"&ra="+String.valueOf(rate.getSelectedItemId())
									+"&ri="+String.valueOf(risk.getSelectedItemId()+1));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s="";
							String line="";
							matchlist=new ArrayList<String>();
							detaillist=new ArrayList<String>();
							int i,j;
							while((s=buffer.readLine())!=null)
							{
								line="";
								detaillist.add(s);
								i=s.indexOf("&");
								j=s.indexOf("&",i+1);
								line+="数额："+s.substring(i+1,j);
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
								matchlist.add(line);
							}	
							handle.sendMessage(new Message());
					}catch(Exception e){}
					}
				}.start();
			}
		});
		
		flash1=(TextView)mon2.findViewById(R.id.mon2_flash);
		flash1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run(){
					try{
							String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Borrowdeal?";
							URL url = new URL(url1+"a="+sharepre.getString("account",""));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
							String s="";
							String line="";
							matchlist1=new ArrayList<String>();
							detaillist1=new ArrayList<String>();
							int i,j,amount=0;
							while((s=buffer.readLine())!=null)
							{
								line="";
								detaillist1.add(s);
								i=s.indexOf("&");
								j=s.indexOf("&",i+1);
								line+="数额"+s.substring(i+1,j);
								amount+=Integer.parseInt(s.substring(i+1,j));
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
								matchlist1.add(line);
							}
							Message msg=new Message();
							msg.what=amount;
							handle.sendMessage(msg);
							
						}catch(Exception e){}
					}
				}.start();
			}
		});
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==-1)
					Toast.makeText(Money.this, "已申请相同贷款",Toast.LENGTH_SHORT).show();
				else if(msg.what==-2)
					Toast.makeText(Money.this, "申请成功",Toast.LENGTH_SHORT).show();
				successdeal.setAdapter(new ArrayAdapter(Money.this,R.layout.itemsytle,matchlist));
				successdeal1.setAdapter(new ArrayAdapter(Money.this,R.layout.itemsytle,matchlist1));	
				getmoney.setText("当前贷得数额:"+String.valueOf(msg.what));
			}
		};
		
		sendrequest1=(TextView)mon2.findViewById(R.id.mon2_sendrequest);
		sendrequest1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run(){
						try{
							String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Adddeal?";
							URL url = new URL(url1+"a="+sharepre.getString("account","")+"&m="+money1.getText().toString()
									+"&y="+String.valueOf(year1.getSelectedItemId())+"&r="+String.valueOf(rate1.getSelectedItemId())
									+"&k="+String.valueOf(style.getSelectedItemId()));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							InputStream in = conn.getInputStream();
							BufferedReader buffer=new BufferedReader(new InputStreamReader(in));
							String line=buffer.readLine();
							Message msg=new Message();
							if(line.charAt(0)=='2')
								msg.what=-1;
							else
								msg.what=-2;
							handle.sendMessage(msg);
						}catch(Exception e){}
					}
				}.start();
				
				lendmoney.setVisibility(View.GONE);
				success1.setVisibility(View.VISIBLE);
			}
			
		});
		
		
		showsuccess1 = (TextView) mon2.findViewById(R.id.mon2_showsuccess);
		showsuccess1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					lendmoney.setVisibility(View.GONE);
					success1.setVisibility(View.VISIBLE);
					
			}

		});

		showlendmoney = (TextView) mon2.findViewById(R.id.mon2_showlendmoney);
		showlendmoney.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lendmoney.getVisibility() == View.VISIBLE) {
					lendmoney.setVisibility(View.GONE);
					success1.setVisibility(View.VISIBLE);
				} else {
					lendmoney.setVisibility(View.VISIBLE);
					success1.setVisibility(View.GONE);
				}
			}

		});
	
		
	
		
		
		successdeal.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int i,j;
				String s=(String) detaillist.get(position);
				Builder build = new AlertDialog.Builder(Money.this);
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
				final String skind=s.substring(i+1, s.length());
				switch(s.substring(i+1, s.length()).charAt(0)){
					case '0':kin.setText("工薪贷（个人）");break;
					case '1':kin.setText("净值贷（企业）");break;
					case '2':kin.setText("网商贷（个体商户）");break;
					case '3':kin.setText("卡易贷（个人）");break;
					case '4':kin.setText("教育贷（个人）");break;
				}
				
				TextView makedeal,dismiss;
				makedeal=(TextView)detailview.findViewById(R.id.detail_makedeal);
				dismiss=(TextView)detailview.findViewById(R.id.detail_dismiss);
				
				final AlertDialog dialog = build.create();
				dialog.show();
				
				dismiss.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				makedeal.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						new Thread(){
							@Override
							public void run(){
							try{
								Time t=new Time();	
								t.setToNow();
								String time=String.valueOf(t.year)+"."+String.valueOf(t.month+1)+"."+String.valueOf(t.monthDay);
								String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Makedeal?";
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
						Toast.makeText(Money.this,"发送请求成功", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
				
			}
			
		});
		
		
		
		successdeal1.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int i,j;
				String s=(String) detaillist1.get(position);
				Builder build = new AlertDialog.Builder(Money.this);
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
				
				TextView makedeal;
				makedeal=(TextView)detailview.findViewById(R.id.detail1_ok);
				
				final AlertDialog dialog = build.create();
				dialog.show();
				
				makedeal.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});				
			}			
		});
	}

}
