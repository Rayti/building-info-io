package pl.put.poznan.transformer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.SampleBuildingModel;

@Controller
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);


    @GetMapping("/api/building")
    public String showEmptyBuildingView(Model model) {
        logger.debug("Request for Empty Building View");
        return "index";
    }

    @GetMapping("/api/building/sample")
    public String showBuildingView(Model model){
        Building building = SampleBuildingModel.get();
        logger.debug("Request for Building View");
        model.addAttribute("building", building);
        return "index";
    }

}
