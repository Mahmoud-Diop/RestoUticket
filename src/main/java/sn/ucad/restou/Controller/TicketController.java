package sn.ucad.restou.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sn.ucad.restou.Entity.Ticket;
import sn.ucad.restou.Service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public Iterable<Ticket> getAllTickets() {
        return ticketService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/etudiant/{etudiantId}")
    public Iterable<Ticket> getTicketsByStudentId(@PathVariable Long etudiantId) {
        return ticketService.getTicketsByStudentId(etudiantId);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.create(ticket));
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<?> validate(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.validate(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

      
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getUsedAndAvailableTickets() {
        return ResponseEntity.ok(ticketService.stat());
    }
}
