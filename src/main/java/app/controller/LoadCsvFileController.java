package app.controller;

import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.store.SnsUserStore;
import app.mappers.dto.SnsUserDTO;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class LoadCsvFileController {

    /**
     * app atribute of type App
     */

    private final App app;
    /**
     * company atribute of type Company
     */
    private final Company company;
    /**
     * snsUserStore atribute of type SnsUserStore
     */
    private SnsUserStore snsUserStore;

    /**
     * LoadCsvFileController constructor
     */

    public LoadCsvFileController() {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.snsUserStore = this.company.getSnsUserStore();
    }

    /**
     * method to get the list from external module that comes from the store
     * @param fileName
     * @return list of objects of type SnsUserDTO
     * @throws FileNotFoundException - to be treated in the UI
     */

    public List<SnsUserDTO> getListFromExternalModule(String fileName) throws FileNotFoundException {
        return this.snsUserStore.getListFromExternalModule(fileName);
    }

    /**
     * method to get the list from the store after validation of the users
     * @param snsUserDTOList
     * @return list of objects of type SnsUserDTO
     * @throws FileNotFoundException - to be treated in the UI
     */

    public List<SnsUserDTO> registerSnsUsersFromExternalModule(List<SnsUserDTO> snsUserDTOList) {
        return this.snsUserStore.registerSnsUsersFromExternalModule(snsUserDTOList);
    }



}
