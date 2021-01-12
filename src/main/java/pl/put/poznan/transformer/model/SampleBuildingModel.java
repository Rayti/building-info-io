package pl.put.poznan.transformer.model;

public class SampleBuildingModel {

    public static Building get(){
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
