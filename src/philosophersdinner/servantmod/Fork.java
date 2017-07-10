package philosophersdinner.servantmod;

public class Fork {
    private volatile boolean busy = false;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
