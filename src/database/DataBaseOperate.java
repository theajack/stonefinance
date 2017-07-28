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
		String dataPath=Environment.getDataDirectory().getAbsolutePath();//  ��/data��
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
		//���ļ�����ʱ��canWriteһֱ���صĶ���false
		// if (!toFile.canWrite()) {
		// MessageDialog.openError(new Shell(),"������Ϣ","���ܹ�д��Ҫ���Ƶ�Ŀ���ļ�" + toFile.getPath());
		// Toast.makeText(this,"���ܹ�д��Ҫ���Ƶ�Ŀ���ļ�", Toast.LENGTH_SHORT);
		// return ;
		// }
		try {
			java.io.FileInputStream fosfrom = new java.io.FileInputStream(fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
			fosto.write(bt, 0, c); //������д�����ļ�����
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
	// �ж��ļ��Ƿ���ڣ�����ڷ���true
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
				new IncomeType("����нˮ"),
				new IncomeType("����"),
				new IncomeType("��ְ���"),
				new IncomeType("��������"),
				new IncomeType("�����"),
				new IncomeType("�˿��"),
				new IncomeType("������"),
				new IncomeType("���"),
				new IncomeType("����"),
				new IncomeType("���"),
				new IncomeType("��Ʊ"),
				new IncomeType("Ӫҵ����"),
				new IncomeType("���"),
				new IncomeType("��Ϣ"),
				new IncomeType("����")
		};
		for(int i=0;i<incomeType.length;i++){
			execSql(sqlStr.insertIncomeType(incomeType[i].getObjectInfo()));
		}
	}

	private void insertBigPayType(){
		BigPayType[] bigPayType = new BigPayType[]{
			new BigPayType("���� "),
			new BigPayType("��ͨ"),
			new BigPayType("����"),
			new BigPayType("����"),
			new BigPayType("Ͷ��"),
			new BigPayType("ҽ��"),
			new BigPayType("�Ӽ�"),
			new BigPayType("����"),
			new BigPayType("����")
		};
		for(int i = 0;i < bigPayType.length;i++ ){
			execSql(sqlStr.insertBigPayType(bigPayType[i].getObjectInfo()));
		} 
	}
	private void insertPayType(){
		PayType[] payType = new PayType[]{
				
				//���� 
				new PayType("1","���"),
				new PayType("1","���"),
				new PayType("1","���"),
				new PayType("1","����ˮ��"),
				new PayType("1","��ʳ�̾�"),
				new PayType("1","����ԭ��"),
				new PayType("1","ҹ��"),
				new PayType("1","���ν���"),
				new PayType("1","��������"),
				//��ͨ 
				new PayType("2","��"),
				new PayType("2","����"),
				new PayType("2","��;����"),
				new PayType("2","����"),
				new PayType("2","��"),
				new PayType("2","ϴ��"),
				new PayType("2","���г�"),
				new PayType("2","�ɻ�"),
				new PayType("2","����"),
				new PayType("2","ͣ����"),
				new PayType("2","�������"),
				new PayType("2","�����"),
				new PayType("2","��ͨ����"),
				//����
				new PayType("3","����Ь��"),
				new PayType("3","�ҾӰٻ�"),
				new PayType("3","�̾�"),
				new PayType("3","��ױ����"),
				new PayType("3","��������"),
				new PayType("3","������Ʒ"),
				new PayType("3","�Ҿ߼ҷ�"),
				new PayType("3","�����鼮"),
				new PayType("3","����"),
				new PayType("3","�鱦����"),
				new PayType("3","�ľ����"),
				new PayType("3","��Ӱ��ӡ"),
				new PayType("3","��������"),
				//����
				new PayType("4","���ζȼ�"),
				new PayType("4","���ε���"),
				new PayType("4","��Ӱ"),
				new PayType("4","�˶�����"),
				new PayType("4","����OK"),
				new PayType("4","��ƿ���"),
				new PayType("4","�����ݳ�"),
				new PayType("4","����"),
				new PayType("4","�������"),
				new PayType("4","�齫����"),
				new PayType("4","�ۻ�����"),
				new PayType("4","��������"),
				//Ͷ��
				new PayType("5","��Ʊ"),
				new PayType("5","����"),
				new PayType("5","��Ʋ�Ʒ"),
				new PayType("5","��"),
				new PayType("5","���д��"),
				new PayType("5","����"),
				new PayType("5","P2P"),
				new PayType("5","֤ȯ�ڻ�"),
				new PayType("5","����"),
				new PayType("5","���"),
				new PayType("5","Ͷ�ʴ���"),
				new PayType("5","��Ϣ֧��"),
				new PayType("5","�ղ�Ʒ"),
				new PayType("5","Ͷ������"),
				//ҽ��   
				new PayType("6","ҽ��ҩƷ"),
				new PayType("6","�Һ�����"),
				new PayType("6","��������"),
				new PayType("6","סԺ��"),
				new PayType("6","����Ժ"),
				new PayType("6","ѧ�ӽ̲�"),
				new PayType("6","��ѵ����"),
				new PayType("6","�ҽ̲�ϰ"),
				new PayType("6","ѧ��"),
				new PayType("6","�׶�����"),
				new PayType("6","������ѧ"),
				new PayType("6","��ѧ����"),
				new PayType("6","ҽ������"),
				//�Ӽ�
				new PayType("7","�ֻ��绰"),
				new PayType("7","��������"),
				new PayType("7","ˮ��ȼ��"),
				new PayType("7","��������"),
				new PayType("7","ס�޷���"),
				new PayType("7","���Ͻ���"),
				new PayType("7","���Կ��"),
				new PayType("7","�����֤"),
				new PayType("7","��ҵ"),
				new PayType("7","��������"),
				new PayType("7","�����"),
				new PayType("7","������Ӱ"),
				new PayType("7","�Ӽ�����"),
				//����
				new PayType("8","�����"),
				new PayType("8","��Ʒ"),
				new PayType("8","���"),
				new PayType("8","Т��"),
				new PayType("8","����"),
				new PayType("8","���ƾ��"),
				new PayType("8","������"),
				new PayType("8","��������"),
				//����
				new PayType("9","�����ɹ�"),
				new PayType("9","�˹�֧��"),
				new PayType("9","���ϸ���"),
				new PayType("9","���̸���"),
				new PayType("9","��ͨ����"),
				new PayType("9","��Ӫ��"),
				new PayType("9","�칫����"),
				new PayType("9","Ӫ�����"),
				new PayType("9","�������"),
				new PayType("9","��������"),	
		};
		for(int i = 0;i < payType.length;i++ ){
			execSql(sqlStr.insertPayType(payType[i].getObjectInfo()));
		}
	}
	private void insertConsume(){
		Consume[] consume = new Consume[]{
				new Consume("�Լ�","1"),
				new Consume("�Լ�","0"),
				new Consume("��ż","1"),
				new Consume("��ż","0"),
				new Consume("����","1"),
				new Consume("����","0"),
				new Consume("��ĸ","1"),
				new Consume("��ĸ","0"),
				new Consume("үү����","1"),
				new Consume("үү����","0"),
				new Consume("����","0"),
				new Consume("����","0"),
				new Consume("��ͥ����","0"),
				new Consume("��ͥ����","1")
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
	/*			���õķ���			*/
	// ��ѯ���
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

	
	// ���ݿ����ѯ����֮���ִ�з���
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
	
/*			�ǹ��÷���				*/
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
	/* ����������Ϣ���� */
	public void updateMember(String account ,JSONObject obj){
		String sql = sqlStr.updateMember(account,obj);
		execSql(sql);
	}

//  �һ��������裬��ѯ�ض��˺��û���Ϣ    // ���û���Ϣ
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
	// �򵥲�ѯ  ��ҳ��ʾ��ϸ
	public String timeShowDetail(String account){
		return getSelectCommon(sqlStr.timeShowDetail(account));
	}
	public String moneyShowDetail(String account){
		return getSelectCommon(sqlStr.moneyShowDetail(account));
	}
	// �����ѯ������ʱ�䡢��֧�����ѯ
	public String selectDetail(JSONObject obj){
		return getSelectCommon(sqlStr.selectDetail(obj));
	}
	
	// ���Ԥ��
	public void insertBudget(JSONObject budgetJson) {
		String sql=sqlStr.insertBudget(budgetJson);
		execSql(sql);
	}
	public void insertConsume(JSONObject consumeJson){
		execSql(sqlStr.insertConsume(consumeJson));
	}
	// ȫ����Աlist��ѯ
	public String getAllDetailList(String startTime, String endTime, int szType,String account){
		return this.getSelectCommon(sqlStr.getAllDetailList(startTime,endTime,szType,account));
	}
	// ȫ����Աpie��column��ѯ
	public String getAllDetailPC(String startTime, String endTime, int szType,String account) {
		return this.getSelectCommon(sqlStr.getAllDetailPC(startTime,endTime,szType,account));
	}
	// ����list��ѯ
	public String getPersonalDetailList(String startTime, String endTime, int szType,String consumename,String account) {
		return this.getSelectCommon(sqlStr.getPersonalDetailList(startTime,endTime,szType,consumename,account));
	}
	// ����pie��column��ѯ
	public String getPersonalDetailPC(String startTime, String endTime, int szType,String consumename,String account) {
		return this.getSelectCommon(sqlStr.getPersonalDetailPC(startTime,endTime,szType,consumename,account));
	}
	
	
	/*	  Ԥ������ 	*/
	//  ȫ����Ա list��ѯ
	public String getAllBudgetList(String startTime, String endTime, int szType,String account){
		return this.getSelectCommon(sqlStr.getAllBudgetList(startTime,endTime,szType,account));
	}
	// ȫ����Աpie��column��ѯ
	public String getAllBudgetPC(String startTime, String endTime, int szType,String account) {
		return this.getSelectCommon(sqlStr.getAllBudgetPC(startTime,endTime,szType,account));
	}
	// ȫ�����͵�Ԥ���ѯ
	public String getAllTypeBudget(String startTime,String endTime,int szType,String account){
		return this.getSelectCommon(sqlStr.getAllTypeBudget(startTime,endTime,szType,account));
	}
	// �����б�listԤ���ѯ
	public String getPersonalBudgetList(String startTime,String endTime,int szType,String consumename,String account){
		return this.getSelectCommon(sqlStr.getPersonalBudgetList(startTime,endTime,szType,consumename,account));
	}
	// ����pie��columnԤ���ѯ
	public String getPersonalBudgetPC(String startTime,String endTime,int szType,String consumename,String account){
		return this.getSelectCommon(sqlStr.getPersonalBudgetPC(startTime,endTime,szType,consumename,account));
	}
	
	// ����
	// ʵ��������
	public String actualAllIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.actualAllIn(startTime,endTime,account));
	}
	// ʵ����֧��
	public String actualAllPay(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.actualAllPay(startTime,endTime,account));
	}
	// Ԥ��������
	public String budgetAllIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.budgetAllIn(startTime,endTime,account));
	}
	// Ԥ����֧��
	public String budgetAllPay(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.budgetAllPay(startTime,endTime,account));
	}
 	// ����Ա����ʵ��-Ԥ����֧��
	public String consumeActualBudgetData(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.consumeActualBudgetData(startTime,endTime,account));
	}
	// ����Ա����ʵ��-Ԥ��������
	public String consumeActualBudgetDataIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.consumeActualBudgetDataIn(startTime,endTime,account));
	}
 	// ��������ʵ��-Ԥ����֧��
	public String typeActualBudgetData(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.typeActualBudgetData(startTime,endTime,account));
	}
	// ��������ʵ��-Ԥ��������
	public String typeActualBudgetDataIn(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.typeActualBudgetDataIn(startTime,endTime,account));
	}
    // ���·�ʵ��-Ԥ����֧��
	public String monthActualBudgetData(String startTime,String endTime,String account){
		return getSelectCommon(sqlStr.monthActualBudgetData(startTime,endTime,account));
	}
    // ���·�ʵ��-Ԥ��������
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
