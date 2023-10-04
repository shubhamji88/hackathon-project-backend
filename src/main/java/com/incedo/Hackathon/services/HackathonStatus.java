package com.incedo.Hackathon.services;

import com.incedo.Hackathon.repository.HackathonStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HackathonStatus {
    @Autowired
    HackathonStatusRepository hackathonStatusRepository;


}
