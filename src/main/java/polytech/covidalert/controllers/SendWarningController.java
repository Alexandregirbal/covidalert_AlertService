package polytech.covidalert.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.exception.FormNotCompletedException;
import polytech.covidalert.exception.ResourceAlreadyExistsException;
import polytech.covidalert.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//http://localhost:8092/covidalert/api/send-warning"
@RestController
@RequestMapping("/covidalert/api/send-warning")
public class SendWarningController {
    @Autowired
    private SendWarningRepository sendWarningRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<SendWarning> list() {
        return sendWarningRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{userEmail}")
    public boolean get(@PathVariable String userEmail) {
        System.out.println(userEmail);

        User userInfected = userRepository.findByEmail(userEmail);
        SendWarning sendWarning = sendWarningRepository.findByUserIdInfected(userInfected.getUser_id());
        if ( sendWarning == null){
            return false;
            //we should verify that the warning is in a certain period of time (7 days for example)
        } else {
            return true;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SendWarning create(@RequestBody final SendWarning sendWarning) {
        if ( sendWarningRepository.findById(sendWarning.getSend_warning_id()).isPresent()){
            throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "sendWarning with ID " + sendWarning.getSend_warning_id()+ " already exist.");
        }
        return sendWarningRepository.saveAndFlush(sendWarning);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping("/list")
    public List<SendWarning> createAllWarningFromUser(@RequestBody final List<SendWarning> listSendWarning) {
        List<SendWarning> sendWarningSaved = new ArrayList<SendWarning>();
        for(int i = 0; i<listSendWarning.size(); i++) {
            SendWarning sendWarning = listSendWarning.get(i);
            if (sendWarningRepository.findById(sendWarning.getSend_warning_id()).isPresent()) {
                throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "sendWarning with ID " + sendWarning.getSend_warning_id() + " already exist.");
            }
            sendWarningSaved.add(sendWarningRepository.saveAndFlush(sendWarning));
        }
        return sendWarningSaved;
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        sendWarningRepository.deleteById(id);
    }

    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    public SendWarning update(@PathVariable Long id, @RequestBody SendWarning sendWarning) {
        // TODO: Ajouter ici une validation si tous
        // les champs ont ete passes
        if (id !=null && sendWarning.getWarning_date()!=null) {
            // Sinon, retourner une erreur 400 (Bad Payload)
            Optional<SendWarning> existingSendWarning = sendWarningRepository.findById(id);
            if (existingSendWarning.isPresent()){
                SendWarning sendWarningBD = existingSendWarning.get();
                BeanUtils.copyProperties(sendWarning, sendWarningBD, "send_warning_id");
                return sendWarningRepository.saveAndFlush(sendWarningBD);
            }

            throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "sendWarning with ID " + sendWarning.getSend_warning_id()+ " not found.");
        }
        else{
            throw new FormNotCompletedException(HttpStatus.NOT_FOUND, "Form not fully completed");
        }
    }
}
