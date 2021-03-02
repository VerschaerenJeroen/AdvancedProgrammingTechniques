package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;

    @GetMapping("/venuelist")
    public String venuelist(Model model) {
        Iterable<Venue> venues = venueRepository.findAll();
        long nrOfVenues = venueRepository.count();
        model.addAttribute("venues", venues);
        model.addAttribute("nrOfVenues",nrOfVenues);
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/{id}"})
    public String venueDetails(Model model,
                               @PathVariable(required = false)  Integer id) {
        Optional<Venue> v = venueRepository.findById(id);
        long nrOfVenues = venueRepository.count();
        if (id!=null && v.isPresent() )
            model.addAttribute("venue", v.get());
            model.addAttribute("prevIndex", id>1 ? id-1 : nrOfVenues);
            model.addAttribute("nextIndex", id < nrOfVenues ? id + 1 : 1);
            model.addAttribute("showFilter", false);
        return "venuedetails";
    }
    @GetMapping({"/venuelist/filter"})
    public String venueListWithFilter(Model model) {
        Iterable<Venue> venues = venueRepository.findAll();
        long nrOfVenues = venueRepository.count();
        model.addAttribute("venues",venues);
        model.addAttribute("showFilters", true);
        model.addAttribute("nrOfVenues",nrOfVenues);
        return "venuelist";
    }
}