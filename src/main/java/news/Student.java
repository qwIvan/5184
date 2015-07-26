package news;

import javax.persistence.Id;

/**
 * Created by imivan on 15-7-26.
 */
public class Student {
    @Id
    long number;
    boolean isComplete;

    int monthOffset;
}
