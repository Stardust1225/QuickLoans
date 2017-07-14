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
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Normal extends Activity{
	
	DrawerLayout lay1;
	ImageView open;
	Button personinfo,changeinfo,history,quit,accrela;
	TextView makedeal,serviceintro,riskintro,accknow;
	Mytextview welcome;
	SharedPreferences sharepre;
	SharedPreferences.Editor editor;
	List recomlist,recomlistdetail;
	ListView list;
	Handler handle;
	
	@Override
	public void onCreate(Bundle save){
		super.onCreate(save);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.normal);
		
		sharepre = getSharedPreferences("setting", MODE_WORLD_WRITEABLE);
		editor = sharepre.edit();
		
		lay1=(DrawerLayout)findViewById(R.id.normal_layout);
		
		open=(ImageView)findViewById(R.id.nor_open);
		open.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
					lay1.openDrawer(Gravity.START);
			}
		});
	
		
		personinfo=(Button)findViewById(R.id.nor_personinfo);
		changeinfo=(Button)findViewById(R.id.nor_changeinfo);
		history=(Button)findViewById(R.id.nor_history);
		quit=(Button)findViewById(R.id.nor_quit);
		welcome=(Mytextview)findViewById(R.id.nor_welcome);
		accrela=(Button)findViewById(R.id.nor_accountrelative);
		
		makedeal=(TextView)findViewById(R.id.nor_makedeal);
		serviceintro=(TextView)findViewById(R.id.nor_serviceintro);
		accknow=(TextView)findViewById(R.id.nor_accknow);
		riskintro=(TextView)findViewById(R.id.nor_riskintro);
		
		Typeface face = Typeface.createFromAsset (getAssets() , "fonts/xingshu.ttf" );
		welcome.setTypeface (face);
		
		
		personinfo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Builder build = new AlertDialog.Builder(Normal.this);
				View view = getLayoutInflater().inflate(R.layout.personinfo, null);
				build.setView(view);
				build.setCancelable(false);
				TextView acc,name,career,edu,email,tele,ok;
				
				acc=(TextView)view.findViewById(R.id.perinfo_account);
				name=(TextView)view.findViewById(R.id.perinfo_name);
				career=(TextView)view.findViewById(R.id.perinfo_career);
				edu=(TextView)view.findViewById(R.id.perinfo_edu);
				email=(TextView)view.findViewById(R.id.perinfo_email);
				tele=(TextView)view.findViewById(R.id.perinfo_tele);
				ok=(TextView)view.findViewById(R.id.perinfo_ok);
				
				acc.setText(sharepre.getString("account",""));
				
				name.setText(sharepre.getString("name",""));
				email.setText(sharepre.getString("email",""));
				tele.setText(sharepre.getString("tele",""));
				
				switch(sharepre.getString("career","0").charAt(0)){
						case '0':career.setText("个体户");break;
						case '1':career.setText("私企");break;
						case '2':career.setText("国企");break;
						case '3':career.setText("事业单位");break;
						case '4':career.setText("政府机关");break;
				}
				
				switch(sharepre.getString("edu","0").charAt(0)){
					case '0':edu.setText("小学");break;
					case '1':edu.setText("初中");break;
					case '2':edu.setText("高中");break;
					case '3':edu.setText("专科");break;
					case '4':edu.setText("本科");break;
					case '5':edu.setText("研究生");break;
				}
				
				final Dialog dialog = build.create();
				
				ok.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		});
		
		changeinfo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Normal.this,Change.class));
			}
		});
		
		history.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Normal.this,History.class));
			}
		});
		
		quit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				editor.clear();
				editor.commit();
				startActivity(new Intent(Normal.this,First.class));
			}
		});
		
		makedeal.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Normal.this,Money.class));
			}
		});
		
		serviceintro.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Builder build = new AlertDialog.Builder(Normal.this);
				View view = getLayoutInflater().inflate(R.layout.text, null);
				build.setView(view);
				build.setCancelable(false);
				
				TextView text=(TextView)view.findViewById(R.id.text_text);
				Button ok=(Button)view.findViewById(R.id.text_ok);
				
				text.setText("   速贷平台是一个集P2P贷款、理财、教育投资为一体的互联网金融平台。"
						+"借款人向平台申请贷款后，平台基于BP神经网络模型对借款人作信用评估；"
						+ "投资者向平台表达自己的选择偏好和可承担的风险系数，平台根据协同过滤算法向投资者推荐贷款项目，实现信贷的快速匹配。\n"
						+"   速贷内附含一个新型的教育投资平台，教育类贷款的借款人可申请教育投资，"
						+ "投资者根据贷款期内对借款对象的认识了解和平台对借款对象的评估信息选取进行教育投资的对象。"
						+ "预选投资对象需参加夏令营交流，优秀营员获得教育投资的机会，投资对象与投资者协商决定在回报模式上选择标准模式还是协议模式。\n"
						+"   平台内所有环节的往来信息都将录入数据分析平台进行大数据研究，为今后的信贷流程和投资环节提供更强大的数据支撑。\n"
						+"   平台内设信息公开平台和反洗钱系统，保证借贷、投资各环节公开透明，合法合规。"
						+ "另外，平台将引入第三方征信平台帮助对借款人的信用评估，引入第三方担保机构、保险公司担保坏账风险，引入第三方商业银行托管客户资金"
						+ "，同时有第三方会计师事务所、律师事务所对平台业务进行多方面监管。");
				
				final Dialog dialog = build.create();
				
				ok.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		});
		
		riskintro.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Builder build = new AlertDialog.Builder(Normal.this);
				View view = getLayoutInflater().inflate(R.layout.text, null);
				build.setView(view);
				build.setCancelable(false);
				
				TextView text=(TextView)view.findViewById(R.id.text_text);
				Button ok=(Button)view.findViewById(R.id.text_ok);
				
				text.setText("   借款人的信用风险：若借款人未及时还款，第三方担保机构、保险公司承担60%的坏账，平台内风险准备金填补20%，剩余20%的坏账由投资者自己承担。\n"
						+ "   教育投资的投资风险：教育类投资的标准回报模式是以工作后第5——10年每年8%的税后收入作为回报,"+
						"若被投资对象出现信用违约，或被投资对象发展前途一般，年收入较低，未达到预期回报效果，投资者需自行承担投资风险。");
				
				final Dialog dialog = build.create();
				
				ok.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		});
		
		accknow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Builder build = new AlertDialog.Builder(Normal.this);
				View view = getLayoutInflater().inflate(R.layout.text, null);
				build.setView(view);
				build.setCancelable(false);
				
				TextView text=(TextView)view.findViewById(R.id.text_text);
				Button ok=(Button)view.findViewById(R.id.text_ok);
				
				text.setText("");
				
				final Dialog dialog = build.create();
				
				ok.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		});
		
		accrela.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Builder build = new AlertDialog.Builder(Normal.this);
				View view = getLayoutInflater().inflate(R.layout.accountrelative, null);
				build.setView(view);
				build.setCancelable(false);
				TextView ok=(TextView)view.findViewById(R.id.acc_ok);
				final Dialog dialog = build.create();
				
				ok.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		});
		
		TextView recommend=(TextView)findViewById(R.id.normal_recommend);
		list=(ListView)findViewById(R.id.nor_recommendlist);
		recomlist=new ArrayList<String>();
		recomlistdetail=new ArrayList<String>();
		
		recommend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run(){
					try{
							String url1 = "http://" + sharepre.getString("ip","localhost") + ":8080/Service/Recommend?";
							URL url = new URL(url1+"a="+sharepre.getString("account",""));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							InputStream in = conn.getInputStream();
							BufferedReader buffer = new BufferedReader(new InputStreamReader(in,"UTF-8"));
							String s="";
							String line="";		
							recomlist=new ArrayList<String>();
							recomlistdetail=new ArrayList<String>();
							int i,j;	
							while((s=buffer.readLine())!=null&&s.length()>3)
							{
								
									line="";
									recomlistdetail.add(s);
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
									recomlist.add(line);
									
							}
							
							handle.sendMessage(new Message());						
							}catch(Exception e){}
					}
				}.start();
			}		
		});
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				list.setAdapter(new ArrayAdapter(Normal.this,R.layout.itemsytle,recomlist));
				

			}
		};
		
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int i,j;
				String s=(String) recomlistdetail.get(position);
				Builder build = new AlertDialog.Builder(Normal.this);
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
						Toast.makeText(Normal.this,"发送请求成功", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
				
				
				dismiss.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}			
				});
			}
		});
	}
}