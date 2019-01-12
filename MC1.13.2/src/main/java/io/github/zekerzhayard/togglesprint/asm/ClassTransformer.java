package io.github.zekerzhayard.togglesprint.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import io.github.zekerzhayard.togglesprint.asm.transformer.GuiControlsVisitor;
import io.github.zekerzhayard.togglesprint.asm.transformer.KeyboardListenerVisitor;
import io.github.zekerzhayard.togglesprint.asm.transformer.MinecraftVisitor;
import net.minecraft.client.KeyboardListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer {
    @Override()
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        if (name.equals(GuiControls.class.getName())) {
            new ClassReader(basicClass).accept(new GuiControlsVisitor(classWriter), ClassReader.SKIP_FRAMES);
            return classWriter.toByteArray();
        } else if (name.equals(KeyboardListener.class.getName())) {
            new ClassReader(basicClass).accept(new KeyboardListenerVisitor(classWriter), ClassReader.SKIP_FRAMES);
            return classWriter.toByteArray();
        } else if (name.equals(Minecraft.class.getName())) {
            new ClassReader(basicClass).accept(new MinecraftVisitor(classWriter), ClassReader.SKIP_FRAMES);
            return classWriter.toByteArray();
        }
        return basicClass;
    }
}