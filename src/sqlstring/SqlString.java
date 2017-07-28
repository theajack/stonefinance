package sqlstring;

import java.util.Date;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

 
public class SqlString {
	///////////////////////  以下
	public String updateCommon(String table,String account,JSONObject obj){
	//	Object {memberaccount: "萍"memberbir: "1993"membername: "萍"membersex: "男"membertele: "18818227695" password: "1"question: "我是谁 "
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(table);
		sb.append(" set ");
		Iterator iterator = obj.keys();
		while(iterator.hasNext()){
			String key =(String)iterator.next();
			sb.append(key);
			sb.append(" = '");
			try {
				String value = obj.getString(key);
				sb.append(value);
				sb.append("' ");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(iterator.hasNext()){
				sb.append(" , ");
			}
		}
		sb.append(" where memberaccount = '");
		sb.append(account);
		sb.append("'");
		return sb.toString();
	}
	public String modifyDetail(String account,String detailId,JSONObject obj){
		String str= updateCommon("detail",account,obj);
		return str+" and detailid='"+ detailId +"'";
	}
	public String getSelect(String table){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(table);
		return sb.toString();
	}
	////////////////////////////////////
	public String getSelect(String account,String table){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(table);
		sb.append(",member where member.memberaccount = '");
		sb.append(account);
		sb.append("' and member.memberaccount = ");
		sb.append(table);
		sb.append(".memberaccount");
		return sb.toString();
	}
	public String getSelect(String table,String[] strs){
		StringBuilder sql=new StringBuilder();
		sql.append("select ");
		for(int i=0;i<strs.length;i++){
			if(i>0){
				sql.append(",");
			}
			sql.append(strs[i]);
		}  
		sql.append(" from ");
		sql.append(table);
		return sql.toString();
	}
	public String getSelect(String table,JSONObject obj,String str){
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(str);
		sql.append(" from "); 
		sql.append(table);
		sql.append(",member where ");
	//	sql.append(account);
		sql.append("member.memberaccount = ");
		sql.append(table);
		sql.append(".memberaccount and ");
		Iterator iterator = obj.keys();
		String key;
		while(iterator.hasNext()){
			key = (String) iterator.next();
			sql.append(key);
			sql.append(" = '");
			try {
				sql.append(obj.getString(key));
				sql.append("'");
				if(iterator.hasNext()){
					sql.append(" and ");
				}else{
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sql.toString();
	}
	public String getSelect(String table,String key,String value){
		StringBuilder sb=new StringBuilder();
		sb.append(getSelect(table));
		sb.append(" where ");
		sb.append(key);
		sb.append("='");
		sb.append(value);
		sb.append("'");
		return sb.toString();
	}
	public String getSelect(String table,String[] strs,String[] key,String[] value){
		StringBuilder sb=new StringBuilder();
		sb.append(getSelect(table,strs));
		sb.append(" where ");
		int keyLength = key.length;
		for(int i=0;i<keyLength;i++){
			sb.append(key[i]);
			sb.append("='");
			sb.append(value[i]);
			sb.append("'");
			if(i!=0){
				sb.append("and");
			}
		}
		return sb.toString();
	}

	/*	插入    */
	private String getInsertSql(String table,JSONObject obj) {
		StringBuilder sb=new StringBuilder();
		sb.append("insert into ");
		sb.append(table);
		sb.append(" (");
		StringBuilder sb2=new StringBuilder();
		sb2.append(") values (");
		//"select count(*) from member where ma="+ma;
		//String sqlName = "insert into " + table + " (";
		//String sqlValue = ") values (";
		Iterator iterator = obj.keys();
		while(iterator.hasNext()){
			String key=(String) iterator.next();
			sb.append(key);
			sb.append(',');
            try {
            	sb2.append("'");
            	sb2.append(obj.getString(key));
            	sb2.append("',");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb2.deleteCharAt(sb2.length()-1);
		sb2.append(')');
		sb.append(sb2);
        return sb.toString();
	}
	
	// 成员
	public String regist(JSONObject obj){
		return getInsertSql("member",obj); //"insert into member (membertype, membername, membertele) values("+insertdata+")";		
	}
	// 个人中心信息更新
	public String updateMember(String account ,JSONObject obj){
		return updateCommon("member",account,obj);
	}
	public String checkRegist(String memberaccount){
		return "select count(*) num from member where memberaccount='"+memberaccount+"'";
	}
	public String insertIncomeType(JSONObject obj){
		return getInsertSql("incometype",obj);
	}
	public String insertPayType(JSONObject obj){
		return getInsertSql("paytype",obj);
	}
	public String insertBigPayType(JSONObject obj) {
		return getInsertSql("bigpaytype",obj);
	}
	public String insertConsume(JSONObject obj){
		return getInsertSql("consume",obj);
	}
	public String insertDetail(JSONObject obj){
		return getInsertSql("detail",obj);
	}
	// 添加预算
	public String insertBudget(JSONObject obj){
		return getInsertSql("budget",obj);
	}
	public String selectMember(String memberaccount){
		return getSelect("member","memberaccount",memberaccount);
	}
	public String selectIncomeType(){
		return getSelect("incometype");
	}
	public String selectBigPayType(){
		return getSelect("bigpaytype");
	}
	public String selectPayType(String str){
		return getSelect("paytype","bigpaytypeid",str);
	}
	public String selectInConsume(){
		return getSelect("consume","consumeszclassify","1");
	}
	public String selectPayConsume(){
		return getSelect("consume","consumeszclassify","0");
	}
	// 简单查询    主页面显示明细
	public String timeShowDetail(String account){
		return "select * from detail where memberaccount = '"+account+"' order by timepoint desc";
	}
	public String moneyShowDetail(String account){
		return "select * from detail where memberaccount = '"+account+"' order by detailmoney desc";
	}
	public String selectDetail(JSONObject obj){
		String str = getSelect("detail",obj,"sum(detailmoney) money,consumename name");
		return  (str+" group by consumename");
	}
	// 搜索
	// 全部成员list查询
	public String getAllDetailList(String startTime, String endTime, int szType,String account){
		return "select * from detail where timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify="+szType+" and memberaccount='"+account +"'";
	}
	// 全部成员pie、column查询
	public String getAllDetailPC(String startTime, String endTime, int szType,String account) {
		return "select consumename name,sum(detailmoney) money from detail where timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify="+szType+" and memberaccount='"+account +"' group by consumename";
	}
	// 个人list查询
	public String getPersonalDetailList(String startTime, String endTime, int szType,String consumename,String account) {
		return "select * from detail where timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify="+szType+" and memberaccount='"+account +"' and consumename = '"+consumename+"'";
	} 
	// 个人pie、column查询
	public String getPersonalDetailPC(String startTime, String endTime, int szType,String consumename,String account) {
		return "select detailname name,detailmoney money from detail where timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify="+szType+" and memberaccount='"+account +"' and consumename = '"+consumename+"' group by detailname";
	}
	
	/*	预算查询	   */
	//  全部成员、全部类型 list查询
	public String getAllBudgetList(String startTime, String endTime, int szType,String account){
		return "select * from budget where budgettimepart >='"+startTime+"-01' and budgettimepart<'"+endTime+"-01' and budgetszclassify="+szType+" and memberaccount='"+account+"'";
	} 
	// 全部成员pie、column查询
	public String getAllBudgetPC(String startTime, String endTime, int szType,String account){
		return "select consumename name,sum(budgetmoney) money  from budget where budgettimepart >='"+startTime+"-01' and budgettimepart<'"+endTime+"-01' and budgetszclassify="+szType+" and memberaccount='"+account+"' group by consumename";
	}
	// 全部类型pie、column的预算查询
	public String getAllTypeBudget(String startTime, String endTime, int szType,String account){
		return "select budgetname name,sum(budgetmoney) money  from budget where budgettimepart >='"+startTime+"-01' and budgettimepart<'"+endTime+"-01' and budgetszclassify="+szType+" and memberaccount='"+account+"' group by budgetname";
	} 
	// 个人列表list预算查询
	public String getPersonalBudgetList(String startTime,String endTime,int szType,String consumename,String account){
		return "select * from budget where budgettimepart >='"+startTime+"-01' and budgettimepart<'"+endTime+"-01' and budgetszclassify="+szType+" and memberaccount='"+account+"' and consumename = '"+consumename+"'";
	} 
	// 个人pie、column预算查询 
	public String getPersonalBudgetPC(String startTime,String endTime,int szType,String consumename,String account){
		return "select budgetname name,sum(budgetmoney) money  from budget where budgettimepart >='"+startTime+"-01' and budgettimepart<'"+endTime+"-01' and budgetszclassify="+szType+" and memberaccount='"+account+"' and consumename = '"+consumename+"' group by budgetname";
	}
	
	// 分析
	// 实际总收入
	public String actualAllIn(String startTime,String endTime,String account){
		 return "select ifnull(sum(detailmoney),0) money from detail where timepoint >='"+startTime+"' and timepoint <'"+endTime+"' and memberaccount = '"+account+"' and detailszclassify="+1;
	}
	// 实际总支出  
	public String actualAllPay(String startTime,String endTime,String account){
		 return "select ifnull(sum(detailmoney),0) money from detail where timepoint >='"+startTime+"' and timepoint <'"+endTime+"' and memberaccount = '"+account+"' and detailszclassify="+0;
	}
	// 预算总收入
	public String budgetAllIn(String startTime,String endTime,String account){
		 return "select ifnull(sum(budgetmoney),0) money from budget where budgettimepart >='"+startTime+"' and budgettimepart <'"+endTime+"' and memberaccount = '"+account+"' and budgetszclassify="+1;
	}
	// 预算总支出
	public String budgetAllPay(String startTime,String endTime,String account){
		 return "select ifnull(sum(budgetmoney),0) money from budget where budgettimepart >='"+startTime+"' and budgettimepart <'"+endTime+"' and memberaccount = '"+account+"' and budgetszclassify="+0;
	}
	// 按成员类型实际-预算总支出
	public String consumeActualBudgetData(String startTime,String endTime,String account){
		return "select ifnull(a.budgetAllPay,0) budgetAllPay,ifnull(b.actualAllPay,0) actualAllPay,a.consumename consumename from (select sum(budgetmoney) budgetAllPay ,consumename from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+0+" group by consumename) a  " +
				"left outer  join  (select sum(detailmoney) actualAllPay ,consumename from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+0+" group by consumename)b on a.consumename=b.consumename " +
				"union " +
				"select ifnull(a.budgetAllPay,0) budgetAllPay,ifnull(b.actualAllPay,0) actualAllPay,b.consumename consumename from (select sum(detailmoney) actualAllPay ,consumename from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+0+" group by consumename) b " +
				"left outer  join  (select sum(budgetmoney) budgetAllPay ,consumename from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+0+" group by consumename) a on a.consumename=b.consumename";
	}
	// 按成员类型实际-预算总收入      
	public String consumeActualBudgetDataIn(String startTime,String endTime,String account){
		return "select ifnull(a.budgetAllIn,0) budgetAllIn,ifnull(b.actualAllIn,0) actualAllIn,a.consumename consumename from (select sum(budgetmoney) budgetAllIn ,consumename from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+1+" group by consumename) a  " +
				"left outer  join  (select sum(detailmoney) actualAllIn ,consumename from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+1+" group by consumename)b on a.consumename=b.consumename " +
				"union " +
				"select ifnull(a.budgetAllIn,0) budgetAllIn,ifnull(b.actualAllIn,0) actualAllIn,b.consumename consumename from (select sum(detailmoney) actualAllIn ,consumename from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+1+" group by consumename) b " +
				"left outer  join  (select sum(budgetmoney) budgetAllIn ,consumename from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+1+" group by consumename) a on a.consumename=b.consumename";
	}
	// 按各类型实际-预算总支出  
	public String typeActualBudgetData(String startTime,String endTime,String account){
		return "select ifnull(a.budgetAllPay,0) budgetAllPay,ifnull(b.actualAllPay,0) actualAllPay,a.budgetname bigPayType from (select sum(budgetmoney) budgetAllPay ,budgetname from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+0+" group by budgetname) a  " +
				"left outer  join  (select sum(detailmoney) actualAllPay ,detailname from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+0+" group by detailname)b on a.budgetname=b.detailname " +
				"union " +
				"select ifnull(a.budgetAllPay,0) budgetAllPay,ifnull(b.actualAllPay,0) actualAllPay,b.detailname bigPayType from (select sum(detailmoney) actualAllPay ,detailname from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+0+" group by detailname) b " +
				"left outer  join  (select sum(budgetmoney) budgetAllPay ,budgetname from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+0+" group by consumename) a on a.budgetname=b.detailname";
	}  
	// 按各类型实际-预算总收入    
	public String typeActualBudgetDataIn(String startTime,String endTime,String account){
		return "select ifnull(a.budgetAllIn,0) budgetAllIn,ifnull(b.actualAllIn,0) actualAllIn,a.budgetname incomeType from (select sum(budgetmoney) budgetAllIn ,budgetname from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+1+" group by budgetname) a  " +
				"left outer  join  (select sum(detailmoney) actualAllIn ,detailname from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+1+" group by detailname) b on a.budgetname=b.detailname " +
				"union " +
				"select ifnull(a.budgetAllIn,0) budgetAllIn,ifnull(b.actualAllIn,0) actualAllIn,b.detailname incomeType from (select sum(detailmoney) actualAllIn ,detailname from detail where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = "+1+" group by detailname) b " +
				"left outer  join  (select sum(budgetmoney) budgetAllIn ,budgetname from budget where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify="+1+" group by budgetname) a on a.budgetname=b.detailname";
	}
	// 按月份实际-预算总支出 
	public String monthActualBudgetData(String startTime,String endTime,String account){
		return "select * from (select ifnull(a.budgetAllPay,0) budgetAllPay,ifnull(b.actualAllPay,0) actualAllPay,a.atime month from " +
				"(select sum(budgetmoney) budgetAllPay ,substr(budgettimepart,0,8) atime from budget " +
				"where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify=0 group by atime) a " +
				"left outer  join  " +
				"(select sum(detailmoney) actualAllPay ,substr(timepoint,0,8) atime from detail " +
				"where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = 0 group by atime)b " +
				"on a.atime=b.atime " +
				"union " +
				"select ifnull(a.budgetAllPay,0) budgetAllPay,ifnull(b.actualAllPay,0) actualAllPay,b.atime month from " +
				"(select sum(detailmoney) actualAllPay ,substr(timepoint,0,8) atime from detail " +
				"where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = 0 group by atime) b " +
				"left outer  join  " +
				"(select sum(budgetmoney) budgetAllPay ,substr(budgettimepart,0,8) atime from budget " +
				"where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify=0 group by atime) a " +
				"on a.atime=b.atime) order by month";
	}
    // 按月份实际-预算总收入
	public String monthActualBudgetDataIn(String startTime,String endTime,String account){
		return "select * from (select ifnull(a.budgetAllIn,0) budgetAllIn,ifnull(b.actualAllIn,0) actualAllIn,a.atime month from " +
				"(select sum(budgetmoney) budgetAllIn ,substr(budgettimepart,0,8) atime from budget " +
				"where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify=1 group by atime) a " +
				"left outer  join  " +
				"(select sum(detailmoney) actualAllIn ,substr(timepoint,0,8) atime from detail " +
				"where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = 1 group by atime)b " +
				"on a.atime=b.atime " +
				"union " +
				"select ifnull(a.budgetAllIn,0) budgetAllIn,ifnull(b.actualAllIn,0) actualAllIn,b.atime month from " +
				"(select sum(detailmoney) actualAllIn ,substr(timepoint,0,8) atime from detail " +
				"where memberaccount = '"+account+"' and timepoint>='"+startTime+"' and timepoint<'"+endTime+"' and detailszclassify = 1 group by atime) b " +
				"left outer  join  " +
				"(select sum(budgetmoney) budgetAllIn ,substr(budgettimepart,0,8) atime from budget " +
				"where memberaccount = '"+account+"' and budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and budgetszclassify=1 group by atime) a " +
				"on a.atime=b.atime) order by month";
	} 
	public String getDeleteDetail(String id){
		return getDeleteCommon("detail","detailid",id);
	}
	public String getDeleteCommon(String table,String name,String id){
		return "delete from "+table+" where "+name+"="+id;
	}
	public String modifyBudget(String account, String budgetid, JSONObject obj) {
		String str= updateCommon("budget",account,obj);
		return str+" and budgetid='"+ budgetid +"'";
	}
	public String getDeleteBudget(String id) {
		return getDeleteCommon("budget","budgetid",id);
	}
	public String allBudgetList(String startTime, String endTime, String account) {
		return "select * from budget where budgettimepart>='"+startTime+"' and budgettimepart<'"+endTime+"' and memberaccount='"+account+"'";
	}
	public String nowMonthAllIn(String date, String account) {
		return "select sum(detailmoney) inMoney from detail where detailszclassify = 1 and memberaccount ='"+account+"' and substr(timepoint,0,8)="+date+" group by substr(timepoint,0,8)";
	}
	public String nowMonthAllPay(String date, String account) {
		return "select sum(detailmoney) payMoney from detail where detailszclassify = 0 and memberaccount ='"+account+"' and substr(timepoint,0,8)="+date+" group by substr(timepoint,0,8)";
	}
}
