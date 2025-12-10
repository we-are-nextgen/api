package org.nextgen.dto.importer;

public class ImportParamDTO {

    public String gitRepoUrl;
    public String gitBranch = "main";   // optional, default main
    public String gitPath = "/";        // optional, path inside repo

    // Optional: allow forcing update if track already exists
    public boolean forceUpdate = false;
}
