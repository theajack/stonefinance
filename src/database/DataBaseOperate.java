package database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sqlstring.SqlString;

import Obj.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

public class DataBaseOperate {  
	private SQLiteDatabase db;
	SqlString sqlStr=new SqlString();
	private String dbPath="/data/data/com.example.stonefinance/stonefinance.db";
	private String dbAbsolutePath="/data/com.example.stonefinance/stonefinance.db";
	private String backUpPath="stonefinancebackup.db";
	public DataBaseOperate(){
		createDbAndTable();
		String dataPath=Environment.getDataDirectory().getAbsolutePath();//  ”/data“
		this.dbAbsolutePath=dataPath+dbAbsolutePath;
		//this.backUpPath=dataPath+backUpPath;
		this.backUpPath=DB.filePath+backUpPath;
		File f=new File(backUpPath);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public void dataBackUp(){
		this.closeDB();
		File fromFile=new File(dbAbsolutePath);
		File toFile=new File(backUpPath);
		copyfile(fromFile,toFile,true);
		this.openDb();
	}
	public void dataRecover(){
		this.closeDB();
		File fromFile=new File(backUpPath);
		File toFile=new File(dbAbsolutePath);
		copyfile(fromFile,toFile,true);
		this.openDb();
	}
	public void copyfile(File fromFile, File toFile,Boolean rewrite )
	{
		if (!fromFile.exists()) {
			return;
		}
		if (!fromFile.isFile()) {
			return ;
		}
		if (!fromFile.canRead()) {
			return ;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}
		//当文件不存时，canWrite一直返回的都是false
		// if (!toFile.canWrite()) {
		// MessageDialog.openError(new Shell(),"错误信息","不能够写将要复制的目标文件" + toFile.getPath());
		// Toast.makeText(this,"不能够写将要复制的目标文件", Toast.LENGTH_SHORT);
		// return ;
		// }
		try {
			java.io.FileInputStream fosfrom = new java.io.FileInputStream(fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
			fosto.write(bt, 0, c); //将内容写到新文件当中
			}
			fosfrom.close();
			fosto.close();
		} catch (Exception ex) {
		}
	}
	private void createDbAndTable() {
		// TODO Auto-generated method stub
		try	{
			if(!isDbExist()){
				db = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
				createAllTable();
				insertTables();
			}else{
				openDb();
			}
		}catch(Exception e)	{
    	}
	}
	// 判定文件是否存在，如存在返回true
	private boolean isDbExist(){
		File f=new File(dbPath);
		return f.exists();
	}
	public void openDb(){
		try	{
			db = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READWRITE);
		}catch(Exception e)	{
    	}
	}
	public void closeDB(){
		db.close();
	}
	private void createAllTable(){
		createMember();
		createIncometype();
		createBigPayType();
		createPayType();
		createConsume();
		createDetail();
		createBudget();
	}
	private void insertTables(){
		insertIncomeType();
		insertBigPayType();
		insertPayType();
		insertConsume();
	}
	private void insertIncomeType(){
		IncomeType[] incomeType = new IncomeType[]{
				new IncomeType("工资薪水"),
				new IncomeType("奖金"),
				new IncomeType("兼职外快"),
				new IncomeType("福利补贴"),
				new IncomeType("生活费"),
				new IncomeType("退款返款"),
				new IncomeType("公积金"),
				new IncomeType("礼金"),
				new IncomeType("基金"),
				new IncomeType("红包"),
				new IncomeType("股票"),
				new IncomeType("营业收入"),
				new IncomeType("租金"),
				new IncomeType("利息"),
				new IncomeType("其他")
		};
		for(int i=0;i<incomeType.length;i++){
			execSql(sqlStr.insertIncomeType(incomeType[i].getObjectInfo()));
		}
	}

