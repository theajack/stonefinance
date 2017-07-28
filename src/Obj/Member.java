package Obj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Member implements DBObject{
	private String memberAccount;
	private String passWord;
	private String question;
	private String answer;
	private String memberName;
	private int memberAge;
	private String memberBir;
	private String memberSex;
	private String memberTele;
	private String memberAddress;
	public Member(String memberAccount,String passWord,String question,String answer,
			String memberName,int memberAge,String memberBir,String memberSex,
			String memberTele,String memberAddress){
		this.memberAccount=memberAccount;
		this.passWord=passWord;
		this.question = question;
		this.answer = answer;
		this.memberName=memberName;
		this.memberAge=memberAge;
		this.memberBir=memberBir;
		this.memberSex=memberSex;
		this.memberTele=memberTele;
		this.memberAddress=memberAddress;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	@Override
	public JSONObject getObjectInfo() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("memberaccount", this.memberAccount);
	        jsonObject.put("password", this.passWord);
			jsonObject.put("question", this.question);
	        jsonObject.put("answer", this.answer);
	        jsonObject.put("membername", this.memberName);
	        jsonObject.put("memberage", this.memberAge);
	        jsonObject.put("memberbir", this.memberBir);
	        jsonObject.put("membersex", this.memberSex);
	        jsonObject.put("membertele", this.memberTele);
	        jsonObject.put("memberaddress", this.memberAddress);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonObject;
	}
	
}
