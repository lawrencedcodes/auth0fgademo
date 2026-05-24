package com.lawrencedcodes.auth0fgademo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/authz")
public class FgaController {

    private final AuthorizationService authorizationService;

    public FgaController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/check")
    public CompletableFuture<Boolean> checkPermission(
            @RequestParam String user,
            @RequestParam String relation,
            @RequestParam String object) {
        return authorizationService.check(user, relation, object);
    }
}
