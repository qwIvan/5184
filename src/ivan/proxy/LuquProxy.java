package ivan.proxy;


import ivan.dao.LuquDAO;
import ivan.db.DataBaseConnection;
import ivan.vo.VO5184;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.Set;


public class LuquProxy implements LuquDAO {
	private DataBaseConnection dbc = null ;
	private LuquImpl dao = null ;
	public LuquProxy(){
		this.dbc = new DataBaseConnection() ;	// �ڴ�������������ݿ����Ӷ����ʵ����
		this.dao = new LuquImpl(this.dbc.getConnection()) ;
	}
	public boolean addVO(VO5184 vo) throws Exception {
		boolean flag = false ;
		try{
			flag = this.dao.addVO(vo) ;
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return flag;
	}
	public int getUserCount(String name) throws Exception {
		int count = 0;
		try{
			count = this.dao.getUserCount(name) ;
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return count;
	}
	public Set<VO5184> getFullNear(String zkzh) throws Exception {
		Set<VO5184> set = null;
//		List<VO5184> list = null;
		try{
			set = this.dao.getFullNear(zkzh) ;
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return set;
	}
}
class LuquImpl implements LuquDAO {
	private Connection conn = null; // ���Ҫ��������ݿ������϶���Ҫ���ݿ����Ӷ���

	public LuquImpl(Connection conn) {
		this.conn = conn; // ���ⲿʵ����ʱ��������
	}
	

	public boolean addVO(VO5184 vo) throws Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO t5184(name,zkzh,csny,xm,lbm,pcm,zymc,yxdm) VALUES (?,?,?,?,?,?,?,?)";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getZkzh());
			pstmt.setString(3, vo.getCsny());
			pstmt.setString(4, vo.getXm());
			pstmt.setString(5, vo.getLbm());
			pstmt.setString(6, vo.getPcm());
			pstmt.setString(7, vo.getZymc());
			pstmt.setString(8, vo.getYxdm());
			int count = pstmt.executeUpdate(); // ִ�и��£����ظ��µļ�¼��
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



	public int getUserCount(String name) throws Exception {
		int count = 0;
		PreparedStatement pstmt = null;
		String sql = "SELECT COUNT(*) FROM t5184  WHERE name=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // ��ʾ�Ѿ����ҵ���
				count = rs.getInt(1);
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
		return count;
	}


	public Set<VO5184> getFullNear(String zkzh) throws Exception {
		Set<VO5184> set = new LinkedHashSet<VO5184>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM t5184 WHERE zkzh BETWEEN ?-25 AND ?+50";// GROUP BY zkzh";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, zkzh);
			pstmt.setString(2, zkzh);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) { // ��ʾ�Ѿ����ҵ���
				VO5184 vo = new VO5184();
				vo.setId(rs.getInt("id"));
				vo.setName(rs.getString("name"));
				vo.setZkzh(rs.getString("zkzh"));
				vo.setCsny(rs.getString("csny"));
				vo.setXm(rs.getString("xm"));
				vo.setLbm(rs.getString("lbm"));
				vo.setPcm(rs.getString("pcm"));
				vo.setZymc(rs.getString("zymc"));
				vo.setYxdm(rs.getString("yxdm"));
				set.add(vo);
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
		return set;
	}
}