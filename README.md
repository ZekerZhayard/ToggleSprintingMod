# ToggleSprintingMod
用于 Minecraft 1.13.2 的强制疾跑模组

## 安装方法
### 基于Rift
1. 安装 Rift ：https://github.com/DimensionalDevelopment/Rift
2. 下载 [RiftTweakLoader](https://github.com/xfl03/RiftTweakLoader) 并放入 `.minecraft\mods` 文件夹
3. 把 ToggleSprintingMod 放入 `.minecraft\mods` 文件夹
4. 选择带有Rift的版本并启动游戏

### 基于原版
1. 在 `.minecraft\versions` 文件夹下新建一个名为 `1.13.2-ToggleSprinting-1.0-RC2` 的文件夹
2. 在 `.minecraft\versions\1.13.2-ToggleSprinting-1.0-RC2` 文件夹中新建一个名为 `1.13.2-ToggleSprinting-1.0-RC2.json` 的文件
3. 在 `1.13.2-ToggleSprinting-1.0-RC2.json` 中写入以下内容：  
```
{
    "id": "1.13.2-ToggleSprinting-1.0-RC2",
    "inheritsFrom": "1.13.2",
    "time": "1970-01-01T00:00:00+08:00",
    "releaseTime": "1970-01-01T00:00:00+08:00",
    "type": "release",
    "arguments": {
        "game": [
            "--tweakClass",
            "io.github.zekerzhayard.togglesprint.asm.ClassTweaker"
        ]
    },
    "mainClass": "net.minecraft.launchwrapper.Launch",
    "libraries": [
        {
            "name": "org.ow2.asm:asm:6.2",
            "url": "http://repo1.maven.org/maven2/"
        },
        {
            "name": "org.ow2.asm:asm-commons:6.2",
            "url": "http://repo1.maven.org/maven2/"
        },
        {
            "name": "org.ow2.asm:asm-tree:6.2",
            "url": "http://repo1.maven.org/maven2/"
        },
        {
            "name": "net.minecraft:launchwrapper:1.12",
            "url": "https://libraries.minecraft.net/"
        },
        {
            "name": "togglesprinting:ToggleSprintingMod_MC1.13.2:1.0-RC2"
        }
    ]
}
```
4. 在 `.minecraft\libraries` 文件夹中新建以下路径
```
togglesprinting\ToggleSprintingMod_MC1.13.2\1.0-RC2
```
5. 下载 ToggleSprintingMod ，并重命名为 `ToggleSprintingMod_MC1.13.2-1.0-RC2.jar`
6. 把 `ToggleSprintingMod_MC1.13.2-1.0-RC2.jar` 放入 `.minecraft\libraries、togglesprinting\ToggleSprintingMod_MC1.13.2\1.0-RC2` 文件夹中
7. 选择带有 `1.13.2-ToggleSprintingMod-1.0-RC2` 的版本并启动游戏


## 模组设置
- 位于游戏中的 `Options - Controls - ToggleSprinting Settings` 中