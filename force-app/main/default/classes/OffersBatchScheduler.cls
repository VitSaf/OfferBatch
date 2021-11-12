global class OffersBatchScheduler implements Schedulable {
    global void execute(SchedulableContext sc) {
        OffersService.startBatch();
    }
}
