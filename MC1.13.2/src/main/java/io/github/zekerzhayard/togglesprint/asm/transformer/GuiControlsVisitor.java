package io.github.zekerzhayard.togglesprint.asm.transformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.github.zekerzhayard.togglesprint.ToggleSprinting;
import net.minecraft.client.gui.GuiControls;

public class GuiControlsVisitor extends ClassVisitor {
    public GuiControlsVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
        this.cv = cv;
    }

    @Override()
    public MethodVisitor visitMethod(final int access, final String name,  final String descriptor, final String signature, final String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ((name.equals("c") || name.equals("initGui")) && descriptor.equals("()V")) {
            return new GuiControlsVisitor.InitGuiVisitor(methodVisitor);
        }
        return methodVisitor;
    }

    public class InitGuiVisitor extends MethodVisitor {
        public InitGuiVisitor(MethodVisitor mv) {
            super(Opcodes.ASM6, mv);
            this.mv = mv;
        }

        @Override()
        public void visitInsn(final int opcode) {
            if (opcode == Opcodes.RETURN) {
                this.mv.visitVarInsn(Opcodes.ALOAD, 0);
                this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, ToggleSprinting.class.getName().replace('.', '/'), "onGuiControlsInit", "(L" + GuiControls.class.getName().replace('.', '/') + ";)V", false);
            }
            this.mv.visitInsn(opcode);
        }
    }
}