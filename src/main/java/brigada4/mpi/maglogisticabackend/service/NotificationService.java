package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SmtpMailSender smtpMailSender;

    /**
     * Отправка уведомления о том, что заявка успешно обработана и создан Response в БД
     * @param userTo
     * @param userFrom
     */
    public void sendMailAboutFinishingApplication(User userTo, User userFrom) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(userTo.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Ваша заявка была успешно обработана сотрудником: %s, и она перешла в статус FINISHED. \n" +
                            "Теперь вы можете продолжать работу над вашей задачей!",
                    userTo.getName() + " " + userTo.getSurname(),
                    userFrom.getName() + " " + userFrom.getSurname()
            );

            smtpMailSender.send(userTo.getEmail(), "Application notification", message);
        }
    }

    /**
     * Отправка уведомления о том, что заявка была принята в работу одним из сотрудников
     * @param userTo
     * @param userFrom
     */
    public void sendMailAboutWorkingApplication(User userTo, User userFrom) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(userTo.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Ваша заявка была взята в работу сотрудником: %s, и она перешла в статус WORKED. \n" +
                            "В скором времени вы получите результат!",
                    userTo.getName() + " " + userTo.getSurname(),
                    userFrom.getName() + " " + userFrom.getSurname()
            );

            smtpMailSender.send(userTo.getEmail(), "Application notification", message);
        }
    }


}
