package net.mischung.timetrack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyActivity extends Activity {

    private final List<Activity> activities;

    public DailyActivity(Activity activity) {
        super(activity.getName());

        this.activities = new ArrayList<Activity>();
        activities.add(activity);

        this.category = activity.getCategory();
        this.description = activity.getDescription();
    }

    public DailyActivity(Activity activity, List<? extends Activity> tail) {
        this(activity);
        activities.addAll(tail);
    }

    public boolean addActivity(SingleActivity activity) {
        return activities.add(activity);
    }

    @Override
    public Date getStartTime() {
        return firstActivity().getStartTime();
    }

    @Override
    public Date getEndTime() {
        return lastActivity().getEndTime();
    }

    @Override
    public long getDuration() {
        long duration = 0;
        for (Activity activity : activities) {
            duration = duration + activity.getDuration();
        }
        return duration;
    }

    public Activity firstActivity() {
        return activities.get(0);
    }

    public Activity lastActivity() {
        return activities.get(activities.size() - 1);
    }

}
