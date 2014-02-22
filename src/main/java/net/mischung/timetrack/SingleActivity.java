package net.mischung.timetrack;

import java.util.Date;

public class SingleActivity extends Activity {

    private final Date startTime;
    private final Date endTime;

    public SingleActivity(String name, Date startTime, Date endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public long getDuration() {
        return getEndTime().getTime() - getStartTime().getTime();
    }

}
