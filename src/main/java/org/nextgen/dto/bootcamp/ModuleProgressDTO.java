package org.nextgen.dto.bootcamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModuleProgressDTO {
    public UUID id;
    public UUID moduleId;
    public String moduleName;
    public boolean completed;
    
    public static ModuleProgressDTO tDto(org.nextgen.model.ModuleProgress moduleProgress){
        ModuleProgressDTO moduleProgressDTO = new ModuleProgressDTO();
        moduleProgressDTO.id = moduleProgress.id;
        moduleProgressDTO.moduleId = moduleProgress.module.id;
        moduleProgressDTO.moduleName = moduleProgress.module.name;
        moduleProgressDTO.completed = moduleProgress.completed;
        return moduleProgressDTO;
    }

    public static List<ModuleProgressDTO> tDtoList(List<org.nextgen.model.ModuleProgress> moduleProgressList ){
        List<ModuleProgressDTO> moduleProgressDTOList = new ArrayList<>();
        for (org.nextgen.model.ModuleProgress mp : moduleProgressList) {
            moduleProgressDTOList.add(ModuleProgressDTO.tDto(mp));
        }
        return moduleProgressDTOList;
    }

}
