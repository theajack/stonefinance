package database;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
public class DB {//文件数据保存与读取
	public static int isRememberAccout=0;
	public static int isAutoLogin=1;
	public static int isPlayBgm=2;
	public static int account=3;
	public static int password=4;
	public static boolean hasSdCard;
	public static String shareImgPath[];
	public static boolean isFirstLoad=false;
	private static String wPath;
	private static char bsplit='-';//数据分隔符
	private static char split='_';//数据分隔符
	public static String path="/data/sfData/";
	public static String filePath;
	public static boolean checkSDCard(){ 
		String sdpath="";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			sdpath = Environment.getExternalStorageDirectory().getAbsolutePath(); 
			hasSdCard=true;
		}
		else{
			hasSdCard=false; 
		}
		filePath=sdpath+path;
		wPath=filePath+"loginData";
		return hasSdCard; 
	}	
	public static void resetAllData(){
		resetData();
		isFirstLoad=true;
	}	
	
	private static void resetData() {
		String[] bs=new String[]{
				"false",
				"false",
				"false",
				" ",
				" "
		};
		
		saveData(bs);
	}
	public static boolean checkData(){
		boolean isLack=false;

		File f=new File(wPath);
		if(!f.exists()){
			isLack=true;
		}
		//isLack=true;
		//if(!file[0].exists()){
		if(isLack){
			f.getParentFile().delete();
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				resetAllData();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}else
			return false;
	}
	
	
	public static int getSingle(int index){
		String s=getData(index);
		return Integer.parseInt(s);
	}
	public static void saveSingle(int index,int data){
		saveSingle(index,""+data);
	}
	public static void saveSingle(int index,String data){
		String []s=getAllData();
		s[index]=data; 
		saveData(s);
	}
	
	public static BufferedReader getBr(String fileName){
		File file=new File(fileName);
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			return br;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String[] getAllData(){
		BufferedReader br = getBr(wPath);
		ArrayList<String> list = new ArrayList<String>(); 
		String s;
		int n=0;
		try {
			while((s=br.readLine()) !=null){
				list.add(s);
				n++;
			}
			br.close();
			String[] str=new String [n];
			for(int i=0;i<n;i++){
				str[i]=list.get(i);
			}
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getData(int index){
		BufferedReader br = getBr(wPath);
		int i=0;
		try {
			while(i!=index){
				br.readLine();
				i++;
			}
			String data=br.readLine();
			br.close();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void saveData(String[] dataStr){
		FileWriter fw;
		try {
			fw = new FileWriter(wPath,false);
		    for(int i=0;i<dataStr.length;i++){
		    	if(i>0){
		    		fw.write("\r\n");
				}
		    	fw.write(dataStr[i]);
		    }
		    fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getLoginState() {
		String[] s=getAllData();
		JSONObject obj = new JSONObject();
		try {
			obj.put("isRememberMe", s[0]);
			obj.put("isAutoLogin", s[1]);
			obj.put("isPlayBgm", s[2]);
			obj.put("account", s[3]);
			obj.put("password", s[4]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
	public static void saveBgmState(boolean b) {
		String s="false";
		if(b){
			s="true";
		}
		DB.saveSingle(isPlayBgm, s);
	}

}