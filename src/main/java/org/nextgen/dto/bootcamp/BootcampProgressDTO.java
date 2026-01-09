package org.nextgen.dto.bootcamp;

import java.util.List;
import java.util.UUID;
import org.nextgen.model.ModuleProgress;

public class BootcampProgressDTO {
    public UUID id;
    public UUID bootcampStartId;
    public int progressPercentage = 0;
    public List<LayerProgressDTO> layerProgress;

    public static BootcampProgressDTO tDto(org.nextgen.model.BootcampProgress bootcampProgress){
        BootcampProgressDTO bootcampProgressDTO = new BootcampProgressDTO();
        bootcampProgressDTO.id = bootcampProgress.id;
        bootcampProgressDTO.layerProgress = LayerProgressDTO.tDtoList(bootcampProgress.completedLayers);
        bootcampProgressDTO.layerProgress.forEach(layerProgressDTO -> {
            UUID targetLayerProgressId = layerProgressDTO.id; 
            List<ModuleProgress> filtered = bootcampProgress.completedModules.stream()
                    .filter(mp ->
                        mp.layerProgress != null &&
                        mp.layerProgress.id != null &&
                        mp.layerProgress.id.equals(targetLayerProgressId)
                    )
                    .toList(); 
            layerProgressDTO.moduleProgresses = ModuleProgressDTO.tDtoList(filtered);
        });
        bootcampProgressDTO.progressPercentage = bootcampProgress.progressPercentage;
        bootcampProgressDTO.bootcampStartId = bootcampProgress.bootcampStart.id;
        return bootcampProgressDTO;
    }
}
