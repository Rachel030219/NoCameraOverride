/*
 * Copyright Rachel030219 2023.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package one.rachelt.nocameraoverride

import android.annotation.SuppressLint
import android.app.AndroidAppHelper
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    var identifierConfigCameraGesturePackage = 0
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName != "com.android.systemui") return
        XposedHelpers.findAndHookMethod("android.content.res.Resources", lpparam.classLoader, "getString", Int::class.java, object : XC_MethodHook() {
            @SuppressLint("DiscouragedApi")
            override fun beforeHookedMethod(param: MethodHookParam?) {
                // fetch the context and get resource identifier
                if (identifierConfigCameraGesturePackage == 0) {
                    identifierConfigCameraGesturePackage =
                        AndroidAppHelper.currentApplication().applicationContext.resources.getIdentifier(
                            "config_cameraGesturePackage",
                            "string",
                            "com.android.systemui"
                        )
                    Log.d("NCO","get identifier $identifierConfigCameraGesturePackage")
                } else {
                    // if the resource identifier matches the one we want to override, return the new value
                    if (param?.args?.get(0) == identifierConfigCameraGesturePackage) {
                        param.result = ""
                        Log.d("NCO","successfully hooked into ${param.thisObject?.javaClass?.name}")
                    }
                }
            }
        })
    }
}