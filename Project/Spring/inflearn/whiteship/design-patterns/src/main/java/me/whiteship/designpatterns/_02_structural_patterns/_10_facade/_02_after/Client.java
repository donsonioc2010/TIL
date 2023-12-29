package me.whiteship.designpatterns._02_structural_patterns._10_facade._02_after;

public class Client {

    public static void main(String[] args) {
        EmailSettings emailSettings = new EmailSettings();
        emailSettings.setHost("127.0.0.1");

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFrom("keesun");
        emailMessage.setTo("whiteship");
        emailMessage.setCc("일남");
        emailMessage.setSubject("오징어게임");
        emailMessage.setText("밖은 더 지옥이더라고..");

        EmailSender emailSender = new EmailSender(emailSettings);
        emailSender.sendEmail(emailMessage);
    }
}
