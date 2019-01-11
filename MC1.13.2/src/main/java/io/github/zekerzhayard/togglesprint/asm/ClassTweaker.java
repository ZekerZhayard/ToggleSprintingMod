package io.github.zekerzhayard.togglesprint.asm;

import java.io.File;
import java.util.List;

import io.github.zekerzhayard.togglesprint.Configuration;
import net.minecraft.client.main.Main;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class ClassTweaker implements ITweaker {
    @Override()
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        Configuration.loadConfig(gameDir.getAbsolutePath() + File.separator + "ToggleSprinting.properties");
    }

    @Override()
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.registerTransformer(ClassTransformer.class.getName());
    }

    @Override()
    public String getLaunchTarget() {
        return Main.class.getName();
    }

	@Override()
	public String[] getLaunchArguments() {
		return new String[0];
	}
}