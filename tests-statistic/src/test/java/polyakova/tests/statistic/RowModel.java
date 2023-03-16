package polyakova.tests.statistic;

/**
 * Data for report row
 *
 * @author Iuliia Poliakova
 */
public class RowModel {
    private String stepName;
    private int countPassed;
    private long durationPassed;
    private int countError;
    private long durationError;

    public RowModel(String stepName, int countPassed, long durationPassed, int countError, long durationError) {
        this.stepName = stepName;
        this.countPassed = countPassed;
        this.durationPassed = durationPassed;
        this.countError = countError;
        this.durationError = durationError;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public int getCountPassed() {
        return countPassed;
    }

    public void setCountPassed(int countPassed) {
        this.countPassed = countPassed;
    }

    public long getDurationPassed() {
        return durationPassed;
    }

    public void setDurationPassed(long durationPassed) {
        this.durationPassed = durationPassed;
    }

    public int getCountError() {
        return countError;
    }

    public void setCountError(int countError) {
        this.countError = countError;
    }

    public long getDurationError() {
        return durationError;
    }

    public void setDurationError(long durationError) {
        this.durationError = durationError;
    }
}
