public class Worker {
    String actStep = null;
    int seconds = 0;

    public void set(String step, int seconds) {
        this.actStep = step;
        this.seconds = seconds;
    }

    public String getActStep() {
        return actStep;
    }

    public boolean nextSecond() {
        seconds--;
        return seconds <= 0;
    }

    public void free() {
        actStep = null;
    }

    public boolean isFree() {
        return actStep == null;
    }

    public boolean isActive() {
        return actStep != null;
    }

    @Override
    public String toString() {
        return "Worker{" + "actStep='" + actStep + '\'' +
                ", seconds=" + seconds +
                '}';
    }
}
