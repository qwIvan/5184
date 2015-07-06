package ivan.factory;

import ivan.dao.LuquDAO;
import ivan.proxy.LuquProxy;


public class Factory{
	public static LuquDAO getInstance(){
		return new LuquProxy() ;
	}
}

