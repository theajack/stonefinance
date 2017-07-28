package Obj;

import org.json.JSONException;
import org.json.JSONObject;

public class PayType implements DBObject{
	private String bigPayTypeId;
	private String payTypeName;
	public PayType(String bigPayTypeId,String payTypeName){
		this.bigPayTypeId = bigPayTypeId;
		this.payTypeName = payTypeName;
	}
	@Override
	public JSONObject getObjectInfo() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("bigpaytypeid", this.bigPayTypeId);
	        jsonObject.put("paytypename", this.payTypeName);     
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonObject;
	}
}
