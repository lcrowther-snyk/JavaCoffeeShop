package org.workshop.coffee.controller;

import org.workshop.coffee.domain.Person;
import org.workshop.coffee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
public class UploadController {

    @Autowired
    private PersonService personService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "person/upload";
    }

    @PostMapping("/uploadimage")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file, Principal principal) throws IOException {
        //get the file name and save it to the UPLOAD_DIRECTORY
        var fileName = file.getOriginalFilename();
        //get path with file separator
        var path = Paths.get(UPLOAD_DIRECTORY + File.separator + fileName);
        //save file to path

        //validate that there is no path traversal using the normalize method
        if (!path.normalize().startsWith(UPLOAD_DIRECTORY)) {
            //send "msg" to view
            model.addAttribute("msg", "Invalid path!");
            return "person/upload";
        }



        Files.write(path, file.getBytes());
        //get person
        var person = getPerson(model, principal);
        //set image name
        person.setProfilePicture(fileName);
        //save person
        personService.savePerson(person);
        //add "msg"
        model.addAttribute("msg", "Image uploaded successfully!");

        return "person/upload";
    }

    public Person getPerson(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "ERROR");
            return null;
        }

        var user = principal.getName();
        var person = personService.findByUsername(user);
        return person;
    }
}