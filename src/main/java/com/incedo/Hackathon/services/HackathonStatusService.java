package com.incedo.Hackathon.services;

import com.incedo.Hackathon.models.HackathonStatus;
import com.incedo.Hackathon.repository.HackathonStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HackathonStatusService {
    @Autowired
    HackathonStatusRepository hackathonStatusRepository;

    @Autowired
    public HackathonStatusService(HackathonStatusRepository hackathonStatusRepository) {
        this.hackathonStatusRepository = hackathonStatusRepository;
    }

    public List<HackathonStatus> getAllStatuses() {
        return hackathonStatusRepository.findAll();
    }

    public HackathonStatus getStatusById(Long id) {
        Optional<HackathonStatus> optionalStatus = hackathonStatusRepository.findById(id);
        return optionalStatus.orElse(null);
    }

    public HackathonStatus createStatus(HackathonStatus status) {
        return hackathonStatusRepository.save(status);
    }

    public HackathonStatus updateStatus(Long id, HackathonStatus newStatus) {
        Optional<HackathonStatus> optionalStatus = hackathonStatusRepository.findById(id);
        if (optionalStatus.isPresent()) {
            HackathonStatus existingStatus = optionalStatus.get();
            existingStatus.setStatusName(newStatus.getStatusName());
            existingStatus.setStartTimeStamp(newStatus.getStartTimeStamp());
            existingStatus.setEndTimeStamp(newStatus.getEndTimeStamp());
            return hackathonStatusRepository.save(existingStatus);
        } else {
            return null; // Status with the given ID not found
        }
    }

    public void deleteStatus(Long id) {
        hackathonStatusRepository.deleteById(id);
    }
}
