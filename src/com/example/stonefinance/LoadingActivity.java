package com.example.stonefinance;

import java.io.File;
import java.io.FileOutputStream;



import database.DB;
import database.DataBaseOperate;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);		
		//�߳�Runnable
		MP.initMusicPlayer(this);
		Runnable runnable = new Runnable(){ 

			@Override
			public void run() {  
				// TODO Auto-generated method stub
				//// java�ļ��У�activity����ת
				Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}	
		};
		if(!DB.checkSDCard()){
			Toast.makeText(this, "�ܱ�Ǹ����⵽����ֻ�û��SD��!", Toast.LENGTH_LONG).show();
		}else{
			if(DB.checkData()){
			}
		}

		DB.shareImgPath=new String[2];
		DB.shareImgPath[0]=this.saveBitMapToFile(this, "share.png", BitmapFactory.decodeResource(this.getResources(), R.drawable.landhead), false);
		DB.shareImgPath[1]=this.saveBitMapToFile(this, "share1.png", BitmapFactory.decodeResource(this.getResources(), R.drawable.background), false);
		DataBaseOperate dbo=new DataBaseOperate();
		////Handler�����߳�
		Handler handler = new Handler();
		handler.postDelayed(runnable, 1500);
	}
	public String saveBitMapToFile(Context context, String fileName, Bitmap bitmap, boolean isCover) {
	    if(null == context || null == bitmap) {
	        return null;
	    }
	    if(TextUtils.isEmpty(fileName)) {
	        return null;
	    }
	    FileOutputStream fOut = null;
	    try {
	        File file = null;
	        String fileDstPath = "";
	        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
	            // ���浽sd��
	            fileDstPath = Environment.getExternalStorageDirectory().getAbsolutePath()
	                    + DB.path + fileName;

	            File homeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
	                    + DB.path);
	            if (!homeDir.exists()) {
	                homeDir.mkdirs();
	            }
	        } else {
	            // ���浽fileĿ¼
	            fileDstPath = context.getFilesDir().getAbsolutePath()
	                    + DB.path + fileName;

	            File homeDir = new File(context.getFilesDir().getAbsolutePath()
	                    + DB.path);
	            if (!homeDir.exists()) {
	                homeDir.mkdir();
	            }
	        }

	        file = new File(fileDstPath);

	        if (!file.exists() || isCover) {
	            // ���������ɾ�����ļ����������Ƿ���ڡ�
	            file.delete();

	            fOut = new FileOutputStream(file);
	            if (fileName.endsWith(".jpg")) {
	                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
	            } else {
	                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
	            }
	            fOut.flush();
	            //bitmap.recycle();
	        }
	        //Toast.makeText(this, "saveDrawableToFile " + fileName+ " success, save path is " + fileDstPath, Toast.LENGTH_LONG).show();
	        return fileDstPath;
	    } catch (Exception e) {
	        //Toast.makeText(this, "saveDrawableToFile: " + fileName + " , error", Toast.LENGTH_LONG).show();
	        return null;
	    } finally {
	        if(null != fOut) {
	            try {
	                fOut.close();
	            } catch (Exception e) {
	    	        //Toast.makeText(this, "saveDrawableToFile, close error", Toast.LENGTH_LONG).show();
	            }
	        }
	    }

		}
	}
