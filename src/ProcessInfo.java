/**
 * Represents a process info tracking object.
 */
public class ProcessInfo implements Comparable<ProcessInfo> {
    private String processName;
    private int processId;
    private int processPriority;
    private int processRemainingRuntime;
    private long processStartTime;
    private long processEndTime;
    private long processElapsedTime;

    /**
     * Constructs a new process info tracker.
     *
     * @param processName the name of the process being tracked.
     * @param processId the id of the process being tracked.
     * @param processPriority the priority of the process being tracked.
     * @param processRemainingRuntime the remaining runtime of the process being tracked.
     */
    public ProcessInfo(String processName, int processId, int processPriority, int processRemainingRuntime) {
        this.processStartTime = System.currentTimeMillis();
        this.processName = processName;
        this.processId = processId;
        this.processPriority = processPriority;
        this.processRemainingRuntime = processRemainingRuntime;
    }


    /**
     * Used to compare processes based on priority. If a process has the same priority, the comparison differentiates
     * between the processes by id.
     *
     * @param o the object to be compared.
     * @return a value less than 0 if priority of x < priority of y; and a value greater than 0 if priority of x > priority of y.
     * If priority of x == priority of y, returns 0 if processId of x == processId of y, a value less than 0 if processId
     * of x < processId of y and greater than 0 if processId of x > processId of y.
     */
    @Override
    public int compareTo(ProcessInfo o) {
        if(o.processPriority == this.processPriority)
            return Integer.compare(this.processId, o.processId);
        else
            return Integer.compare(this.processPriority, o.processPriority);
    }

    // Getters and Setters
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getProcessPriority() {
        return processPriority;
    }

    public void setProcessPriority(int processPriority) {
        this.processPriority = processPriority;
    }

    public int getProcessRemainingRuntime() {
        return processRemainingRuntime;
    }

    public void setProcessRemainingRuntime(int processRemainingRuntime) {
        this.processRemainingRuntime = processRemainingRuntime;
    }

    public long getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(long processStartTime) {
        this.processStartTime = processStartTime;
    }

    public long getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(long processEndTime) {
        this.processEndTime = processEndTime;
    }

    public long getProcessElapsedTime() {
        return processElapsedTime;
    }

    public void setProcessElapsedTime(long processElapsedTime) {
        this.processElapsedTime = processElapsedTime;
    }

    /**
     * Executes a process for the timeframe specified in executionTime.
     *
     * @param executionTime the timeframe alloted to the execution of this process.
     * @return true if the process has finished execution, otherwise false.
     */
    public boolean executeProcess(int executionTime) {
        int timeToExecute = Math.min(executionTime, this.processRemainingRuntime);

        try {
            if (timeToExecute > 0) {
                Thread.sleep(timeToExecute);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        this.processRemainingRuntime -= timeToExecute;
        this.processEndTime = System.currentTimeMillis();

        if (this.processRemainingRuntime <= 0) {
            endProcess();
            return true;
        }
        return false;
    }


    /**
     * Gets process completion statistics.
     *
     * @return the string containing the process completion statistics.
     */
    public String displayCompletedInfo() {
        return String.format("Process Name: %-10s Process Priority: %-5d Completion Time: %-5s",
                processName,
                processPriority,
                processElapsedTime);
    }

    /**
     * Overrides the default toString() implementation to get process details.
     *
     * @return a String containing the process details.
     */
    @Override
    public String toString() {
        return String.format("Process Name: %-10s Process Id: %-5d Process Priority: %-5d Process Remaining Runtime: %-5d",
                processName,
                processId,
                processPriority,
                processRemainingRuntime);
    }

    /**
     * Sets the end time and calculates and sets the elapsed time.
     */
    private void endProcess() {
        // Set the end time and calculate elapsed time
        this.processEndTime = System.currentTimeMillis();
        this.processElapsedTime = processEndTime - processStartTime;
    }

}
