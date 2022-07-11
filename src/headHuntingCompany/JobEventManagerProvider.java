package headHuntingCompany;

public class JobEventManagerProvider {
    private static JobEventManager JOB_EVENT_MANAGER = null;

    private JobEventManagerProvider() {
    }

    public static JobEventManager  getInstance(){
        if(JOB_EVENT_MANAGER == null)
            JOB_EVENT_MANAGER = new JobEventManagerImpl();

        return JOB_EVENT_MANAGER;
    }
}
