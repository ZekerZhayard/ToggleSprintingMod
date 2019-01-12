package io.github.zekerzhayard.togglesprint.asm.transformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import io.github.zekerzhayard.togglesprint.ToggleSprinting;

public class KeyboardListenerVisitor extends ClassVisitor {
    public KeyboardListenerVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
        this.cv = cv;
    }

    @Override()
    public MethodVisitor visitMethod(final int access, final String name,  final String descriptor, final String signature, final String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ((name.equals("a") || name.equals("onKeyEvent")) && descriptor.equals("(JIIII)V")) {
            return new KeyboardListenerVisitor.OnKeyEventVisitor(methodVisitor);
        }
        return methodVisitor;
    }

    public class OnKeyEventVisitor extends MethodVisitor {
        public OnKeyEventVisitor(MethodVisitor mv) {
            super(Opcodes.ASM6, mv);
            this.mv = mv;
        }

        @Override()
        public void visitInsn(final int opcode) {
            if (opcode == Opcodes.RETURN) {
                this.mv.visitVarInsn(Opcodes.LLOAD, 1);
                this.mv.visitVarInsn(Opcodes.ILOAD, 3);
                this.mv.visitVarInsn(Opcodes.ILOAD, 4);
                this.mv.visitVarInsn(Opcodes.ILOAD, 5);
                this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, ToggleSprinting.class.getName().replace('.', '/'), "onKeyEvent", "(JIII)V", false);
            }
            this.mv.visitInsn(opcode);
        }
    }
}