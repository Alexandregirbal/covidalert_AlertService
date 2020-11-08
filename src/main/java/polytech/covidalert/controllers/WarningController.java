package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.models.Warning;
import polytech.covidalert.models.WarningRepository;

import java.util.List;

@RestController
@RequestMapping("/covidalert/api/warning")
public class WarningController {
    @Autowired
    private WarningRepository warningRepository;

    @GetMapping
    public List<Warning> list() {
        return warningRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Warning get(@PathVariable Long id) {
        if (! warningRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warning with ID " +id+ " not found.");
        }
        return warningRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Warning create(@RequestBody final Warning warning) {
        return warningRepository.saveAndFlush(warning);
    }
}
