package com.example.RGT.Service.Module;

import com.example.RGT.Entity.SiteUser;
import com.example.RGT.Repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;

    public SiteUser save(SiteUser siteUser) {
        siteUserRepository.save(siteUser);
        return siteUser;
    }

    public SiteUser getUser(String username) {
        return siteUserRepository.findByUsername(username);
    }
}