	private void insertBigPayType(){
		BigPayType[] bigPayType = new BigPayType[]{
			new BigPayType("餐饮 "),
			new BigPayType("交通"),
			new BigPayType("购物"),
			new BigPayType("娱乐"),
			new BigPayType("投资"),
			new BigPayType("医教"),
			new BigPayType("居家"),
			new BigPayType("人情"),
			new BigPayType("生意")
		};
		for(int i = 0;i < bigPayType.length;i++ ){
			execSql(sqlStr.insertBigPayType(bigPayType[i].getObjectInfo()));
		} 
	}
	private void insertPayType(){
		PayType[] payType = new PayType[]{
				
				//餐饮 
				new PayType("1","早餐"),
				new PayType("1","午餐"),
				new PayType("1","晚餐"),
				new PayType("1","饮料水果"),
				new PayType("1","零食烟酒"),
				new PayType("1","买卖原料"),
				new PayType("1","夜宵"),
				new PayType("1","油盐酱醋"),
				new PayType("1","餐饮其他"),
				//交通 
				new PayType("2","打车"),
				new PayType("2","公交"),
				new PayType("2","长途汽车"),
				new PayType("2","地铁"),
				new PayType("2","火车"),
				new PayType("2","洗车"),
				new PayType("2","自行车"),
				new PayType("2","飞机"),
				new PayType("2","加油"),
				new PayType("2","停车费"),
				new PayType("2","罚款赔款"),
				new PayType("2","车款车贷"),
				new PayType("2","交通其他"),
				//购物
				new PayType("3","服饰鞋包"),
				new PayType("3","家居百货"),
				new PayType("3","烟酒"),
				new PayType("3","化妆护肤"),
				new PayType("3","电子数码"),
				new PayType("3","宝宝用品"),
				new PayType("3","家具家纺"),
				new PayType("3","报刊书籍"),
				new PayType("3","电器"),
				new PayType("3","珠宝首饰"),
				new PayType("3","文具玩具"),
				new PayType("3","摄影文印"),
				new PayType("3","购物其他"),
				//娱乐
				new PayType("4","旅游度假"),
				new PayType("4","网游电玩"),
				new PayType("4","电影"),
				new PayType("4","运动健身"),
				new PayType("4","卡拉OK"),
				new PayType("4","茶酒咖啡"),
				new PayType("4","歌舞演出"),
				new PayType("4","电视"),
				new PayType("4","花鸟宠物"),
				new PayType("4","麻将棋牌"),
				new PayType("4","聚会玩乐"),
				new PayType("4","娱乐其他"),
				//投资
				new PayType("5","股票"),
				new PayType("5","基金"),
				new PayType("5","理财产品"),
				new PayType("5","余额宝"),
				new PayType("5","银行存款"),
				new PayType("5","保险"),
				new PayType("5","P2P"),
				new PayType("5","证券期货"),
				new PayType("5","出资"),
				new PayType("5","外汇"),
				new PayType("5","投资贷款"),
				new PayType("5","利息支出"),
				new PayType("5","收藏品"),
				new PayType("5","投资其他"),
				//医教   
				new PayType("6","医疗药品"),
				new PayType("6","挂号门诊"),
				new PayType("6","养生保健"),
				new PayType("6","住院费"),
				new PayType("6","养老院"),
				new PayType("6","学杂教材"),
				new PayType("6","培训考试"),
				new PayType("6","家教补习"),
				new PayType("6","学费"),
				new PayType("6","幼儿教育"),
				new PayType("6","出国留学"),
				new PayType("6","助学贷款"),
				new PayType("6","医教其他"),
				//居家
				new PayType("7","手机电话"),
				new PayType("7","房贷房贷"),
				new PayType("7","水电燃气"),
				new PayType("7","美发美容"),
				new PayType("7","住宿房租"),
				new PayType("7","材料建材"),
				new PayType("7","电脑宽带"),
				new PayType("7","快递邮证"),
				new PayType("7","物业"),
				new PayType("7","家政服务"),
				new PayType("7","生活费"),
				new PayType("7","婚庆摄影"),
				new PayType("7","居家其他"),
				//人情
				new PayType("8","礼金红包"),
				new PayType("8","物品"),
				new PayType("8","请客"),
				new PayType("8","孝敬"),
				new PayType("8","给予"),
				new PayType("8","慈善捐款"),
				new PayType("8","代付款"),
				new PayType("8","人情其他"),
				//生意
				new PayType("9","进货采购"),
				new PayType("9","人工支出"),
				new PayType("9","材料辅料"),
				new PayType("9","工程付款"),
				new PayType("9","交通运输"),
				new PayType("9","运营费"),
				new PayType("9","办公费用"),
				new PayType("9","营销广告"),
				new PayType("9","店面租金"),
				new PayType("9","生意其他"),	
		};
		for(int i = 0;i < payType.length;i++ ){
			execSql(sqlStr.insertPayType(payType[i].getObjectInfo()));
		}
	}
	private void insertConsume(){
		Consume[] consume = new Consume[]{
				new Consume("自己","1"),
				new Consume("自己","0"),
				new Consume("配偶","1"),
				new Consume("配偶","0"),
				new Consume("孩子","1"),
				new Consume("孩子","0"),
				new Consume("父母","1"),
				new Consume("父母","0"),
				new Consume("爷爷奶奶","1"),
				new Consume("爷爷奶奶","0"),
				new Consume("亲戚","0"),
				new Consume("朋友","0"),
				new Consume("家庭公用","0"),
				new Consume("家庭公用","1")
		};
		for(int i=0;i<consume.length;i++){
			execSql(sqlStr.insertConsume(consume[i].getObjectInfo()));
		}
	}
	
