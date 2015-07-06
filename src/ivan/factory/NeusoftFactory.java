package ivan.factory;

import ivan.dao.NeusoftDAO;
import ivan.proxy.NeusoftProxy;


public class NeusoftFactory{
	public static NeusoftDAO getInstance(){
		return new NeusoftProxy() ;
	}
}

