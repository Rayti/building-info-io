package pl.put.poznan.transformer.api;

import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.SampleBuildingModel;
import pl.put.poznan.transformer.service.BuildingService;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {

    @Test
    void existingSameIndexes() {
        Building building = SampleBuildingModel.get();

        Building building1 = SampleBuildingModel.get();

        BuildingService service = new BuildingService();
        service.setBuilding(building);

        assertTrue(service.idExistsInStructure(building1.getId()));

        assertTrue(service.idExistsInStructure(building1.getLevelList().get(0).getId()));

        assertTrue(service.idExistsInStructure(building1.getLevelList().get(0).getRoomList().get(0).getId()));

    }
}