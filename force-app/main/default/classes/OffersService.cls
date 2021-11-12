public with sharing class OffersService {
    private OffersService() {}
    private static final Integer CLEASING_INTERVAL_DAYS = 7;
    @testVisible
    private static final String CRON_EXP = '0 0 0 ? * MON';

    /**
     * Starting batch immediatly
     * return jobId
     */
    public static Id startBatch() {
        return Database.executeBatch(new OffersBatch());
    }
    /**
     * Scheduling batch 
     * return jobId
     */
    public static Id scheduleBatch() {
        return System.schedule('Cleasing offers', CRON_EXP, new OffersBatchScheduler());
    }
}
//SerialBatchApexRangeChunkHandler