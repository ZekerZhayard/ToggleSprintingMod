package io.github.zekerzhayard.togglesprint.asm;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer {
    private List<String> targets = Lists.newArrayList("cfq", "cft", "ckv", "net.minecraft.client.KeyboardListener", "net.minecraft.client.Minecraft", "net.minecraft.client.gui.GuiControls");

    @Override()
    public byte[] transform(String className, String transformedName, byte[] basicClass) {
        if (targets.contains(className)) {
            System.out.println("Found the class: " + transformedName);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            new ClassReader(basicClass).accept(new ClassVisitor(Opcodes.ASM6, classWriter) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
                    // cfq.a(JIIII)V -> net/minecraft/client/KeyboardListener.onKeyEvent(JIIII)V
                    // cft.m()V -> net/minecraft/client/Minecraft.runTick()V
                    // ckv.c()V -> net/minecraft/client/gui/GuiControls.initGui()V
                    boolean isOnKeyEvent = name.equals("a") || name.equals("onKeyEvent");
                    boolean isInitGui = name.equals("c") || name.equals("initGui");
                    boolean isRunTick = name.equals("m") || name.equals("runTick");
                    if (((isInitGui || isRunTick) && desc.equals("()V")) || (isOnKeyEvent && desc.equals("(JIIII)V"))) {
                        System.out.println("Found the method: " + name + desc);
                        return new MethodVisitor(Opcodes.ASM6, methodVisitor) {
                            @Override
                            public void visitInsn(final int opcode) {
                                if (opcode == Opcodes.RETURN) {
                                    if (isOnKeyEvent) {
                                        this.mv.visitVarInsn(Opcodes.LLOAD, 1);
                                        IntStream.rangeClosed(3, 5).forEach(i -> this.mv.visitVarInsn(Opcodes.ILOAD, i));
                                        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/togglesprint/ToggleSprinting", "onKeyEvent", "(JIII)V", false);
                                    } else {
                                        this.mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/togglesprint/ToggleSprinting", isInitGui ? "onGuiControlsInit" : "onClientTick", "(L" + className.replace('.', '/') + ";)V", false);
                                    }
                                }
                                this.mv.visitInsn(opcode);
                            }
                        };
                    }
                    return methodVisitor;
                }
            }, ClassReader.SKIP_FRAMES);
            return classWriter.toByteArray();
        }
        return basicClass;
    }
}