
package ivan.db;

import java.sql.* ;


public class DataBaseConnection{
	public static final String DBDRIVER = "com.mysql.jdbc.Driver" ;
	
//	//7692337
//	public static final String DBURL = "jdbc:mysql://sqld.duapp.com:4050/tsLPMkudexbPhHLVkNOZ?useUnicode=true&characterEncoding=GBK" ;
//	public static final String DBUSER = "rTug4YaTH5w3T79N1sZf9Eex" ;
//	public static final String DBPASS = "KEaC02qcEpoBpHPn3S2OMbNUmp5XNz5p" ;
	
	//7692336
//	public static final String DBURL = "jdbc:mysql://sqld.duapp.com:4050/HSjQrZjcTsYSyDLRRfnw?useUnicode=true&characterEncoding=GBK" ;
//	public static final String DBUSER = "vrniA0jsYIohojh6v2d5GuDl" ;
//	public static final String DBPASS = "NIX7kRvZ246d7PXcgC2K5CtBgGMcVGdZ" ;
	
	//localhost
	public static final String DBURL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=GBK" ;
	public static final String DBUSER = "root" ;
	public static final String DBPASS = "" ;
	private Connection conn = null ;
	public DataBaseConnection(){
		try{
			Class.forName(DBDRIVER) ;
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	public Connection getConnection(){
		return this.conn ;
	}
	public void close(){
		if(this.conn!=null){
			try{
				this.conn.close() ;
			}catch(Exception e){}
		}
	}
};
