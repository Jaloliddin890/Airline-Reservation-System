package tmrv.dev.airlinereservationsystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.Flight;
import tmrv.dev.airlinereservationsystem.domains.Ticket;
import tmrv.dev.airlinereservationsystem.domains.User;
import tmrv.dev.airlinereservationsystem.repository.TicketRepository;


import java.util.List;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;
    private final TicketRepository ticketRepository;

    public NotificationService(JavaMailSender mailSender, TicketRepository ticketRepository) {
        this.mailSender = mailSender;
        this.ticketRepository = ticketRepository;
    }

    public void notifyCustomersAboutChanges(Flight flight) {
        List<Ticket> tickets = ticketRepository.findByFlight(flight);

        for (Ticket ticket : tickets) {
            User customer = ticket.getCustomer();
            String subject = "Important Update to Your Flight";
            String message = "Dear " + customer.getName() + ",\n\n"
                    + "There has been an update to your flight. Please check the details.\n\n"
                    + "Flight " + flight.getId() + "\n"
                    + "New Departure Time: " + flight.getDepartureTime() + "\n"
                    + "New Arrival Time: " + flight.getArrivalTime() + "\n\n"
                    + "Thank you.";

            try {
                sendEmail(customer.getEmail(), subject, message);
            } catch (MessagingException e) {
                // Handle exception - possibly log the error
                e.printStackTrace();
            }
        }
    }

    private void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        mailSender.send(mimeMessage);
    }
}
