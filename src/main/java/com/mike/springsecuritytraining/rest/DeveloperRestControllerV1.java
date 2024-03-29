package com.mike.springsecuritytraining.rest;

import com.mike.springsecuritytraining.model.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/api/v1/developer")
public class DeveloperRestControllerV1 {

    private final List<Developer> DEVELOPERS = Stream.of(
                    new Developer(1L, "Ivan", "Ivanov"),
                    new Developer(2L, "Sergey", "Sergeev"),
                    new Developer(3L, "Petr", "Petrov"))
            .collect(Collectors.toList());

    @GetMapping
    @PreAuthorize("hasAuthority('developer:read')")
    public List<Developer> getAll() {
        return DEVELOPERS;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developer:read')")
    public Developer getById(@PathVariable Long id) {
        return DEVELOPERS.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developer:write')")
    public Developer create(@RequestBody Developer developer) {
        DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developer:write')")
    public void deleteById(@PathVariable Long id) {
        DEVELOPERS.removeIf(d -> d.getId().equals(id));
    }
}
