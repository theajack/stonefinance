package Obj;

import org.json.JSONException;
import org.json.JSONObject;

public class IncomeType implements DBObject{
	private String incomeTypeName;
	public IncomeType(String incomeTypeName){
		this.incomeTypeName = incomeTypeName;
	}
	@Override
	public JSONObject getObjectInfo() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("incometypename", this.incomeTypeName);    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonObject;
	}
}
