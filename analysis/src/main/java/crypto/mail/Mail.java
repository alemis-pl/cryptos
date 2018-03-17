package crypto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {

    private String receiverEmail;
    private String subject;
    private String message;
}
