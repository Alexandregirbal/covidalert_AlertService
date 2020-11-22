package polytech.covidalert.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Component
public class EmailSender {

    @Value("${spring.email.host}")
    private String host;
    @Value("${spring.email.port}")
    private int port;
    @Value("${spring.email.username}")
    private String username;
    @Value("${spring.email.password}")
    private String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void sendEmail(String mail) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.getHost());
        mailSender.setPort(this.getPort());
        mailSender.setUsername(this.getUsername());
        mailSender.setPassword(this.getPassword());
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("covidalertmail@gmail.com");
        mailMessage.setTo(mail);
        mailMessage.setSubject("COVIDALERT: Vous êtes cas contact !");
        mailMessage.setText("Bonjour, vous avez été en contact durant un des 7 derniers jours avec une personne testée positive au COVID19. \n" +
                        "Nous vous conseillons de vous mettre en quatorzaine et d'aller vous faire dépister au plus vite.");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        mailSender.send(mailMessage);
    }
}
