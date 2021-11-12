public with sharing class OffersBatchUtils {
    private OffersBatchUtils() {}

    /**
     * Changes status of expired offers
     * offers - list of offers from Batch.start method with incorrect statuses
     * return - list of expired offers with correct status
     */
    public static Offer__c[] changeExpiredOffersStatus(List<Offer__c> offers) {
        Offer__c[] expiredOffers = new List <Offer__c>();
        for (Offer__c offer : offers) {
            offer.Status__c = 'Expired';
            expiredOffers.add(offer);
        }
        return expiredOffers;
    }
}
