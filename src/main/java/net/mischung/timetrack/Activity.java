package net.mischung.timetrack;

import java.util.Date;

public abstract class Activity {

    protected final String name;
    protected String category;
    protected String description;

    protected Activity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract Date getStartTime();
    public abstract Date getEndTime();
    public abstract long getDuration();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (category != null ? !category.equals(activity.getCategory()) : activity.getCategory() != null) return false;
        if (description != null ? !description.equals(activity.getDescription()) : activity.getDescription() != null)
            return false;
        if (!name.equals(activity.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
