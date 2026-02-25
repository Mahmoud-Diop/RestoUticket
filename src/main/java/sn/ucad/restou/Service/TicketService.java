package sn.ucad.restou.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sn.ucad.restou.Entity.Ticket;
import sn.ucad.restou.Repository.TicketRepository;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // methode to create a ticket
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // geting all tickets
    public Iterable<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    // getting a ticket by id
    public Optional<Ticket> getById(Long id) {
        return ticketRepository.findById(id);
    }

    // getting a ticket by status(used|unused)
    public Iterable<Ticket> getTicketsStatus(Boolean used) {
        return ticketRepository.findByUsed(used);
    }

    // getting tickets by studentId

    public Iterable<Ticket> getTicketsByStudentId(Long studentId) {
        return ticketRepository.findByStudentId(studentId);
    }

    // method to validate a ticket
    public Ticket validate(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id : " + id));
        if (ticket.getUsed()) {
            throw new RuntimeException("Ticket already used");
        }

        LocalDate today = LocalDate.now();
        // expired Ticket`
        if (ticket.getValidityDate().isBefore(today)) {
            throw new RuntimeException("Ticket has expired");

        }
        if (ticket.getValidityDate().isBefore(ticket.getPurchaseDate().toLocalDate())) {
            throw new RuntimeException("Validity date must be after purchase date");
        }

        ticket.setUsed(true);
        return ticketRepository.save(ticket);
    }

    // method to delete a ticket
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    public long countAllTickets() {
        return ticketRepository.count();
    }

    public long countUsedTickets() {
        Iterable<Ticket> tickets = ticketRepository.findByUsed(true);
        long count = 0;
        for (Ticket ticket : tickets) {
            count++;
        }
        return count;
    }

    public long countAvailableTickets() {
        Iterable<Ticket> tickets = ticketRepository.findByUsed(false);
        long count = 0;
        for (Ticket ticket : tickets) {
            count++;
        }
        return count;
    }

    // Méthode stat qui regroupe tout
    public Map<String, Long> stat() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("total", countAllTickets());
        stats.put("used", countUsedTickets());
        stats.put("available", countAvailableTickets());
        return stats;

    }

}
