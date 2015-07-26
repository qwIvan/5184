package ivan.proxy;


import ivan.dao.NeusoftDAO;
import ivan.db.DataBaseConnection;
import ivan.vo.NeusoftVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class NeusoftProxy implements NeusoftDAO {
	private DataBaseConnection dbc = null ;
	private NeusoftImpl dao = null ;
	public NeusoftProxy(){
		this.dbc = new DataBaseConnection() ;	// 在代理类中完成数据库连接对象的实例化
		this.dao = new NeusoftImpl(this.dbc.getConnection()) ;
	}
	public boolean addLuqu(NeusoftVO luqu) throws Exception {
		boolean flag = false ;
		try{
			flag = this.dao.addLuqu(luqu) ;		// 调用真实主题实现类
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return flag;
	}
	public NeusoftVO getByIdnum(String idnum) throws Exception {
		NeusoftVO vo = null ;
		try{
			vo = this.dao.getByIdnum(idnum) ;
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return vo;
	}
}
class NeusoftImpl implements NeusoftDAO {
	private Connection conn = null; // 如果要想完成数据库操作则肯定需要数据库连接对象

	public NeusoftImpl(Connection conn) {
		this.conn = conn; // 由外部实例化时传递连接
	}
	
	public boolean addLuqu(NeusoftVO luqu) throws Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO luqu(name,idnum,sex,cnum,major) VALUES (?,?,?,?,?)";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, luqu.getName());
			pstmt.setString(2, luqu.getIdnum());
			pstmt.setString(3, luqu.getSex());
			pstmt.setString(4, luqu.getCnum());
			pstmt.setString(5, luqu.getMajor());
			int count = pstmt.executeUpdate(); // 执行更新，返回更新的记录数
			if (count > 0) {
				flag = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}
	public NeusoftVO getByIdnum(String idnum) throws Exception {
		NeusoftVO vo = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM luqu WHERE idnum=? LIMIT 1";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, idnum);
			ResultSet rs = pstmt.executeQuery(); // 执行更新，返回更新的记录数
			if (rs.next()) {
				vo = new NeusoftVO();
				vo.setName(rs.getString("name"));
				vo.setIdnum(rs.getString("idnum"));
				vo.setSex(rs.getString("sex"));
				vo.setCnum(rs.getString("cnum"));
				vo.setMajor(rs.getString("major"));
			}
			rs.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
		return vo;
	}
}