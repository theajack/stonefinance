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
        * 加载放在assets目录下的index.html文件 注意url的地址前缀为： file:///android_asset/
        *
        * 其实可以把这个html布局文件放在公网中，这样方便随时更新维护 例如
        * webview.loadUrl("bickshare.imwork.net");
        */

		if(DB.getData(DB.isPlayBgm).equals("true")){
			MP.playBgm();
		}
		webViewIndex.loadUrl("file:///android_asset/land.html");
		/** 允许javascript的运行 */
		webViewIndex.getSettings().setJavaScriptEnabled(true);
		webViewIndex.setWebViewClient(new WebViewClient(){ 
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                       view.loadUrl(url);//点击超链接的时候重新在原来进程上加载URL
	                       return true;
	           }
	        });  
		
		/** 
         * 添加一个js交互接口，方便html布局文件中的javascript代码能与后台java代码直接交互访问
         * "contact"为给该对象取得别名 对应android.html中的contact
         */
       	 webViewIndex.addJavascriptInterface(new DataPost(this), "dataPost");
	}
	
	public void shareToFriendCircle(){
		if(testNet()){

			Toast.makeText(this, "正在加载...", Toast.LENGTH_LONG).show();
			ArrayList<Uri> uris=new ArrayList<Uri>();
			for(int i=0;i<DB.shareImgPath.length;i++){
				uris.add(Uri.fromFile(new File(DB.shareImgPath[i])));
			}
			shareToTimeLine("我正在使用石头理财。下载地址<http://a.app.qq.com/o/simple.jsp?pkgname=com.example.stonefinance>(若您未安装应用宝，请选择普通下载)，永久免费，无广告，无内购。一款专业的理财助手，助你更好的打理自己的小金库。收支预算，搜索分析，备份恢复，样样俱全，更多功能等你来发现，赶紧也来试试吧！      <来自小石头--石头理财>"
						,uris);
		}else{
			Toast.makeText(this, "网络连接出现问题!", Toast.LENGTH_LONG).show();
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
