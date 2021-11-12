/**
 * Universal Containers would like to run a cleansing routine on regular basis (weekly)
 *  that updates the status of expired Offers.
If the Status field has the value of Sent after the expiration date,
the Status needs to be changed to Expired.
Implement a batch job which realizes this requirement.
//https://login.salesforce.com/packaging/installPackage.apexp?p0=04t5g000000MFPz&isdtp=p1
 */ 
public without sharing class OffersBatch implements Database.Batchable<sObject> {

  public Database.QueryLocator start(Database.BatchableContext bc) {
    String query = 'SELECT Id, CreatedById, Expiration_date__c, Status__c FROM Offer__c WHERE Status__c = \'Waiting for answer\''; 
    return Database.getQueryLocator(query);
  }

  public void execute(Database.BatchableContext bc, List<sObject> offers) {
    
    Offer__c[] expiredOffers = OffersBatchUtils.findExpiredOffers((List<Offer__c>)offers);
    if (expiredOffers.size() > 0) {
      update expiredOffers;
    }
  }

  public void finish(Database.BatchableContext bc) {}
}
