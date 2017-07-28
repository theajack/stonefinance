package Obj;

import org.json.JSONException;
import org.json.JSONObject;

public class Consume implements DBObject{
	private String consumeName;
	private String consumeSZClassify;
	public Consume(String consumeName,String consumeSZClassify){
		this.consumeName = consumeName;
		this.consumeSZClassify = consumeSZClassify;
	}
	@Override
	public JSONObject getObjectInfo() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("consumename", consumeName);
			jsonObject.put("consumeszclassify", consumeSZClassify);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}
