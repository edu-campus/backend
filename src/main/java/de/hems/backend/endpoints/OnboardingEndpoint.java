package de.hems.backend.endpoints;

import de.hems.backend.components.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingEndpoint {

    private final ConfigurationManager configurationManager;

    @Autowired
    public OnboardingEndpoint(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @PostMapping(value = "/smtp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity smtp(@RequestParam String host)  {
        //TODO:
        return ResponseEntity.ok().build();
    }
}
