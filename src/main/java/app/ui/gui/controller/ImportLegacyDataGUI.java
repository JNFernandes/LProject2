package app.ui.gui.controller;

import app.controller.ExportVaccinationStatisticsController;
import app.controller.ImportLegacyDataController;
import app.ui.MainFx;
import app.ui.gui.CoordinatorScene1;
import app.ui.gui.ImportLegacyDataScene1;

import java.util.logging.Level;
import java.util.logging.Logger;

    public class ImportLegacyDataGUI {

        private MainFx mainApp;
        private ImportLegacyDataController importLegacyDataController;


        public ImportLegacyDataGUI(MainFx mainApp)
        {
            this.mainApp = mainApp;
            this.importLegacyDataController = new ImportLegacyDataController();
        }

    }
