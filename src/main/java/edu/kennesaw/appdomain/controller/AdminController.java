package edu.kennesaw.appdomain.controller;


import edu.kennesaw.appdomain.dto.AdminEmailObject;
import edu.kennesaw.appdomain.dto.MessageResponse;
import edu.kennesaw.appdomain.dto.UserDTO;
import edu.kennesaw.appdomain.entity.User;
import edu.kennesaw.appdomain.exception.UserAttributeMissingException;
import edu.kennesaw.appdomain.repository.UserRepository;
import edu.kennesaw.appdomain.service.AdminService;
import edu.kennesaw.appdomain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://synergyaccounting.app", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) throws UserAttributeMissingException, IOException, InterruptedException {
        return adminService.createUserWithRole(user);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/updateuser")
    public ResponseEntity<?> updateuser(@RequestBody UserDTO user) throws IOException, InterruptedException {
        if (user.getUserid().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserID must be valid.");
        }
        return adminService.updateUser(user);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam boolean status){
        return adminService.setActiveStatus(id, status);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/usersearch")
    public ResponseEntity<?> findUser(@RequestBody UserDTO user) {
        User foundById = null;
        User foundByEmail = null;
        User foundByUsername = null;
        if (user.getUserid().isPresent()) {
            foundById = userRepository.findByUserid(user.getUserid().get());
        }
        if (user.getEmail().isPresent()) {
            foundByEmail = userRepository.findByEmail(user.getEmail().get());
        }
        if (user.getUsername().isPresent()) {
            foundByUsername = userRepository.findByUsername(user.getUsername().get());
        }
        if (foundById == null && foundByEmail == null && foundByUsername == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("That user does not exist."));
        }
        if ((foundById != null && foundByEmail != null && !foundById.equals(foundByEmail)) ||
                (foundById != null && foundByUsername != null && !foundById.equals(foundByUsername)) ||
                (foundByEmail != null && foundByUsername != null && !foundByEmail.equals(foundByUsername))) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Multiple fields correspond to different users."));
        }
        User foundUser = foundById != null ? foundById :
                foundByEmail != null ? foundByEmail :
                        foundByUsername;
        return ResponseEntity.ok(foundUser);
    }


    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/send-email")
    public ResponseEntity<?> sendAdminEmail(@RequestBody AdminEmailObject aem) {
        emailService.sendAdminEmail(aem.getTo(), aem.getFrom(), aem.getSubject(), aem.getBody());
        return ResponseEntity.ok().body(new MessageResponse("Email sent.")) ;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/emails/{username}")
    public ResponseEntity<?> getMail(@PathVariable("username") String username) {
        List<AdminEmailObject> emails = emailService.getUserEmails(username);
        if (emails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No emails found."));
        }
        return ResponseEntity.ok(emails);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/emails/delete")
    public ResponseEntity<?> deleteMail(@RequestBody AdminEmailObject[] emails) {
        if (emails.length == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Selection is Empty!");
        if (emailService.deleteEmails(emails)) return ResponseEntity.ok(new MessageResponse("Email deleted successfully!"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Email could not be deleted!"));
    }

}
