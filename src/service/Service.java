package service;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Obj.Budget;
import database.DB;
import database.DataBaseOperate;

public class Service {
	private final String resultTrue="true";
	private final String resultFalse="false";
	private String user="";
	private DataBaseOperate dbo=new DataBaseOperate();
	public String account;
	
	Budget[] budget = new Budget[]{
			new Budget("ʯ��",5800,"2016.1-2016.2",true,""),
			new Budget("ʯ��",800,"2016.1-2016.2",false,""),
			new Budget("ʯ��",80,"2016.1-2016.2",true,""),
			new Budget("ʯ��",60,"2016.1-2016.2",false,""),
			new Budget("ʯ��",6000,"2016.1-2016.2",true,""),
			new Budget("ʯ��",600,"2016.1-2016.2",true,""),
			new Budget("ƿ��",3600,"2016.1-2016.2",true,""),
			new Budget("ƿ��",360,"2016.1-2016.2",false,""),
			new Budget("����",2000,"2016.1-2016.2",true,""),
			new Budget("����",330,"2016.1-2016.2",false,""),
			new Budget("ʯ��",5800,"2016.2-2016.3",true,""),
			new Budget("ʯ��",800,"2016.2-2016.3",false,""),
			new Budget("ʯ��",80,"2016.2-2016.3",true,""),
			new Budget("ʯ��",60,"2016.2-2016.3",false,""),
			new Budget("ʯ��",6000,"2016.2-2016.3",true,""),
			new Budget("ʯ��",600,"2016.2-2016.3",true,""),
			new Budget("ƿ��",3600,"2016.2-2016.3",true,""),
			new Budget("ƿ��",360,"2016.2-2016.3",false,""),
			new Budget("����",2000,"2016.2-2016.3",true,""),
			new Budget("����",330,"2016.2-2016.3",false,"")
	};
	
//////////////////////////		 ����
	
