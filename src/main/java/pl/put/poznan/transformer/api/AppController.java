package pl.put.poznan.transformer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;
import pl.put.poznan.transformer.model.SampleBuildingModel;
import pl.put.poznan.transformer.service.BuildingService;

@Controller
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);

    private BuildingService service;

    public AppController() {
        this.service = new BuildingService();
    }

    @GetMapping("/api/building")
    public String showMainView(Model model) {
        logger.debug("Request for empty view");
        return "index";
    }

    @GetMapping("/api/building/sample")
    public String createSampleBuildingAndshowMainView(Model model){
        Building building = SampleBuildingModel.get();
        logger.info("Request for view with sample");
        model.addAttribute("building", building);
        return "index";
    }

    @GetMapping("/api/building/options")
    public String showBuildingWithOptions(Model model) {
        logger.info("Request for view with building: name: " + service.getBuilding().getName() +
                ", id: " + service.getBuilding().getId());
        model.addAttribute("building", service.getBuilding());
        return "index";
    }

    @GetMapping("/api/building/create")
    public String goToBuildingCreation(Model model){
        return "create/buildingCreation";
    }

    @PostMapping("/api/building/create")
    public String createBuildingAndGoToLevelCreation(@RequestParam String buildingId,
                                          @RequestParam String buildingName,
                                          Model model) {
        if(!correctFields(buildingId, buildingName, model)) {
            return "error";
        }
        Building building = new Building(Long.parseLong(buildingId), buildingName);
        service.setBuilding(building);
        logger.info("Creating building: name:" + buildingName + ", id: " + buildingId);
        model.addAttribute("building", building);
        return "create/levelCreation";
    }

    @PostMapping("/api/building/create/level")
    public String createLevel(@RequestParam String newLevelId,
                              @RequestParam String newLevelName,
                              Model model) {
        if (!correctFields(newLevelId, newLevelName, model)){
            model.addAttribute("building", service.getBuilding());
            return "create/levelCreation";
        }
        Level level = new Level(Long.parseLong(newLevelId), newLevelName);
        service.addLevel(level);
        logger.info("Creating level: name: " + newLevelName + ", id: " + newLevelId);
        model.addAttribute("building", service.getBuilding());
        return "create/levelCreation";
    }

    @PostMapping("/api/building/create/room/{levelId}")
    public String createRoom(@RequestParam String newRoomId,
                             @RequestParam String newRoomName,
                             @RequestParam String newRoomArea,
                             @RequestParam String newRoomCube,
                             @RequestParam String newRoomHeating,
                             @RequestParam String newRoomLight,
                             @PathVariable long levelId,
                             Model model) {
        if(!correctRoomFields(newRoomId, newRoomName, newRoomArea, newRoomCube, newRoomHeating, newRoomLight, model)){
            model.addAttribute("building", service.getBuilding());
            return "create/levelCreation";
        }
        if(newRoomArea.isEmpty()){
            model.addAttribute("message", "EmptyArea");
            return "error";
        }
        Room room = new Room(Long.parseLong(newRoomId), newRoomName,
                Long.parseLong(newRoomArea), Long.parseLong(newRoomCube),
                Long.parseLong(newRoomHeating), Long.parseLong(newRoomLight));
        service.addRoom(levelId, room);
        logNewRoom(room);
        model.addAttribute("building", service.getBuilding());
        //model.addAttribute("level", service.getLevel(levelId));
        return "create/levelCreation";
    }


    private void logNewRoom(Room room) {
        logger.info("Creating room: name: " + room.getName() + ", id: " +
                room.getId() + ", area: " + room.getArea() + ", volume: " + room.getCube() +
                ", heating: " + room.getHeating() + ", light: " + room.getLight());
    }


    private boolean correctRoomFields(String roomId,
                                      String roomName,
                                      String roomArea,
                                      String roomCube,
                                      String roomHeating,
                                      String roomLight,
                                      Model model) {
        if (roomId.isEmpty() ||
                roomArea.isEmpty() ||
                roomName.isEmpty() ||
                roomCube.isEmpty() ||
                roomHeating.isEmpty() ||
                roomLight.isEmpty() ||
                roomId.contains(",") ||
                roomArea.contains(",") ||
                roomCube.contains(",") ||
                roomHeating.contains(",") ||
                roomLight.contains(",")
        ) {
            model.addAttribute("message", "All room fields must be filled!");
            return false;
        }
        return true;
    }

    private boolean correctFields(String id, String name, Model model) {
        if(name.isEmpty() || id.isEmpty()){
            model.addAttribute("message", "All level fields must be filled");
            return false;
        }
        return true;
    }

}
