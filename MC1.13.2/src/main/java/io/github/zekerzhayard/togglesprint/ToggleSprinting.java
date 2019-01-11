package io.github.zekerzhayard.togglesprint;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Maps;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class ToggleSprinting {
    private static boolean isSneak = false;
    private static boolean isSprint = false;

    @SuppressWarnings(value = { "unchecked" })
    public static void onGuiControlsInit(GuiControls gui) throws IllegalAccessException, InvocationTargetException {
        ((List<GuiButton>) Stream.of(GuiScreen.class.getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            return field.getName().equals("o") || field.getName().equals("buttons");
        }).findFirst().get().get(gui)).removeIf(button -> button.id == GameSettings.Options.AUTO_JUMP.getOrdinal());

        ((List<IGuiEventListener>) Stream.of(GuiScreen.class.getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            return field.getName().equals("j") || field.getName().equals("children");
        }).findFirst().get().get(gui)).removeIf(button -> {
            try {
                return ((GuiButton) button).id == GameSettings.Options.AUTO_JUMP.getOrdinal();
            } catch (ClassCastException e) {
                return false;
            }
        });

        Stream.of(GuiScreen.class.getDeclaredMethods()).filter(method -> {
            method.setAccessible(true);
            return (method.getName().equals("a") || method.getName().equals("addButton")) && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(GuiButton.class);
        }).findFirst().get().invoke(gui, new GuiButton(300, gui.width / 2 + 5, 42, 150, 20, "ToggleSprint Settings...") {
            @Override()
            public void onClick(double mouseX, double mouseY) {
                Minecraft.getInstance().displayGuiScreen(new ToggleSprintingGui(gui));
            }
        });
    }

    public static void onKeyEvent(long window, int key, int scancode, int action) {
        if (window == Minecraft.getInstance().mainWindow.getHandle() && action == GLFW.GLFW_PRESS) {
            if (Minecraft.getInstance().gameSettings.keyBindSprint.matchesKey(key, scancode) && Configuration.enableToggleSprint) {
                ToggleSprinting.isSprint = !ToggleSprinting.isSprint;
            }
            if (Minecraft.getInstance().gameSettings.keyBindSneak.matchesKey(key, scancode) && Configuration.enableToggleSneak) {
                ToggleSprinting.isSneak = !ToggleSprinting.isSneak;
            }
        }
    }

    public static void onClientTick(Minecraft mc) {
        if (mc.world != null) {
            Map<KeyBinding, Boolean> map = Maps.newHashMap();
            map.put(mc.gameSettings.keyBindSprint, Configuration.enableToggleSprint && ToggleSprinting.isSprint);
            map.put(mc.gameSettings.keyBindSneak, Configuration.enableToggleSneak && ToggleSprinting.isSneak && mc.currentScreen == null);
            map.entrySet().stream().filter(e -> e.getValue()).forEach(e -> {
                try {
                    KeyBinding.setKeyBindState((InputMappings.Input) Stream.of(KeyBinding.class.getDeclaredFields()).filter(field -> {
                        field.setAccessible(true);
                        return field.getName().equals("h") || field.getName().equals("keyCode");
                    }).findFirst().get().get(e.getKey()), true);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
}