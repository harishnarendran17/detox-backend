package com.detox.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detox.model.FocusSession;
import com.detox.model.User;
import com.detox.repository.FocusSessionRepository;
import com.detox.repository.UserRepository;
@RestController
@RequestMapping("/api/focus")
public class Focuscontroller {
	
	@Autowired
    private FocusSessionRepository focusRepo;

    @Autowired
    private UserRepository userRepo;
	
    @PostMapping("/start")
    public FocusSession start(@RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow();

        FocusSession session = new FocusSession();
        session.setUser(user);
        session.setStartTime(LocalDateTime.now());
        session.setStatus("ACTIVE");
        return focusRepo.save(session);
    }

    @PostMapping("/break")
    public FocusSession breakFocus(@RequestParam Long sessionId) {
        FocusSession session = focusRepo.findById(sessionId).orElseThrow();
        session.setStatus("FAILED");
        session.setEndTime(LocalDateTime.now());
        session.setPenaltyPaid(false);
        return focusRepo.save(session);
    }

    @PostMapping("/complete")
    public FocusSession complete(@RequestParam Long sessionId) {
        FocusSession session = focusRepo.findById(sessionId).orElseThrow();
        session.setStatus("COMPLETED");
        session.setEndTime(LocalDateTime.now());

        User user = session.getUser();
        user.setPoints(user.getPoints() + 10);
        userRepo.save(user);

        return focusRepo.save(session);
    }
}
