package io.github.zekerzhayard.togglesprint;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class ToggleSprintingGui extends GuiScreen {
    public static String getBooleanText(boolean booleanIn) {
        return booleanIn ? "\u00a7a" + I18n.format("options.on") : "\u00a7c" + I18n.format("options.off") ;
    }

    private GuiScreen lastScreen;

    public ToggleSprintingGui(GuiScreen gui) {
        this.lastScreen = gui;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        String title = "\u00a7e\u00a7lToggleSprinting Mod Settings";
        this.drawCenteredString(this.fontRenderer, title, this.width / 2 + 30, this.height / 2 - 66, 0xFFFFFF);
        String toggleSprintStr = I18n.format("toggle.sprint");
        String toggleSneakStr = I18n.format("toggle.sneak");
        String autoJump = I18n.format("options.autoJump");
        this.drawString(this.fontRenderer, toggleSprintStr, this.width / 2 - 70 - this.fontRenderer.getStringWidth(toggleSprintStr), this.height / 2 - 22, 0xFFFFFF);
        this.drawString(this.fontRenderer, toggleSneakStr, this.width / 2 - 70 - this.fontRenderer.getStringWidth(toggleSneakStr), this.height / 2, 0xFFFFFF);
        this.drawString(this.fontRenderer, autoJump, this.width / 2 - 70 - this.fontRenderer.getStringWidth(autoJump), this.height / 2 + 22, 0xFFFFFF);
        this.drawString(this.fontRenderer, "\u00a73ToggleSprinting Mod v@VERSION@ by ZekerZhayard", 0, this.height - this.fontRenderer.FONT_HEIGHT, 0xFFFFFF);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        this.addButton(new GuiButton(0, this.width / 2 - 60, this.height / 2 - 28, 200, 20, ToggleSprintingGui.getBooleanText(Configuration.enableToggleSprint)) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                Configuration.enableToggleSprint = !Configuration.enableToggleSprint;
                this.displayString = ToggleSprintingGui.getBooleanText(Configuration.enableToggleSprint);
            }
        });
        this.addButton(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 6, 200, 20, ToggleSprintingGui.getBooleanText(Configuration.enableToggleSneak)) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                Configuration.enableToggleSneak = !Configuration.enableToggleSneak;
                this.displayString = ToggleSprintingGui.getBooleanText(Configuration.enableToggleSneak);
            }
        });
        this.addButton(new GuiButton(2, this.width / 2 - 60, this.height / 2 + 16, 200, 20, ToggleSprintingGui.getBooleanText(this.mc.gameSettings.getOptionOrdinalValue(GameSettings.Options.AUTO_JUMP))) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                ToggleSprintingGui.this.mc.gameSettings.setOptionValue(GameSettings.Options.AUTO_JUMP, 0);
                this.displayString = ToggleSprintingGui.getBooleanText(ToggleSprintingGui.this.mc.gameSettings.getOptionOrdinalValue(GameSettings.Options.AUTO_JUMP));
            }
        });
        this.addButton(new GuiButton(200, this.width / 2 - 100, this.height - 35, I18n.format("gui.done")) {
            public void onClick(double mouseX, double mouseY) {
                Configuration.saveConfig();
                ToggleSprintingGui.this.mc.displayGuiScreen(ToggleSprintingGui.this.lastScreen);
            }
        });
    }

    @Override
    public void onGuiClosed() {
        Configuration.saveConfig();
    }
}