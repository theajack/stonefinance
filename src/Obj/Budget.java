package Obj;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Budget implements DBObject{
	private String memberAccount;
	private float  budgetMoney;
	private String budgetTimePart;
	private boolean budgetSZClassify;
	private String consumeName;
	public Budget(String memberAccount,float  budgetMoney,String budgetTimePart,
			boolean budgetSZClassify,String consumeName){
		this.memberAccount =  memberAccount;
		this.budgetMoney = budgetMoney;
		this.budgetTimePart = budgetTimePart;
		this.budgetSZClassify = budgetSZClassify;
		this.consumeName = consumeName;
	}
	@Override
	public JSONObject getObjectInfo() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("memberaccount",memberAccount );
			jsonObject.put("budgetmoney",budgetMoney );
			jsonObject.put("budgettimepart",budgetTimePart );
			jsonObject.put("budgetszclassify",budgetSZClassify );
			jsonObject.put("consumename",consumeName );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	
	

}
