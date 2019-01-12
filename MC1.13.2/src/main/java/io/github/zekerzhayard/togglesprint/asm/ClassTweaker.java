package io.github.zekerzhayard.togglesprint.asm;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.zekerzhayard.togglesprint.Configuration;
import net.minecraft.client.main.Main;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class ClassTweaker implements ITweaker {
    public static boolean DEV = false;
    private List<String> args = Lists.newArrayList();

    @Override()
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args.addAll(args);
        this.args.addAll(Lists.newArrayList("--gameDir", gameDir.getAbsolutePath(), "--assetsDir", assetsDir.getAbsolutePath(), "--version", profile));
        ClassTweaker.DEV = args.contains("--dev");
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
		return this.args.toArray(new String[0]);
	}
}