	/*==============================================================*/
	/* Table: consume                                                */
	/*==============================================================*/
	private void createConsume(){
		String consumeSql="create table if not exists consume ("
				   +"consumeid           INTEGER        PRIMARY KEY AUTOINCREMENT,"
				   +"consumename        varchar(20)         null,"
				   +"consumeszclassify    bit       		null)";
				   db.execSQL(consumeSql);
	}
 
	/*==============================================================*/
	/* Table: budget                                                */
	/*==============================================================*/
	private void createBudget(){
		String budgetSql="create table if not exists budget ("
				   +"budgetid             INTEGER        PRIMARY KEY AUTOINCREMENT,"
				   +"memberaccount       varchar(20)           null,"
				   +"bigpaytypeid            INTEGER              null,"
				   +"incometypeid         INTEGER              null,"
				   +"budgetname           varchar(20)          null,"
				   +"budgetmoney          money                null,"
				   +"budgettimepart       DATE                 null,"
				   +"budgetszclassify     bit                  null,"
				   +"consumename          varchar(20)          null,"
				   +"budgetmark           varchar(20)          null,"
				   +"constraint FK_member foreign key (memberaccount) references member(memberaccount),"
				   +"constraint FK_incometype foreign key (incometypeid) references incometype(incometypeid),"
				   +"constraint FK_bigpaytype foreign key (bigpaytypeid) references bigpaytype(bigpaytypeid))";
				   db.execSQL(budgetSql);
	}
	
	
	/*==============================================================*/
	/* Table: detail                                                */
	/*==============================================================*/
	private void createDetail(){
		String detailSql = "create table if not exists detail ("
				   +"detailid             INTEGER        PRIMARY KEY AUTOINCREMENT,"
				   +"memberaccount        varchar(20)          null,"
				   +"paytypeid            INTEGER              null,"
				   +"incometypeid         INTEGER              null,"
				   +"detailname           varchar(20)          null,"
				   +"timepoint            DATE                 null,"
				   +"adress               varchar(20)          null,"
				   +"detailszclassify     bit                  null,"
				   +"detailmoney          money                null,"
				   +"detailmark           varchar(20)          null,"
				   +"consumename          varchar(20)          null,"
				   +"constraint FK_member foreign key (memberaccount) references member(memberaccount),"
				   +"constraint FK_incometype foreign key (incometypeid) references incometype(incometypeid),"
				   +"constraint FK_paytype foreign key (paytypeid) references paytype(paytypeid))";
				db.execSQL(detailSql);			
	}
	
