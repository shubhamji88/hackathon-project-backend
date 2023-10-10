package com.incedo.Hackathon.controllers;

import com.incedo.Hackathon.models.HackathonStatus;
import com.incedo.Hackathon.services.HackathonStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hack")
public class HackathonStatusController {
    private final HackathonStatusService hackathonStatusService;

    @Autowired
    public HackathonStatusController(HackathonStatusService hackathonStatusService) {
        this.hackathonStatusService = hackathonStatusService;
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PARTICIPANT') or hasRole('JUDGE') or hasRole('PANELIST')")
    @GetMapping
    public List<HackathonStatus> getAllStatuses() {
        return hackathonStatusService.getAllStatuses();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public HackathonStatus createStatus(@RequestBody HackathonStatus status) {
        return hackathonStatusService.createStatus(status);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public HackathonStatus updateStatus(@PathVariable Long id, @RequestBody HackathonStatus status) {
        return hackathonStatusService.updateStatus(id, status);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable Long id) {
        hackathonStatusService.deleteStatus(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-all")
    public List<HackathonStatus> updateAllStatuses(@RequestBody List<HackathonStatus> statuses) {
        List<HackathonStatus> updatedStatuses = new ArrayList<>();
        for (HackathonStatus status : statuses) {
            HackathonStatus updatedStatus = hackathonStatusService.updateStatus(status.getId(), status);
            if (updatedStatus != null) {
                updatedStatuses.add(updatedStatus);
            }
        }
        return updatedStatuses;
    }

}
