public with sharing class OffersService {
    private OffersService() {}
    private static final Integer CLEASING_INTERVAL_DAYS = 7;
    @testVisible
    private static final String CRON_EXP = '0 0 0 ? * MON';

    public static Id startBatch() {
        return Database.executeBatch(new OffersBatch());
    }

    public static Id scheduleBatch() {
        return System.schedule('Cleasing offers', CRON_EXP, new OffersBatchScheduler());
    }
}
