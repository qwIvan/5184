package ivan.dao;

import java.util.Set;

import ivan.vo.VO5184;

public interface LuquDAO{
	public boolean addVO(VO5184 vo) throws Exception;
	public int getUserCount(String name) throws Exception;
	public Set<VO5184> getFullNear(String zkzh) throws Exception;
}