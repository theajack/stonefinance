package com.example.stonefinance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DB;

import service.Service;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class DataPost {
	private Handler handler = null;  
    private WebView webView = null; 
    private MainActivity activity;
    Service service;
    public DataPost(MainActivity activity) {
  //     this.webView = (WebView)activity.findViewById(R.id.webViewIndex);  
  //     this.handler = handler;  
        service=new Service();
        this.activity=activity;
    } 
  /*  @JavascriptInterface
    public void getDetail(){
        //ͨ��handler��ȷ��init������ִ�������߳���  
          handler.post(new Runnable() {
            public void run(){
               webView.loadUrl("javascript:showDetail('" + getDetailList() + "')"); 
               // javascript:�˷���������ǰ�˵ķ��� ��ͬʱ����ȥ����
            }  
        }); 
    }*/
    String [] methods=new String[]{
    		"regist",//0
    		"land",
    		"findPW",
    		"getLoginUser",
    		"getUserData",
    		"updateUserData",//5
    		"showIncomeType",
    		"showBigPayType",
    		"showPayType",
    		"showInConsume",
    		"showPayConsume",//10
    		"saveDetail",
    		"saveBudget",
    		"timeShowDetail",
    		"moneyShowDetail",
    		"selectDetail",//15
    		"searchAllDetail",
    		"searchAllBudget",
    		"addIncomeType",
    		"addBigPayType",
    		"addInConsume",//20
    		"addPayConsume",
    		"actualAllIn",
    		"actualAllPay", 
    		"budgetAllIn",
    		"budgetAllPay",//25
    		"consumeActualBudgetData",
    		"consumeActualBudgetDataIn",
    		"typeActualBudgetData",
    		"typeActualBudgetDataIn",
    		"monthActualBudgetData",//30
    		"monthActualBudgetDataIn",
    		"modifyDetail",
    		"deleteDetail",
    		"modifyBudget",
    		"deleteBudget",//35
    		"allBudgetList",
    		"nowMonthAllIn",
    		"nowMonthAllPay",
    		"playBgm",
    		"stopBgm",//40
    		"playBtnClick",
    		"dataBackUp",
    		"dataRecover",
    		"logout",
    		"getLoginState",//45
    		"saveLoginState",
    		"autoLoginOn",
    		"autoLoginOff",
    		"share"
    };
     
    private int getMethodIndex(String method){
    	for(int i=0;i<methods.length;i++){
    		if(method.equals(methods[i]))
    			return i;
    	} 
    	return -1; 
    }
    @JavascriptInterface
    public String dataPost(String method,String str){
    	String result="";  
               switch(getMethodIndex(method)){ 
               		case 0:result=service.regist(str);break;
               		case 1:result=service.land(str);break;
               		case 2:result=service.findPW(str);break;
               		case 3:result=service.getLoginUser(str);break;
               		case 4:result=service.getUserData();break;
               		case 5:service.updateUserData(str);break;
               		case 6:result=service.showIncomeType();break;
               		case 7:result=service.showBigPayType();break;
               		case 8:result=service.showPayType(str);break;
               		case 9:result=service.showInConsume();break; 
               		case 10:result=service.showPayConsume();break;
               		case 11:result=service.saveDetail(str);break;
               		case 12:result=service.saveBudget(str);break;
               		// �򵥲�ѯ  ��ҳ����ʾ��ϸ  
               		case 13:result=service.timeShowDetail();break; 
               		case 14:result=service.moneyShowDetail();break;
               		// ��ѯ�����Ϣ  չ�ֱ�ͼ
               		case 15:result=service.selectDetail(str);break;
               		// ����������ѯ 
               		case 16:result=service.searchAllDetail(str);break;
               		case 17:result=service.searchAllBudget(str);break;
               		case 18:result=service.addIncomeType(str);break; 
               		case 19:result=service.addBigPayType(str);break;
               		case 20:result=service.addInConsume(str);break;
               		case 21:result=service.addPayConsume(str);break;
               		// ����  
               		// ʵ��������      
               		case 22:result=service.actualAllIn(str);break;
               		// ʵ����֧�� 
               		case 23:result=service.actualAllPay(str);break;
               		// Ԥ�������� 
               		case 24:result=service.budgetAllIn(str);break;
               		// Ԥ����֧�� 
               		case 25:result=service.budgetAllPay(str);break;
               		// ����Ա����ʵ��-Ԥ����֧��
               		case 26:result = service.consumeActualBudgetData(str);break;
               		// ����Ա����ʵ��-Ԥ�������� 
               		case 27:result = service.consumeActualBudgetDataIn(str);break;
               		// ��������ʵ��-Ԥ����֧��  
               		case 28:result = service.typeActualBudgetData(str);break;
               		// ��������ʵ��-Ԥ�������� 
               		case 29:result = service.typeActualBudgetDataIn(str);break;
               		// ���·�ʵ��-Ԥ����֧�� 
               		case 30:result = service.monthActualBudgetData(str);break;
               		// ���·�ʵ��-Ԥ�������� 
               		case 31:result = service.monthActualBudgetDataIn(str);break;
               		case 32:result = service.modifyDetail(str);break; 
               		case 33:result = service.deleteDetail(str);break;
               		case 34:result = service.modifyBudget(str);break;
               		case 35:result = service.deleteBudget(str);break; 
               		case 36:result = service.allBudgetList(str);break;
               		case 37:result = service.nowMonthAllIn(str);break;
               		case 38:result = service.nowMonthAllPay(str);break;
               		case 39:MP.playBgm();break;
               		case 40:MP.stopBgm();break;  
               		case 41:MP.playBtnClick();break;
               		case 42:result = service.dataBackUp();break;
               		case 43:result = service.dataRecover();break;
               		case 44:service.logout();break;
               		case 45:result = service.getLoginState();break;
               		case 46:service.saveLoginState(str);break;
               		case 47:DB.saveSingle(DB.isAutoLogin, "true");break;
               		case 48:DB.saveSingle(DB.isAutoLogin, "false");break;
               		case 49:this.activity.shareToFriendCircle();break;
               		
        };
        return result; 
    }  
}