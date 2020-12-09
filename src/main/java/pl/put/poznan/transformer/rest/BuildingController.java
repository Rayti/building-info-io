package pl.put.poznan.transformer.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.logic.AreaVisitor;
import pl.put.poznan.transformer.logic.HeatingVisitor;
import pl.put.poznan.transformer.logic.LightVisitor;
import pl.put.poznan.transformer.logic.VolumeVisitor;
import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

@RestController
public class BuildingController {


    @GetMapping("/test_my_building")
    Building returnSameBuilding(@RequestBody Building building) {
        return building;
    }

    @GetMapping("/area")
    Answer returnArea(@RequestParam long id, @RequestBody Building building) {
        AreaVisitor areaVisitor = new AreaVisitor(id);
        building.accept(areaVisitor);
        return new Answer(String.valueOf(areaVisitor.getArea()));
    }

    @GetMapping("/volume")
    Answer returnVolume(@RequestParam long id, @RequestBody Building building){
        VolumeVisitor volumeVisitor = new VolumeVisitor(id);
        building.accept(volumeVisitor);
        return new Answer(String.valueOf(volumeVisitor.getVolume()));
    }

    @GetMapping("/light")
    Answer returnLight(@RequestParam long id, @RequestBody Building building) {
        LightVisitor lightVisitor = new LightVisitor(id);
        building.accept(lightVisitor);
        return new Answer(String.valueOf(lightVisitor.getLight()));
    }

    @GetMapping("heating")
    Answer returnHeating(@RequestParam long id, @RequestBody Building building) {
        HeatingVisitor heatingVisitor = new HeatingVisitor(id);
        building.accept(heatingVisitor);
        return new Answer(String.valueOf(heatingVisitor.getHeating()));
    }



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

        return building;
    }





}