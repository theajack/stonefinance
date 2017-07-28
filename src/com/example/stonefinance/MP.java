package com.example.stonefinance;

import database.DB;
import android.app.Activity;
import android.media.MediaPlayer;

public class MP {
	static MediaPlayer bgm;
	static MediaPlayer btnm;
	static Activity activity;
	public static void initMusicPlayer(Activity activity) {
		MP.activity=activity; 
		bgm = creatMusic(activity, R.raw.bgm);
		bgm.setLooping(true);
		//bgm.pause();
		btnm = creatMusic(activity, R.raw.click);
	} 
	private static MediaPlayer creatMusic(Activity acticity,int id){
		return MediaPlayer.create(acticity,id );
	}
	public static void playBtnClick(){
		if(btnm.isPlaying()){
			btnm.stop();
			btnm = creatMusic(activity, R.raw.click);
		}
		btnm.start();
	}
	public static void playBgm(){ 
		bgm.start();
		DB.saveBgmState(true);
	}
	public static void stopBgm(){
		if(bgm.isPlaying()){
			bgm.pause();
		}
		DB.saveBgmState(false);
	}
	public static void playBgmTemp(){ 
		bgm.start();
	}
	public static void stopBgmTemp(){
		if(bgm.isPlaying()){
			bgm.pause();
		}
	}
}
