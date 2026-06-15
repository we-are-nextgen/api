package org.nextgen.dto;

import java.util.ArrayList;
import java.util.List;

public class WorkshopCatalogEntryDTO {
    public String name;
    public String title;
    public String description;
    public int capacity;
    public int available;
    public int allocated;

    public static List<WorkshopCatalogEntryDTO> fromEnvironments(List<EnvironmentEntry> environments) {
        List<WorkshopCatalogEntryDTO> entries = new ArrayList<>();
        if (environments == null) {
            return entries;
        }
        for (EnvironmentEntry env : environments) {
            if (env.workshop == null || env.workshop.name == null) {
                continue;
            }
            WorkshopCatalogEntryDTO entry = new WorkshopCatalogEntryDTO();
            entry.name = env.workshop.name;
            entry.title = env.workshop.title;
            entry.description = env.workshop.description;
            entry.capacity = env.capacity;
            entry.available = env.available;
            entry.allocated = env.allocated;
            entries.add(entry);
        }
        return entries;
    }

    public static class EnvironmentEntry {
        public String name;
        public String state;
        public WorkshopInfo workshop;
        public int capacity;
        public int available;
        public int allocated;
    }

    public static class WorkshopInfo {
        public String name;
        public String title;
        public String description;
    }
}
