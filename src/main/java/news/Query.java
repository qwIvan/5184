package news;

import java.util.List;

/**
 * Created by imivan on 15-7-26.
 */
public class Query {
    Student own;
    List<Student> classmates;
    int preOffset;
    int nextOffset;
    boolean preValid;
    boolean nextValid;
    boolean direction;
    boolean isOwnComplete;
    int classmateCount;
}
