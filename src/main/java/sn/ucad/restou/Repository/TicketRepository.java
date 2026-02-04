package sn.ucad.restou.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sn.ucad.restou.Entity.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Optional<Ticket> findByTicketCode(String codeTicket);

    Iterable<Ticket> findByUsed(Boolean used);

    Iterable<Ticket> findByStudentId(Long studentId);

}
