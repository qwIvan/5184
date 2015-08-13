package news;

import javax.persistence.Id;

/**
 * Created by imivan on 15-7-26.
 */
public class Student {
    boolean isComplete;
    int monthOffset;
    @Id
    long zkzh;//准考证号1721505040
    String month;
    String xm;//姓名
    String yxdm;//院校代码
    String zymc;////院校名称
    String lbm;//类别名
    String pcm;//批次名
}
