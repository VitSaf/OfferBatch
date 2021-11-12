@isTest
public class TestDataFactory {
    private TestDataFactory() {}

    public static User createAdminUser() {
        Profile prof = [SELECT Id, Name FROM Profile WHERE Name = 'System Administrator' LIMIT 1];
        User user = new User(Username = 'test3453User345@testtt.com', IsActive = true, ProfileId = prof.Id, Alias = 'testU',
            email = 'test123@mail.com', LastName = 'test', TimeZoneSidKey = 'GMT', LocaleSidKey = 'en_US',
            EmailEncodingKey = 'UTF-8', LanguageLocaleKey = 'en_US');
        insert user;
        return user;
    }
}
