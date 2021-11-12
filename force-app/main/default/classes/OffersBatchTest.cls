@isTest
private class OffersBatchTest {
    @TestSetup
    private static void makeData(){
        Integer recordsNum = 6;
        Date expDate = Date.today().addDays(-(Integer)(recordsNum/2));
        Contact[] contacts = new List <Contact>();
        for(Integer i = 0; i < recordsNum; i++) {
            contacts.add(new Contact(LastName = 'asd' + i));
        }
        insert contacts;
        Offer__c[] offers = new List <Offer__c>();
        for (Integer i = 1; i < recordsNum; i++) {
            offers.add(new Offer__c(Addressee__c = contacts[i].Id, Expiration_date__c = expDate, Salary__c = 500*i,
            Status__c = 'Waiting for answer'));
            expDate = expDate.addDays(1);
        }
        insert offers;
    }

    @isTest
    private static void testBatchStart() {
        User user = TestDataFactory.createAdminUser();
        System.runAs(user) {
            Integer countBeforeBatch = [SELECT Id, Status__c FROM Offer__c WHERE Status__c = 'Expired'].size();
            Test.startTest();
            OffersService.startBatch();
            Test.stopTest();
            Integer countAfterBatch = [SELECT Id, Status__c FROM Offer__c WHERE Status__c = 'Expired'].size();
            System.assertNotEquals(countBeforeBatch, countAfterBatch);
        }
    }

    @isTest
    private static void testScheduler() {
        User user = TestDataFactory.createAdminUser();
        System.runAs(user) {
            Test.startTest();
            Id jobId = OffersService.scheduleBatch();
            Test.stopTest();
            CronTrigger cronInfo = [SELECT Id, CronExpression, TimesTriggered, NextFireTime 
                    FROM CronTrigger WHERE Id = :jobId];
            System.assertEquals(OffersService.CRON_EXP, cronInfo.CronExpression);
        }
    }
}
