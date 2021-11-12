public with sharing class OffersBatchUtils {
    private OffersBatchUtils() {}

    public static Offer__c[] findExpiredOffers(List<Offer__c> offers) {
        final Date today = Date.today();
        Offer__c[] expiredOffers = new List <Offer__c>();
        for (Offer__c offer : offers) {
            if (offer.Expiration_date__c < today) {
                offer.Status__c = 'Expired';
                expiredOffers.add(offer);
            }
        }
        return expiredOffers;
    }
}
