public class Time2Distance {
    private long raceDuration;
    private long recordDistance;

    public Time2Distance(long raceDuration) {
        this.raceDuration = raceDuration;
    }

    public long getRaceDuration() {
        return raceDuration;
    }

    public void setRaceDuration(long raceDuration) {
        this.raceDuration = raceDuration;
    }

    public long getRecordDistance() {
        return recordDistance;
    }

    public void setRecordDistance(long recordDistance) {
        this.recordDistance = recordDistance;
    }

    @Override
    public String toString() {
        return "Time2Distance{" +
                "raceDuration=" + raceDuration +
                ", recordDistance=" + recordDistance +
                '}';
    }
}
