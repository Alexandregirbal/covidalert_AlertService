package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.models.SendWarning;
import polytech.covidalert.models.SendWarningRepository;
import polytech.covidalert.models.Warning;
import polytech.covidalert.models.WarningRepository;

import java.util.List;

@RestController
@RequestMapping("/covidalert/api/send-warning")
public class SendWarningController {
    @Autowired
    private SendWarningRepository sendWarningRepository;

    @GetMapping
    public List<SendWarning> list() {
        return sendWarningRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public SendWarning get(@PathVariable Long id) {
        if (! sendWarningRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SendWarning with ID " +id+ " not found.");
        }
        return sendWarningRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SendWarning create(@RequestBody final SendWarning sendWarning) {
        return sendWarningRepository.saveAndFlush(sendWarning);
    }
}
