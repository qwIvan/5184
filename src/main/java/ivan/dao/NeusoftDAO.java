package ivan.dao;

import ivan.vo.NeusoftVO;

public interface NeusoftDAO{
	public boolean addLuqu(NeusoftVO luqu) throws Exception;
	public NeusoftVO getByIdnum(String idnum) throws Exception;
}