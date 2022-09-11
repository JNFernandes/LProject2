package app.ui.gui;

import javafx.stage.FileChooser;

public class FileChooserLegacyDataUI
{
    private FileChooser fileChooser;

    private FileChooserLegacyDataUI()
    {
        fileChooser = new FileChooser();
        bindExtensionFilter("Legacy Data Files", "*.csv");
    }

    public static FileChooser createFileChooserLegacyData()
    {
        FileChooserLegacyDataUI fcLegacyData = new FileChooserLegacyDataUI();
        return fcLegacyData.fileChooser;
    }

    private void bindExtensionFilter(String description, String extension)
    {
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(filter);
    }

}
