package com.incedo.Hackathon.services;

import com.incedo.Hackathon.models.Domain;
import com.incedo.Hackathon.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainService {

    private final DomainRepository domainRepository;

    @Autowired
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    public Domain getDomainById(Long id) {
        Optional<Domain> optionalDomain = domainRepository.findById(id);
        return optionalDomain.orElse(null);
    }

    public Domain createDomain(Domain domain) {
        return domainRepository.save(domain);
    }

    public Domain updateDomain(Long id, Domain updatedDomain) {
        Optional<Domain> optionalDomain = domainRepository.findById(id);
        if (optionalDomain.isPresent()) {
            Domain existingDomain = optionalDomain.get();
            existingDomain.setDomainName(updatedDomain.getDomainName());
            return domainRepository.save(existingDomain);
        } else {
            return null;
        }
    }

    public boolean deleteDomain(Long id) {
        Optional<Domain> optionalDomain = domainRepository.findById(id);
        if (optionalDomain.isPresent()) {
            domainRepository.delete(optionalDomain.get());
            return true;
        } else {
            return false;
        }
    }
}
