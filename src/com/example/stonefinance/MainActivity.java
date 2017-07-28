package com.example.stonefinance;
import java.io.File;
import java.util.ArrayList;


import database.DB;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
public class MainActivity extends Activity {
	
	private WebView webViewIndex;
	//private ContactService contactService;
	public Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);     
		webViewIndex = (WebView) findViewById(R.id.webViewIndex);
		/**    
        * ���ط���assetsĿ¼�µ�index.html�ļ� ע��url�ĵ�ַǰ׺Ϊ�� file:///android_asset/
        *
        * ��ʵ���԰����html�����ļ����ڹ����У�����������ʱ����ά�� ����
        * webview.loadUrl("bickshare.imwork.net");
        */

		if(DB.getData(DB.isPlayBgm).equals("true")){
			MP.playBgm();
		}
		webViewIndex.loadUrl("file:///android_asset/land.html");
		/** ����javascript������ */
		webViewIndex.getSettings().setJavaScriptEnabled(true);
		webViewIndex.setWebViewClient(new WebViewClient(){ 
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                       view.loadUrl(url);//��������ӵ�ʱ��������ԭ�������ϼ���URL
	                       return true;
	           }
	        });  
		
		/** 
         * ���һ��js�����ӿڣ�����html�����ļ��е�javascript���������̨java����ֱ�ӽ�������
         * "contact"Ϊ���ö���ȡ�ñ��� ��Ӧandroid.html�е�contact
         */
       	 webViewIndex.addJavascriptInterface(new DataPost(this), "dataPost");
	}
	
	public void shareToFriendCircle(){
		if(testNet()){

			Toast.makeText(this, "���ڼ���...", Toast.LENGTH_LONG).show();
			ArrayList<Uri> uris=new ArrayList<Uri>();
			for(int i=0;i<DB.shareImgPath.length;i++){
				uris.add(Uri.fromFile(new File(DB.shareImgPath[i])));
			}
			shareToTimeLine("������ʹ��ʯͷ��ơ����ص�ַ<http://a.app.qq.com/o/simple.jsp?pkgname=com.example.stonefinance>(����δ��װӦ�ñ�����ѡ����ͨ����)��������ѣ��޹�棬���ڹ���һ��רҵ��������֣�������õĴ����Լ���С��⡣��֧Ԥ�㣬�������������ݻָ���������ȫ�����๦�ܵ��������֣��Ͻ�Ҳ�����԰ɣ�      <����Сʯͷ--ʯͷ���>"
						,uris);
		}else{
			Toast.makeText(this, "�������ӳ�������!", Toast.LENGTH_LONG).show();
		}
	}
	private void shareToTimeLine(String title, ArrayList<Uri> uris) {
	    Intent intent = new Intent();
	    ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
	    intent.setComponent(comp);
	    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
	    intent.setType("image/*");
 
	    intent.putExtra("Kdescription", title);

	    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
	    startActivity(intent);
	}
	public boolean testNet() { 
		//String s=this.gainSkillManager.getNetTime().toString();
		//String s1=new Date().toString();
		//this.gainSkillManager.getNetTime();
		if (this != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null) { 
				return mNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	} 
	  
    @Override  
    protected void onResume() {
    	if(DB.getData(DB.isPlayBgm).equals("true")){
    		MP.playBgmTemp();
    	}
    	//this.time.reStart();
        super.onResume();  
    }  
    
    @Override  
    protected void onPause() {  
    	MP.stopBgmTemp();
    	//this.time.stop();
        super.onPause();  
    }


}
