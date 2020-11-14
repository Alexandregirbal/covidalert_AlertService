package polytech.covidalert.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.exception.FormNotCompletedException;
import polytech.covidalert.exception.ResourceAlreadyExistsException;
import polytech.covidalert.models.Warning;
import polytech.covidalert.models.WarningRepository;

import java.util.List;
import java.util.Optional;

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
        if ( warningRepository.findById(warning.getWarning_id()).isPresent()){
            throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "Warning with ID " + warning.getWarning_id()+ " already exist.");
        }
        return warningRepository.saveAndFlush(warning);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        warningRepository.deleteById(id);
    }

    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    public Warning update(@PathVariable Long id, @RequestBody Warning warning) {
        // TODO: Ajouter ici une validation si tous
        // les champs ont ete passes
        if (id !=null && warning.getType()!=null &&
                warning.getLabel()!=null) {
            // Sinon, retourner une erreur 400 (Bad Payload)
            Optional<Warning> existingWarning = warningRepository.findById(id);
            if (existingWarning.isPresent()){
                Warning warningBD = existingWarning.get();
                BeanUtils.copyProperties(warning, warningBD, "warning_id");
                return warningRepository.saveAndFlush(warningBD);
            }

            throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "warning with ID " + warning.getWarning_id()+ " not found.");
        }
        else{
            throw new FormNotCompletedException(HttpStatus.NOT_FOUND, "Form not fully completed");
        }
    }
}