	public String regist(String str) {
		try {
			JSONObject obj = new JSONObject(str);
			int num = dbo.getMaNum(obj);
			if(num==0){
				dbo.regist(obj);
				return resultTrue;
			}else{
				return resultFalse;
			}
		} catch (JSONException e) {
			return resultFalse;
		}
	}
	public String land(String str){ 
		try {
			JSONObject json = new JSONObject(str);
			String memberaccount = json.getString("memberaccount");
			account = memberaccount;
			String password = json.getString("password");
			JSONObject jsonObject = dbo.selectMember(memberaccount);
			if(jsonObject==null){
				return resultFalse;
			}else{
				if(password.equals(jsonObject.getString("password"))){
					this.user = memberaccount;
					return resultTrue;
				}else{
					return resultFalse;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultFalse;
		}
	}
	public String findPW(String str) {
		try {
			JSONObject json = new JSONObject(str);
			String memberaccount = json.getString("memberaccount");
			JSONObject obj = dbo.selectMember(memberaccount);
			if(obj!=null){
				String s = obj.toString();
				s=s+"";
				return s;
			}else{
				return resultFalse;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	public String getLoginUser(String str) {
		return user;
	}
	/* �������ĵ��޸� */
	// ��ȡ��ǰ�û�����
	public String getUserData(){
		return dbo.selectMember(account).toString();
	}
	// �����û���Ϣ
	public void updateUserData(String str){
		try {
			JSONObject obj = new JSONObject(str);
			dbo.updateMember(account,obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 
	 
// 	��ѯincometype���е�����
	public String showIncomeType(){
		return dbo.selectIncomeType();
	} 
	public String showBigPayType(){
		return dbo.selectBigPayType();
	}
	public String showPayType(String str){
		return dbo.selectPayType(str);
	}
	public String showInConsume(){
		return dbo.selectInConsume();
	}
 	public String showPayConsume(){
		return dbo.selectPayConsume();
	}
 	public String saveDetail(String str){
 		JSONObject obj = null;
		try {
			obj = new JSONObject(str);
			this.addAccount(obj,false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
 		dbo.insertDetail(obj);
 		return resultTrue;
 	}
 	public String saveBudget(String str){
 		JSONObject obj = null;
 		try {
			obj = new JSONObject(str);
			this.addAccount(obj,false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 		dbo.insertBudget(obj);
 		return resultTrue;	
 	}
 	private void addAccount(JSONObject obj){
 		addAccount(obj,true);
 	}
 	private void addAccount(JSONObject obj,boolean isNeedTable){
		try {
			if(isNeedTable)
				obj.put("member.memberaccount", this.account);
			else
				obj.put("memberaccount", this.account);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
 	// �򵥲�ѯ  ��ҳ����ʾ��ϸ
 	public String timeShowDetail(){
 		return dbo.timeShowDetail(account);
 	}
 	public String moneyShowDetail(){
 		return dbo.moneyShowDetail(account);
 	}
 	// ��ͼ����Ϣչʾ
 	public String selectDetail(String str){
 		JSONObject obj = null;
 		try {
			obj= new  JSONObject(str);
			this.addAccount(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return dbo.selectDetail(obj); 
 	}
 	public String addIPCommonType(String str,String method,String key){
 		try {
 			JSONArray objectArray = new JSONArray(method);
 			JSONObject obj = new JSONObject(str);
 			String incomePayInfo = obj.getString(key);
 			for(int i=0;i<objectArray.length();i++){
 	 			String incomePayType = objectArray.getJSONObject(i).getString(key);
 				if(incomePayInfo.equals(incomePayType)){
 					return resultFalse;
 				}
 			}
 			if(key.equals("incometypename")){
 	 	 		dbo.insertIncomeType(obj);
 			}
 			if(key.equals("bigpaytypename")){
 				dbo.insertBigPayType(obj);
 			}
 			if(key.equals("consumename")){
 				dbo.insertConsume(obj);
 			}
 			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 		return resultTrue;
 	}
 	public String addIncomeType(String str){
 		return addIPCommonType(str,dbo.selectIncomeType(),"incometypename");
 	}
 	public String addBigPayType(String str){
 		return addIPCommonType(str,dbo.selectBigPayType(),"bigpaytypename");
 	}
 	public String addInConsume(String str){
 		return addIPCommonType(str,dbo.selectInConsume(),"consumename");
 	}
 	public String addPayConsume(String str){
 		return addIPCommonType(str,dbo.selectPayConsume(),"consumename");
 	}
 	public void saveAccount(String str){
 		account = str;
 	}


	public String searchAllDetail(String str) {
		try { 
			JSONObject obj = new JSONObject(str);
			String startTime=obj.getString("starttime");
			String endTime=obj.getString("endtime");
			String searchType=obj.getString("searchtype");
			String consumeType=obj.getString("consumetype");
			String type = obj.getString("type");
			String consumename = "";
			if(consumeType.equals("ȫ����Ա")){
				int szType;
				if(searchType.equals("��Ա����")){
					szType=1;
				}else{
					szType=0;
				}
				if(type.equals("list")){
					return dbo.getAllDetailList(startTime,endTime,szType,account);
				}
				// ȫ����Աpie��column��ѯ
				if(type.equals("pie")||type.equals("column")){
					return dbo.getAllDetailPC(startTime,endTime,szType,account);
				}  
			}else{
				int szType;
				consumename = consumeType;
				if(searchType.equals("��������")){
					szType = 1;
				}else{
					szType = 0;
				}
				if(type.equals("list")){
					return dbo.getPersonalDetailList(startTime,endTime,szType,consumename,account);
				}
				if(type.equals("pie")||type.equals("column")){
					return dbo.getPersonalDetailPC(startTime,endTime,szType,consumename,account);
				}	
			}
			return "";
		} catch (JSONException e) {
			return "";
		}
	}
	
	public String searchAllBudget(String str) {
		try { 
			JSONObject obj = new JSONObject(str);
			String startTime=obj.getString("starttime");
			String endTime=obj.getString("endtime");
			String searchType=obj.getString("searchtype");
			String consumeType=obj.getString("consumetype"); // ����Ա��ȫ����Ա��ȫ������
			String type = obj.getString("type");             // list��pie��column
			String consumename = "";
			int szType;
			if(searchType.equals("��Ա����")||searchType.equals("��������")||searchType.equals("��������")){
				szType=1;
			}else{
				szType=0;
			}
			if(consumeType.equals("ȫ����Ա")){
				if(type.equals("list")){
					return dbo.getAllBudgetList(startTime,endTime,szType,account);
				}
				// ȫ����Աpie��column��ѯ
				if(type.equals("pie")||type.equals("column")){
					return dbo.getAllBudgetPC(startTime,endTime,szType,account);
				}
			}else{
				if(consumeType.equals("ȫ������")){
					if(type.equals("list")){
						return dbo.getAllBudgetList(startTime,endTime,szType,account);
					}
					if(type.equals("pie")||type.equals("column")){
						return dbo.getAllTypeBudget(startTime,endTime,szType,account);
					}
				}else{
					consumename = consumeType;
					if(type.equals("list")){
						return dbo.getPersonalBudgetList(startTime,endTime,szType,consumename,account);
					}
					if(type.equals("pie")||type.equals("column")){
						return dbo.getPersonalBudgetPC(startTime,endTime,szType,consumename,account);
					}
				}
			}
			return "";
		} catch (JSONException e) {
			return "";
		}
	}
	
	// ����
	// ��ȡʱ�䴦��
 	public String getDatePart(String str){
 		JSONObject obj = null;
		String startTime="" ;
		String endTime="" ;
		try {
			obj= new JSONObject(str);
			startTime = obj.getString("starttime")+"-01";
			endTime = obj.getString("endtime")+"-01";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startTime+endTime;
 	}
 	// ʵ��������
 	public String actualAllIn(String str){
 		String Date = getDatePart(str);
 		return dbo.actualAllIn(Date.substring(0, 10),Date.substring(10,20),account);
 	}
 	// ʵ����֧��
 	public String actualAllPay(String str){
 		String Date = getDatePart(str);
 		return dbo.actualAllPay(Date.substring(0, 10),Date.substring(10,20),account);
 	}
 	// Ԥ��������
 	public String budgetAllIn(String str){
 		String Date = getDatePart(str);
 		return dbo.budgetAllIn(Date.substring(0, 10),Date.substring(10,20),account);
 	} 	
 	// Ԥ����֧��
 	public String budgetAllPay(String str){
 		String Date = getDatePart(str);
 		return dbo.budgetAllPay(Date.substring(0, 10),Date.substring(10,20),account);
 	}
 	// ����Ա����ʵ��-Ԥ����֧��
 	public String consumeActualBudgetData(String str){
 		String Date = getDatePart(str);
 		return dbo.consumeActualBudgetData(Date.substring(0, 10),Date.substring(10,20),account);
 	}
 	
 	// ����Ա����ʵ��-Ԥ��������
 	public String consumeActualBudgetDataIn(String str){
 		String Date = getDatePart(str);
 		return dbo.consumeActualBudgetDataIn(Date.substring(0, 10),Date.substring(10,20),account);
 	}
 	// ��������ʵ��-Ԥ����֧��
 	public String typeActualBudgetData(String str){
 		String Date = getDatePart(str);
 		return dbo.typeActualBudgetData(Date.substring(0, 10),Date.substring(10,20),account);
 	}
 	
 	// ��������ʵ��-Ԥ��������
 	public String typeActualBudgetDataIn(String str){
 		String Date = getDatePart(str);
 		return dbo.typeActualBudgetDataIn(Date.substring(0, 10),Date.substring(10,20),account);
 	}
    // ���·�ʵ��-Ԥ����֧��
	public String monthActualBudgetData(String str){
 		String Date = getDatePart(str);
 		return dbo.monthActualBudgetData(Date.substring(0, 10),Date.substring(10,20),account);
 	}
    // ���·�ʵ��-Ԥ��������
	public String monthActualBudgetDataIn(String str){
 		String Date = getDatePart(str);
 		return dbo.monthActualBudgetDataIn(Date.substring(0, 10),Date.substring(10,20),account);
 	}
	public String modifyDetail(String str) {
		try {
			JSONObject obj= new JSONObject(str);
			String detailId=obj.getString("detailid");
			obj.remove("detailid");
			return dbo.modifyDetail(obj,account,detailId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		}
	}
	public String deleteDetail(String str) {
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			String detailId=obj.getString("detailid");
			return dbo.deleteDetailById(detailId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String modifyBudget(String str) {
		try {
			JSONObject obj= new JSONObject(str);
			String budgetid=obj.getString("budgetid");
			String time=obj.getString("budgettimepart")+"-01";
			obj.put("budgettimepart", time);
			obj.remove("detailid");
			return dbo.modifyBudget(obj,account,budgetid);
		} catch (JSONException e) {
			e.printStackTrace();
			return null; 
		}
	}
	public String deleteBudget(String str) {
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			String budgetid=obj.getString("budgetid");
			return dbo.deleteBudgetById(budgetid);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	} 
	public String allBudgetList(String str) {
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			String startTime = obj.getString("starttime")+"-01";
			String endTime = obj.getString("endtime")+"-01";
			return dbo.allBudgetList(startTime,endTime,account);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String nowMonthAllIn(String str) {
		return dbo.nowMonthAllIn(str,account);
	}
	public String nowMonthAllPay(String str) {
		return dbo.nowMonthAllPay(str,account);
	}
	public String dataBackUp() {
		try{
			this.dbo.dataBackUp();
			return "true";
		}catch (Exception e){
			return "false";
		}
	}
	public String dataRecover() {
		try{
			this.dbo.dataRecover();
			return "true";
		}catch (Exception e){
			return "false";
		}
	}
	public void logout(){
		this.account="";
		this.user="";
	}
	public String getLoginState(){
		return DB.getLoginState();
	}
	public void saveRemember(String str) {
		
		
	}
	public void saveAuto(String str) {
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			String isAutoLogin=obj.getString("isAutoLogin");
			DB.saveSingle(DB.isAutoLogin, isAutoLogin);
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void saveLoginState(String str) {
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			DB.saveData(new String[]{
				obj.getString("isRememberMe"),
				obj.getString("isAutoLogin"),
				obj.getString("isPlayBgm"),
				obj.getString("account"),
				obj.getString("password")
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
