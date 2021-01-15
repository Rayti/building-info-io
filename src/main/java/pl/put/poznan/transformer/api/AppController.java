package pl.put.poznan.transformer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;
import pl.put.poznan.transformer.model.*;
import pl.put.poznan.transformer.service.BuildingService;

import java.util.Arrays;
import java.util.List;

@Controller
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);

    private BuildingService service;

    public AppController() {
        this.service = new BuildingService();
    }

    @GetMapping("/api/building")
    public String showMainView(Model model) {
        logger.info("Request for front view");
        return "index";
    }

    @GetMapping("/api/building/sample")
    public String createSampleBuildingAndShowMainView(Model model){
        Building building = SampleBuildingModel.get();
        service.setBuilding(building);
        logger.info("Request for view with sample building");
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

    @GetMapping("/api/building/options/calculateArea")
    public String calculateArea(@RequestParam String newAreaId, Model model) {
        model.addAttribute("building", service.getBuilding());
        logger.info("Request for area of location with id: " + newAreaId);
        if(!checkCalculateData(newAreaId, model)) return "index";
        AreaVisitor visitor = new AreaVisitor(Long.parseLong(newAreaId));
        service.getBuilding().accept(visitor);
        float area = visitor.getArea();
        model.addAttribute("calcArea", area);
        model.addAttribute("calcAreaId", Long.parseLong(newAreaId));
        return "index";
    }

    @GetMapping("/api/building/options/calculateVolume")
    public String calculateVolume(@RequestParam String newVolumeId, Model model) {
        model.addAttribute("building", service.getBuilding());
        logger.info("Request for volume of location with id: " + newVolumeId);
        if(!checkCalculateData(newVolumeId, model)) return "index";
        VolumeVisitor visitor = new VolumeVisitor(Long.parseLong(newVolumeId));
        service.getBuilding().accept(visitor);
        float volume = visitor.getVolume();
        model.addAttribute("calcVolume", volume);
        model.addAttribute("calcVolumeId", Long.parseLong(newVolumeId));
        return "index";
    }

    @GetMapping("/api/building/options/calculateHeating")
    public String calculateHeating(@RequestParam String newHeatingId, Model model) {
        model.addAttribute("building", service.getBuilding());
        logger.info("Request for heating of location with id: " + newHeatingId);
        if(!checkCalculateData(newHeatingId, model)) return "index";
        HeatingVisitor visitor = new HeatingVisitor(Long.parseLong(newHeatingId));
        service.getBuilding().accept(visitor);
        float heating = visitor.getHeating();
        model.addAttribute("calcHeating", heating);
        model.addAttribute("calcHeatingId", Long.parseLong(newHeatingId));
        return "index";
    }

    @GetMapping("/api/building/options/calculateLight")
    public String calculateLight(@RequestParam String newLightId, Model model) {
        model.addAttribute("building", service.getBuilding());
        logger.info("Request for light power of location with id: " + newLightId);
        if(!checkCalculateData(newLightId, model)) return "index";
        LightVisitor visitor = new LightVisitor(Long.parseLong(newLightId));
        service.getBuilding().accept(visitor);
        float light = visitor.getLight();
        model.addAttribute("calcLight", light);
        model.addAttribute("calcLightId", Long.parseLong(newLightId));
        return "index";
    }

    @GetMapping("/api/building/options/getOverheatingLocations")
    public String overheatingLocations(@RequestParam String newHeatingPivot, Model model) {
        model.addAttribute("building", service.getBuilding());
        logger.info("Request for overheating locations with heating pivot: " + newHeatingPivot);
        if(!checkHeatingPivot(newHeatingPivot, model)) return "index";
        OverheatingLocationsVisitor visitor = new OverheatingLocationsVisitor(Float.parseFloat(newHeatingPivot));
        service.getBuilding().accept(visitor);
        List<Location> locations = Arrays.asList(visitor.getOverheatedLocations());
        model.addAttribute("overheatedLocations", locations);
        if(locations.isEmpty()) model.addAttribute("messagePivot", "There are no locations meeting the criteria");
        model.addAttribute("heatingPivot", newHeatingPivot);
        return "index";
    }

    private boolean checkHeatingPivot(String pivot, Model model) {
        if (pivot.isEmpty()) {
            model.addAttribute("message", "Empty field!");
            return false;
        }
        return true;
    }

    private boolean checkCalculateData(String id, Model model) {
        if (id.isEmpty()) {
            model.addAttribute("message", "Empty index field!");
            return false;
        }
        if(!service.idExistsInStructure(Long.parseLong(id))){
            model.addAttribute("message", "Id does not exists!");
            return false;
        }
        return true;
    }


    @GetMapping("/api/building/create")
    public String goToBuildingCreation(Model model){
        logger.info("Request for creating building view");
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
        if (existsInStructure(Long.parseLong(newLevelId), model)) {
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
        if (existsInStructure(Long.parseLong(newRoomId), model)) {
            model.addAttribute("building", service.getBuilding());
            return "create/levelCreation";
        }
        Room room = new Room(Long.parseLong(newRoomId), newRoomName,
                Long.parseLong(newRoomArea), Long.parseLong(newRoomCube),
                Long.parseLong(newRoomHeating), Long.parseLong(newRoomLight));
        service.addRoom(levelId, room);
        logNewRoom(room);
        model.addAttribute("building", service.getBuilding());
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
                roomLight.isEmpty()
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

    private boolean existsInStructure(long id, Model model) {
        if(service.idExistsInStructure(id)){
            model.addAttribute("message", "Id " + id + " already exists in your building!");
            return true;
        }
        return false;
    }

}
