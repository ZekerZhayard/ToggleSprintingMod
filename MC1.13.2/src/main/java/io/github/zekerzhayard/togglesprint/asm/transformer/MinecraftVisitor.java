package io.github.zekerzhayard.togglesprint.asm.transformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.github.zekerzhayard.togglesprint.ToggleSprinting;
import io.github.zekerzhayard.togglesprint.asm.ClassTweaker;
import io.github.zekerzhayard.togglesprint.resources.TSResourcePackFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackList;

public class MinecraftVisitor extends ClassVisitor {
    public MinecraftVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
        this.cv = cv;
    }

    @Override()
    public MethodVisitor visitMethod(final int access, final String name,  final String descriptor, final String signature, final String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("<init>") && descriptor.equals("(L" + GameConfiguration.class.getName().replace('.', '/') + ";)V")) {
            return new MinecraftVisitor.InitVisitor(methodVisitor);
        } else if ((name.equals("m") || name.equals("runTick")) && descriptor.equals("()V")) {
            return new MinecraftVisitor.RunTickVisitor(methodVisitor);
        }
        return methodVisitor;
    }

    public class InitVisitor extends MethodVisitor {
        public InitVisitor(MethodVisitor mv) {
            super(Opcodes.ASM6, mv);
            this.mv = mv;
        }

        @Override()
        public void visitFieldInsn(final int opcode, final String owner, final String name, final String descriptor) {
            this.mv.visitFieldInsn(opcode, owner, name, descriptor);
            if (opcode == Opcodes.GETFIELD && owner.equals(Minecraft.class.getName().replace('.', '/')) && (name.equals("packFinder") || name.equals("au"))) {
                String o = ResourcePackList.class.getName(), n = ClassTweaker.DEV ? "addPackFinder" : "a", d = "(L" + IPackFinder.class.getName().replace('.', '/') + ";)V";
                this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, o.replace('.', '/'), n, d, false);
                this.mv.visitVarInsn(Opcodes.ALOAD, 0);
                this.mv.visitFieldInsn(Opcodes.GETFIELD, Minecraft.class.getName().replace('.', '/'), ClassTweaker.DEV ? "resourcePackRepository" : "av", "L" + ResourcePackList.class.getName().replace('.', '/') + ";");
                this.mv.visitTypeInsn(Opcodes.NEW, TSResourcePackFinder.class.getName().replace('.', '/'));
                this.mv.visitInsn(Opcodes.DUP);
                this.mv.visitMethodInsn(Opcodes.INVOKESPECIAL, TSResourcePackFinder.class.getName().replace('.', '/'), "<init>", "()V", false);
                // this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, o.replace('.', '/'), n, d, false);
            }
        }
    }

    public class RunTickVisitor extends MethodVisitor {
        public RunTickVisitor(MethodVisitor mv) {
            super(Opcodes.ASM6, mv);
            this.mv = mv;
        }

        @Override()
        public void visitInsn(final int opcode) {
            if (opcode == Opcodes.RETURN) {
                this.mv.visitVarInsn(Opcodes.ALOAD, 0);
                this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, ToggleSprinting.class.getName().replace('.', '/'), "onClientTick", "(L" + Minecraft.class.getName().replace('.', '/') + ";)V", false);
            }
            this.mv.visitInsn(opcode);
        }
    }
}