	/*==============================================================*/
	/* Table: incometype                                            */
	/*==============================================================*/
	private void createIncometype(){
		String incomeTypeSql = "create table if not exists incometype ("
				+" incometypeid         INTEGER          PRIMARY KEY AUTOINCREMENT,"
				+" incometypename       varchar(20)          null)";
		db.execSQL(incomeTypeSql);
	}
    /*==============================================================*/
	/* Table: member                                                */
	/*==============================================================*/
	private void createMember(){
		String memberSql = "create table if not exists member ("
			   +"memberaccount		varchar(20)      PRIMARY KEY,"
			   +"password		    varchar(20)         not null,"
			   +"membername         varchar(20)          null,"
			   +"memberbir          varchar(20)          null,"
			   +"membersex          varchar(20)          null,"
			   +"membertele         varchar(20)          null,"
			   +"question          varchar(20)          null,"
			   +"answer            varchar(20)          null)";
		db.execSQL(memberSql);
	}
	/*==============================================================*/
	/* Table: bigpaytype                                            */
	/*==============================================================*/
	private void createBigPayType(){
		String payTypeSql = "create table if not exists bigpaytype ("
			   +" bigpaytypeid            INTEGER         PRIMARY KEY AUTOINCREMENT,"
			   +" bigpaytypename          varchar(20)          null)";
		db.execSQL(payTypeSql);
	}
	/*==============================================================*/
	/* Table: paytype                                               */
	/*==============================================================*/
	private void createPayType(){
		String payTypeSql = "create table if not exists paytype ("
			   +" paytypeid            INTEGER         PRIMARY KEY AUTOINCREMENT,"
			   +" bigpaytypeid         INTEGER          null,"
			   +" paytypename          varchar(20)          null,"
			   +"constraint FK_bigpaytype foreign key (bigpaytypeid) references bigpaytype(bigpaytypeid))";
		db.execSQL(payTypeSql);
	}
	/*			公用的方法			*/
	// 查询结果
	public String getSelectCommon(String sql){
		JSONArray ja=getSelectArray(sql);
	    return ja.toString();
	}
	public JSONObject getSelectSingle(String sql){
		JSONArray ja=getSelectArray(sql);
		try {
			return (JSONObject) ja.get(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private JSONArray getSelectArray(String sql){
		JSONArray ja=new JSONArray();
		Cursor cur=db.rawQuery(sql, new String[]{});  
		int n=cur.getColumnCount();
		String[] columName=cur.getColumnNames();
	    while(cur.moveToNext()){
			JSONObject json=new JSONObject();
	    	for(int i=0;i<n;i++){
	    		try {
					json.put(columName[i], cur.getString(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	ja.put(json);
	    }
	    cur.close();
	    return ja;
	}  

	
	// 数据库除查询方法之外的执行方法
	public String execSql(String sql){
		try{
			db.execSQL(sql);
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	public int getCount(String sql){
		JSONObject result=this.getSelectSingle(sql);
		try {
			return Integer.parseInt(result.getString("num"));
		} catch (Exception e) {
			return 1;
		}
	}
	
/*			非公用方法				*/
	public int getMaNum(JSONObject obj) {
		try {
			return this.getCount(this.sqlStr.checkRegist(obj.getString("memberaccount")));
		} catch (JSONException e) {
			return 1;
		}
	}

	public void regist(JSONObject memberJson) {
		String sql=sqlStr.regist(memberJson);
		execSql(sql);
	}
	/* 个人中心信息更新 */
	public void updateMember(String account ,JSONObject obj){
		String sql = sqlStr.updateMember(account,obj);
		execSql(sql);
	}

//  找回密码所需，查询特定账号用户信息    // 绑定用户信息
	public JSONObject selectMember(String memberaccount) {
		return getSelectSingle(sqlStr.selectMember(memberaccount));	
	}

	public String selectIncomeType(){
		return getSelectCommon(sqlStr.selectIncomeType());
	}
	public String selectBigPayType(){
		return getSelectCommon(sqlStr.selectBigPayType());
	}
    public String selectPayType(String str){
    	return getSelectCommon(sqlStr.selectPayType(str));
    }
	public String selectInConsume(){
		return getSelectCommon(sqlStr.selectInConsume());
	}
	public String selectPayConsume(){
		return getSelectCommon(sqlStr.selectPayConsume());
	}
	public void insertDetail(JSONObject obj) {
		String sql=sqlStr.insertDetail(obj);
		execSql(sql);
	}
	public void insertIncomeType(JSONObject obj){
		String sql = sqlStr.insertIncomeType(obj);
		execSql(sql);
	}
	public void insertBigPayType(JSONObject obj){
		String sql = sqlStr.insertBigPayType(obj);
		execSql(sql);
	}
	// 简单查询  主页显示明细
	public String timeShowDetail(String account){
		return getSelectCommon(sqlStr.timeShowDetail(account));
	}
	public String moneyShowDetail(String account){
		return getSelectCommon(sqlStr.moneyShowDetail(account));
	}
	// 报表查询，根据时间、收支分类查询
	public String selectDetail(JSONObject obj){
		return getSelectCommon(sqlStr.selectDetail(obj));
	}
	
	// 添加预算
	public void insertBudget(JSONObject budgetJson) {
		String sql=sqlStr.insertBudget(budgetJson);
		execSql(sql);
	}
	public void insertConsume(JSONObject consumeJson){
		execSql(sqlStr.insertConsume(consumeJson));
	}
	// 全部成员list查询
	public String getAllDetailList(String startTime, String endTime, int szType,String account){
		return this.getSelectCommon(sqlStr.getAllDetailList(startTime,endTime,szType,account));
	}
	// 全部成员pie、column查询
	public String getAllDetailPC(String startTime, String endTime, int szType,String account) {
		return this.getSelectCommon(sqlStr.getAllDetailPC(startTime,endTime,szType,account));
	}
	// 个人list查询
	public String getPersonalDetailList(String startTime, String endTime, int szType,String consumename,String account) {
		return this.getSelectCommon(sqlStr.getPersonalDetailList(startTime,endTime,szType,consumename,account));
	}
	// 个人pie、column查询
	public String getPersonalDetailPC(String startTime, String endTime, int szType,String consumename,String account) {
		return this.getSelectCommon(sqlStr.getPersonalDetailPC(startTime,endTime,szType,consumename,account));
	}
	
	
	/*	  预算搜索 	*/
	//  全部成员 list查询
	public String getAllBudgetList(String startTime, String endTime, int szType,String account){
		return this.getSelectCommon(sqlStr.getAllBudgetList(startTime,endTime,szType,account));
	}
	// 全部成员pie、column查询
	public String getAllBudgetPC(String startTime, String endTime, int szType,String account) {
		return this.getSelectCommon(sqlStr.getAllBudgetPC(startTime,endTime,szType,account));
	}
	// 全部类型的预算查询
	public String getAllTypeBudget(String startTime,String endTime,int szType,String account){
		return this.getSelectCommon(sqlStr.getAllTypeBudget(startTime,endTime,szType,account));
	}
	// 个人列表list预算查询
	public String getPersonalBudgetList(String startTime,String endTime,int szType,String consumename,String account){
		return this.getSelectCommon(sqlStr.getPersonalBudgetList(startTime,endTime,szType,consumename,account));
	}
	// 个人pie、column预算查询
	public String getPersonalBudgetPC(String startTime,String endTime,int szType,String consumename,String account){
		return this.getSelectCommon(sqlStr.getPersonalBudgetPC(startTime,endTime,szType,consumename,account));
	}
	
	// 分析
	// 实际总收入
	public String actualAllIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.actualAllIn(startTime,endTime,account));
	}
	// 实际总支出
	public String actualAllPay(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.actualAllPay(startTime,endTime,account));
	}
	// 预算总收入
	public String budgetAllIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.budgetAllIn(startTime,endTime,account));
	}
	// 预算总支出
	public String budgetAllPay(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.budgetAllPay(startTime,endTime,account));
	}
 	// 按成员类型实际-预算总支出
	public String consumeActualBudgetData(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.consumeActualBudgetData(startTime,endTime,account));
	}
	// 按成员类型实际-预算总收入
	public String consumeActualBudgetDataIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.consumeActualBudgetDataIn(startTime,endTime,account));
	}
 	// 按各类型实际-预算总支出
	public String typeActualBudgetData(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.typeActualBudgetData(startTime,endTime,account));
	}
	// 按各类型实际-预算总收入
	public String typeActualBudgetDataIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.typeActualBudgetDataIn(startTime,endTime,account));
	}
    // 按月份实际-预算总支出
	public String monthActualBudgetData(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.monthActualBudgetData(startTime,endTime,account));
	}
    // 按月份实际-预算总收入
	public String monthActualBudgetDataIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.monthActualBudgetDataIn(startTime,endTime,account));
	}
	public String modifyDetail(JSONObject obj,String account,String detailId) {
		return this.execSql(sqlStr.modifyDetail(account, detailId, obj));
	}  
	public String deleteDetailById(String detailId) {
		return execSql(sqlStr.getDeleteDetail(detailId));
	}
	public String deleteBudgetById(String detailId) {
		return this.execSql(sqlStr.getDeleteBudget(detailId));
	}
	public String modifyBudget(JSONObject obj, String account, String budgetid) {
		return this.execSql(sqlStr.modifyBudget(account, budgetid, obj)); 
	}
	public String allBudgetList(String startTime, String endTime, String account) {
		return getSelectCommon(sqlStr.allBudgetList(startTime,endTime,account));
	}
	public String nowMonthAllIn(String date, String account) {
		return getSelectCommon(sqlStr.nowMonthAllIn(date,account));
	}
	public String nowMonthAllPay(String date, String account) {
		return getSelectCommon(sqlStr.nowMonthAllPay(date,account));
	}
	
	



}
