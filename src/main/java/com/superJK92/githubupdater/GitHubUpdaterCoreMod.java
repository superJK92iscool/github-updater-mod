package com.superJK92.githubupdater;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(value = -1000)
public class GitHubUpdaterCoreMod implements IFMLLoadingPlugin {
    private static final Logger LOGGER = LogManager.getLogger("GitHubUpdater");

    private static final String[][] GITHUB_MODS = {
        {"TIABS-1.12.2.jar", "mods/TIABS-1.12.2.jar"},
        {"TinkerSkyblock-1.12.2.jar", "mods/TinkerSkyblock-1.12.2.jar"},
        {"extrautils2-1.12.2.jar", "mods/extrautils2-1.12.2.jar"},
        {"packcompanion.jar", "mods/packcompanion.jar"},
        {"pebbledrop.jar", "mods/pebbledrop.jar"}
    };

    private static final String REPO_BASE_URL = "https://raw.githubusercontent.com/superJK92iscool/mc-mods/main/";

    static {
        LOGGER.info("[GitHubUpdater] Checking for custom mod updates from GitHub...");
        for (String[] modInfo : GITHUB_MODS) {
            downloadMod(modInfo[0], modInfo[1]);
        }
    }

    private static void downloadMod(String remoteFileName, String localPath) {
        try {
            String fileUrl = REPO_BASE_URL + remoteFileName;
            File targetFile = new File(localPath);

            File parentDir = targetFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            LOGGER.info("[GitHubUpdater] Fetching {}...", remoteFileName);
            URL url = new URL(fileUrl);
            try (InputStream in = url.openStream()) {
                Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("[GitHubUpdater] Successfully updated {}", remoteFileName);
            }
        } catch (Exception e) {
            LOGGER.error("[GitHubUpdater] Failed to download {}: {}", remoteFileName, e.getMessage());
        }
    }

    @Override
    public String[] getASMTransformerClass() { return new String[0]; }
    @Override
    public String getModContainerClass() { return null; }
    @Override
    public String getSetupClass() { return null; }
    @Override
    public void injectData(Map<String, Object> data) {}
    @Override
    public String getAccessTransformerClass() { return null; }
}
