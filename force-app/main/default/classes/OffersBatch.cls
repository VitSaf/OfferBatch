/**
 * Universal Containers would like to run a cleansing routine on regular basis (weekly)
 *    that updates the status of expired Offers.
If the Status field has the value of Sent after the expiration date,
the Status needs to be changed to Expired.
Implement a batch job which realizes this requirement.
https://login.salesforce.com/packaging/installPackage.apexp?p0=04t5g000000MFPz&isdtp=p1
 */
public without sharing class OffersBatch implements Database.Batchable<sObject>, Database.Stateful {

    public List<String> errors;

    public OffersBatch() {
        this.errors  = new List<String>();
    }

    public Database.QueryLocator start(Database.BatchableContext bc) {
        String query = 'SELECT Id, Expiration_date__c, Status__c FROM Offer__c ' +
            'WHERE Status__c = \'Sent\' AND Expiration_date__c < TODAY';
        return Database.getQueryLocator(query);
    }

    /**
     * changes status of expired offers from 'Sent' to 'Expired'
     */
    public void execute(Database.BatchableContext bc, List<sObject> offers) {
        Offer__c[] expiredOffers = OffersBatchUtils.changeExpiredOffersStatus((List<Offer__c>) offers);
        if (expiredOffers.size() > 0) {
            try {
                update expiredOffers;
            } catch (DMLException dmle) {
                this.errors.add(dmle.getMessage());
            }  
        }
    }

    /**
     * sends error report to a person who started batch by email
     */
    public void finish(Database.BatchableContext bc) {
        AsyncApexJob a = [SELECT Id, Status, CreatedBy.Email FROM AsyncApexJob WHERE Id =:BC.getJobId()];
        Messaging.SingleEmailMessage mail = new Messaging.SingleEmailMessage();
        String[] toAddresses = new String[] {a.CreatedBy.Email};
        mail.setToAddresses(toAddresses);
        if (this.errors.size() > 0) {
            mail.setSubject('Errors during weekly offers cleasing ');
            mail.setPlainTextBody('DMLException: ' + this.errors.toString());
            
        } else {
            mail.setSubject('Weekly offers cleasing completed succesfull');
            mail.setPlainTextBody('No errors found');
        }
        Messaging.sendEmail(new Messaging.SingleEmailMessage[] { mail });
    }
}
