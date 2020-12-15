package pl.put.poznan.transformer.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;
import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Location;
import pl.put.poznan.transformer.model.Room;

/**
 * BuildingController is class which enables to create REST communications.
 * It also provides logging of all requests on INFO and DEBUG layer.
 */
@RestController
public class BuildingController {

    /**
     * Enables logging.
     */
    private final Logger logger = LoggerFactory.getLogger(BuildingController.class);

    /**
     * Simple testing if your JSON file is correct.
     * @param building Building to be tested
     * @return exactly the same building as provided as argument if correct
     */
    @GetMapping("/test_my_building")
    Building returnSameBuilding(@RequestBody Building building) {
        logger.debug("Request for building check on " + building.getName());
        return building;
    }

    /**
     * Calculates area of specific Location
     * @param id id of location to be examined
     * @param building Building on which calculations are performed.
     * @return area of Location provided as argument
     */
    @GetMapping("/area/{id}")
    Answer returnArea(@PathVariable long id, @RequestBody Building building) {
        AreaVisitor areaVisitor = new AreaVisitor(id);
        building.accept(areaVisitor);
        logger.info("Request for area of " + id + " id");
        return new Answer(String.valueOf(areaVisitor.getArea()));
    }

    /**
     * Calculates volume of specific Location
     * @param id id of Location to be examined
     * @param building Building on which calculations are performed
     * @return volume of Location provided as argument
     */
    @GetMapping("/volume/{id}")
    Answer returnVolume(@PathVariable long id, @RequestBody Building building){
        VolumeVisitor volumeVisitor = new VolumeVisitor(id);
        building.accept(volumeVisitor);
        logger.info("Request for volume of " + id + " id");
        return new Answer(String.valueOf(volumeVisitor.getVolume()));
    }

    /**
     * Calculates light power per area unit of specific Location
     * @param id id of Location to be examined
     * @param building Building on which calculations are performed
     * @return light power per area unit of Location provided as argument
     */
    @GetMapping("/light/{id}")
    Answer returnLight(@PathVariable long id, @RequestBody Building building) {
        LightVisitor lightVisitor = new LightVisitor(id);
        building.accept(lightVisitor);
        logger.info("Request for light of " + id + " id");
        return new Answer(String.valueOf(lightVisitor.getLight()));
    }

    /**
     * Calculates power used on heating per volume unit of specific Location
     * @param id id of Location to be examined
     * @param building Building on which calculations are performed
     * @return power used on heating per volume of Location provided as argument
     */
    @GetMapping("/heating/{id}")
    Answer returnHeating(@PathVariable long id, @RequestBody Building building) {
        HeatingVisitor heatingVisitor = new HeatingVisitor(id);
        building.accept(heatingVisitor);
        logger.info("Request for heating of " + id + " id");
        return new Answer(String.valueOf(heatingVisitor.getHeating()));
    }

    /**
     * Provides list of Locations which heating is over parameter provided
     * as argument
     * @param heating heating power on which Location is overheating
     * @param building Building on which calculations are performed
     * @return list of Locations meeting the conditions
     */
    @GetMapping("/overheating_locations/{heating}")
    Location[] returnOverheatingLocations(@PathVariable float heating, @RequestBody Building building) {
        OverheatingLocationsVisitor visitor = new OverheatingLocationsVisitor(heating);
        building.accept(visitor);
        logger.info("Request for locations with avg. heating above " + heating);
        return visitor.getOverheatedLocations();
    }

    /**
     * Generates simple building Location which can be further used to
     * check others methods behavior
     * @return Building witch 2 Levels and 6 Rooms
     */
    @GetMapping("/sample_building")
    Building returnSampleBuilding() {
        Room room1 = new Room(5671, "Kitchen", 25, 140,
                30, 10);
        Room room2 = new Room(5672, "Hallway", 50, 300,
                25, 19);
        Room room3 = new Room(5673, "Bathroom", 12, 50,
                50, 60);
        Room room4 = new Room(5681, "MasterBedroom", 23, 152,
                26, 30);
        Room room5 = new Room(5682, "GuestBedroom",  15, 90,
                17, 13);
        Room room6 = new Room(5691, "Attic", 75, 210,
                3, 1);

        Level level0 = new Level(8830, "Ground floor",
                room1, room2, room3);
        Level level1 = new Level(8831, "First floor",
                room4, room5);
        Level level2 = new Level(8832, "Second floor",
                room6);

        Building building = new Building(1750, "Sample house",
                level0, level1, level2);

        logger.debug("Request for getting sample building");
        return building;
    }
}
