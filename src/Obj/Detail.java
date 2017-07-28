package Obj;

import org.json.JSONException;
import org.json.JSONObject;

public class Detail implements DBObject{
	private String memberAccount;
	private int payTypeId;
	private int incomeTypeId;
	private String detailName;
	private String timePoint;
	private String adress;
	private boolean detailSZClassify;
	private float detailMoney;
	private String detailMark;
	private String consumeName;
	public Detail(String memberAccount,int payTypeId,int incomeTypeId,String detailName,String timePoint,String adress,
			boolean detailSZClassify,float detailMoney,String detailMark,String consumeName){
		this.memberAccount = memberAccount;
		this.payTypeId = payTypeId;
		this.incomeTypeId = incomeTypeId;
		this.detailName = detailName;
		this.timePoint = timePoint;
		this.adress = adress;
		this.detailSZClassify = detailSZClassify;
		this.detailMoney = detailMoney;
		this.detailMark = detailMark;
		this.consumeName = consumeName;
	}
	@Override
	public JSONObject getObjectInfo() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("memberAccount", this.memberAccount);
	        jsonObject.put("paytypeid", this.payTypeId);
	        jsonObject.put("incometypeid", this.incomeTypeId);
	        jsonObject.put("detailname", this.detailName);
			jsonObject.put("timepoint", this.timePoint);
	        jsonObject.put("adress", this.adress);
	        jsonObject.put("detailszclassify", this.detailSZClassify);
	        jsonObject.put("detailmoney", this.detailMoney);
	        jsonObject.put("detailmark", this.detailMark);
			jsonObject.put("consumename",consumeName );
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonObject;
	}
	
	
}
