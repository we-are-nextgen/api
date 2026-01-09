package org.nextgen.dto.bootcamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.nextgen.model.LayerProgress;
public class LayerProgressDTO {
    public UUID id;
    public UUID layerId;
    public String layerName;
    public boolean completed;
    public List<ModuleProgressDTO> moduleProgresses;
    
    public void addModuleProgress(ModuleProgressDTO moduleProgressDTO){
        if (this.moduleProgresses == null){
            this.moduleProgresses = new ArrayList<>();
        }
        this.moduleProgresses.add(moduleProgressDTO);
    }

    static LayerProgressDTO toDto(LayerProgress layerProgress){
        LayerProgressDTO layerProgressDTO = new LayerProgressDTO();
        layerProgressDTO.id = layerProgress.id;
        layerProgressDTO.layerId = layerProgress.layer.id;
        layerProgressDTO.layerName = layerProgress.layer.name;
        layerProgressDTO.completed = layerProgress.completed;
        return layerProgressDTO;
    }
    
    static List<LayerProgressDTO> tDtoList(List<LayerProgress> layerProgressList){
        List<LayerProgressDTO> layerProgressDTOList = new ArrayList<>();
        for (LayerProgress lp : layerProgressList) {
            layerProgressDTOList.add(LayerProgressDTO.toDto(lp));
        }
        return layerProgressDTOList;
    }
}
