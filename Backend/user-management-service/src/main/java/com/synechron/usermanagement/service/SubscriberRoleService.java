package com.synechron.usermanagement.service;

import com.synechron.usermanagement.model.SubscriberRole;
import com.synechron.usermanagement.repository.SubscriberRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberRoleService {
    @Autowired
    private SubscriberRoleRepository subscriberRoleRepository;

    public SubscriberRole getById(Long id) {
        return subscriberRoleRepository.getReferenceById(id);
    }

    public List<SubscriberRole> getAll() {
        return subscriberRoleRepository.findAll();
    }
}
