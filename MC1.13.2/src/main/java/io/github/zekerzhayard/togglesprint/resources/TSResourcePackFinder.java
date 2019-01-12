package io.github.zekerzhayard.togglesprint.resources;

import java.io.IOException;
import java.util.Map;

import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.data.PackMetadataSection;

public class TSResourcePackFinder implements IPackFinder {
    @Override()
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
        TSResourcePack rp = new TSResourcePack();
        try {
		    nameToPackMap.put("togglesprinting", packInfoFactory.create("togglesprinting", true, () -> rp, rp, rp.getMetadata(PackMetadataSection.SERIALIZER), ResourcePackInfo.Priority.TOP));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}