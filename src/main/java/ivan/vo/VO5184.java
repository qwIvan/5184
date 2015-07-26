package ivan.vo;

public class VO5184 {
	private int id;
	private String name;
	private String zkzh;
	private String csny;
	private String xm;
	private String lbm;
	private String pcm;
	private String zymc;
	private String yxdm;
	public boolean equals(Object arg0) {
		VO5184 vo = (VO5184) arg0;
		return this.getZkzh().equals(vo.getZkzh());
	}
	public int hashCode() {
		return (int) (Long.valueOf(zkzh)%Integer.MAX_VALUE);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZkzh() {
		return zkzh;
	}
	public void setZkzh(String zkzh) {
		this.zkzh = zkzh;
	}
	public String getCsny() {
		return csny;
	}
	public void setCsny(String csny) {
		this.csny = csny;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getLbm() {
		return lbm;
	}
	public void setLbm(String lbm) {
		this.lbm = lbm;
	}
	public String getPcm() {
		return pcm;
	}
	public void setPcm(String pcm) {
		this.pcm = pcm;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
	}
	public String getYxdm() {
		return yxdm;
	}
	public void setYxdm(String yxdm) {
		this.yxdm = yxdm;
	}
}
