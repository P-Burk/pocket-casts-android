package au.com.shiftyjelly.pocketcasts.ui.helper

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.edit
import au.com.shiftyjelly.pocketcasts.localization.BuildConfig
import au.com.shiftyjelly.pocketcasts.preferences.di.PublicSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import au.com.shiftyjelly.pocketcasts.images.R as IR
import au.com.shiftyjelly.pocketcasts.localization.R as LR

private const val PREFERENCE_APPICON = "pocketCastsAppIcon"

@Singleton
class AppIcon @Inject constructor(
    @ApplicationContext private val context: Context,
    @PublicSharedPreferences private val sharedPreferences: SharedPreferences
) {

    enum class AppIconType(val id: String, @StringRes val labelId: Int, @DrawableRes val settingsIcon: Int, val isPlus: Boolean, @DrawableRes val launcherIcon: Int, val aliasName: String) {
        DEFAULT(
            id = "default",
            labelId = LR.string.settings_app_icon_default,
            settingsIcon = IR.drawable.ic_appicon0,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher,
            aliasName = ".ui.MainActivity_0"
        ),
        DARK(
            id = "dark",
            labelId = LR.string.settings_app_icon_dark,
            settingsIcon = IR.drawable.ic_appicon1,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_1,
            aliasName = ".ui.MainActivity_1"
        ),
        ROUND_LIGHT(
            id = "roundedLight",
            labelId = LR.string.settings_app_icon_round_light,
            settingsIcon = IR.drawable.ic_appicon2,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_2,
            aliasName = ".ui.MainActivity_2"
        ),
        ROUND_DARK(
            id = "roundedDark",
            labelId = LR.string.settings_app_icon_round_dark,
            settingsIcon = IR.drawable.ic_appicon3,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_3,
            aliasName = ".ui.MainActivity_3"
        ),
        INDIGO(
            id = "indigo",
            labelId = LR.string.settings_app_icon_indigo,
            settingsIcon = IR.drawable.ic_appicon_indigo,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_indigo,
            aliasName = ".ui.MainActivity_9"
        ),
        ROSE(
            id = "rose",
            labelId = LR.string.settings_app_icon_rose,
            settingsIcon = IR.drawable.appicon_rose,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_rose,
            aliasName = ".ui.MainActivity_12"
        ),
        CAT(
            id = "cat",
            labelId = LR.string.settings_app_icon_pocket_cats,
            settingsIcon = IR.drawable.ic_appicon_pocket_cats,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_cat,
            aliasName = ".ui.MainActivity_10"
        ),
        REDVELVET(
            id = "redvelvet",
            labelId = LR.string.settings_app_icon_red_velvet,
            settingsIcon = IR.drawable.appicon_red_velvet,
            isPlus = false,
            launcherIcon = IR.mipmap.ic_launcher_redvelvet,
            aliasName = ".ui.MainActivity_11"
        ),
        PRIDE_2023(
            id = "pride_2023",
            labelId = LR.string.settings_app_icon_pride_2023,
            settingsIcon = IR.drawable.appicon_pride_2023,
            type = SubscriptionType.NONE,
            launcherIcon = IR.mipmap.ic_launcher_pride_2023,
            aliasName = ".ui.MainActivity_18"
        ),
        PLUS(
            id = "plus",
            labelId = LR.string.settings_app_icon_plus,
            settingsIcon = IR.drawable.ic_appicon4,
            isPlus = true,
            launcherIcon = IR.mipmap.ic_launcher_4,
            aliasName = ".ui.MainActivity_4"
        ),
        CLASSIC(
            id = "classic",
            labelId = LR.string.settings_app_icon_classic,
            settingsIcon = IR.drawable.ic_appicon5,
            isPlus = true,
            launcherIcon = IR.mipmap.ic_launcher_5,
            aliasName = ".ui.MainActivity_5"
        ),
        ELECTRIC_BLUE(
            id = "electricBlue",
            labelId = LR.string.settings_app_icon_electric_blue,
            settingsIcon = IR.drawable.ic_appicon6,
            isPlus = true,
            launcherIcon = IR.mipmap.ic_launcher_6,
            aliasName = ".ui.MainActivity_6"
        ),
        ELECTRIC_PINK(
            id = "electricPink",
            labelId = LR.string.settings_app_icon_electric_pink,
            settingsIcon = IR.drawable.ic_appicon7,
            isPlus = true,
            launcherIcon = IR.mipmap.ic_launcher_7,
            aliasName = ".ui.MainActivity_7"
        ),
        RADIOACTIVE(
            id = "radioactive",
            labelId = LR.string.settings_app_icon_radioactivity,
            settingsIcon = IR.drawable.appicon_radioactive,
            isPlus = true,
            launcherIcon = IR.mipmap.ic_launcher_radioactive,
            aliasName = ".ui.MainActivity_8"
        ),
        HALLOWEEN(
            id = "halloween",
            labelId = LR.string.settings_app_icon_halloween,
            settingsIcon = IR.drawable.appicon_halloween,
            isPlus = true,
            launcherIcon = IR.mipmap.ic_launcher_halloween,
            aliasName = ".ui.MainActivity_13"
        );

        companion object {
            fun fromString(value: String, default: AppIconType = PRIDE_2023): AppIconType {
                return AppIconType.values().find { it.id == value } ?: default
            }
        }
    }

    var activeAppIcon: AppIconType = getAppIconFromPreferences()
        set(value) {
            field = value
            setAppIconToPreferences(value)
        }

    val allAppIconTypes = AppIconType.values()

    private fun getAppIconFromPreferences(): AppIconType {
        val appIconId: String = sharedPreferences.getString(PREFERENCE_APPICON, AppIconType.PRIDE_2023.id) ?: AppIconType.PRIDE_2023.id
        return AppIconType.fromString(appIconId, AppIconType.PRIDE_2023)
    }

    private fun setAppIconToPreferences(appIconType: AppIconType) {
        sharedPreferences.edit {
            putString(PREFERENCE_APPICON, appIconType.id)
        }
    }

    fun enableSelectedAlias(selectedIconType: AppIconType) {
        val componentPackage = if (BuildConfig.DEBUG) "au.com.shiftyjelly.pocketcasts.debug" else "au.com.shiftyjelly.pocketcasts"
        val classPath = "au.com.shiftyjelly.pocketcasts"
        AppIconType.values().forEach { iconType ->
            val componentName = ComponentName(componentPackage, "$classPath${iconType.aliasName}")
            // If we are using the default icon we just switch every alias off
            val enabledFlag = if (selectedIconType == iconType && selectedIconType != AppIconType.PRIDE_2023) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            context.packageManager.setComponentEnabledSetting(componentName, enabledFlag, PackageManager.DONT_KILL_APP)
        }
    }
}
