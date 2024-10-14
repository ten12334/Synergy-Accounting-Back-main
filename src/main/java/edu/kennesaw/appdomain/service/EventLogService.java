public class EventLogService{
    private EventLogRepository eventLogRepository;

    public void logEvent(String eventType, Account before, Account after, String userID){
        EventLog log = new EventLog();
        log.setEventType(eventType);
        log.setBeforeImage(before != null ? before.toString() : null);
        log.setAfterImage(after != null ? after.toString() : null)
        log.setTimestamp(LocalDateTime.now());
        log.setUserId(userID);
        eventLogRepository.save(log);
    }
}