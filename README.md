# [WIP] [Xposed] NoCameraOverride

A module to prevent Android SystemUI from forcing the camera gesture to launch a specific camera app.

**STILL UNDER DEVELOPMENT** but works correctly as far as I can tell on my Pixel 6 Pro (raven). USE AT YOUR OWN RISK. I am not responsible for any damage to your device.

## Usage

Install the module and reboot. That's it.

You can modify the file [MainHook.kt#L39](https://github.com/Rachel030219/NoCameraOverride/tree/master/app/src/main/java/com/rachel/NoCameraOverride/MainHook.kt#L39) to change the package name of the camera app you want to use, or leave it empty so that you can choose one from the camera apps on device.

## How it works

> TO BE FINISHED