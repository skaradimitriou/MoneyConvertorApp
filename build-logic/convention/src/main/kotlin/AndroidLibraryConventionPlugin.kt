import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("com.jetbrains.kotlin.android")
            }
//            extensions.configure<LibraryExtension> {
//
//            }
        }
    }
}