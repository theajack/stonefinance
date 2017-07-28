package Obj;

import org.json.JSONException;
import org.json.JSONObject;

public class BigPayType implements DBObject{
	private String bigPayTypeName;
	public BigPayType(String bigPayTypeName){
		this.bigPayTypeName = bigPayTypeName;
	}
	@Override
	public JSONObject getObjectInfo() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
	        jsonObject.put("bigpaytypename", this.bigPayTypeName);     
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonObject;
	}
}
