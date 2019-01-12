package io.github.zekerzhayard.togglesprint.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Sets;

import net.minecraft.resources.AbstractResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;

public class TSResourcePack extends AbstractResourcePack {
    public TSResourcePack() {
        super(null);
    }

    @Override()
    protected InputStream getInputStream(String path) throws IOException {
        return this.getClass().getClassLoader().getResourceAsStream(path);
    }

    @Override()
    protected boolean resourceExists(String path) {
        return this.getClass().getClassLoader().getResource(path) != null;
    }

    @Override()
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String path, int maxDepth, Predicate<String> filter) {
        return Sets.newHashSet();
    }

    @Override()
    public Set<String> getResourceNamespaces(ResourcePackType type) {
        return Sets.newHashSet(this.getName());
    }

    @Override()
    public void close() {

    }

    @Override()
    public String getName() {
        return "togglesprinting";
    }
}