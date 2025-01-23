package com.miguelruivo.flutter.plugin.countrycodes.country_codes

import android.R
import android.os.Build
import java.util.Locale
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
//import io.flutter.plugin.common.PluginRegistry.Registrar

/** CountryCodesPlugin */
public class CountryCodesPlugin: FlutterPlugin, MethodCallHandler {
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "country_codes")
    channel.setMethodCallHandler(CountryCodesPlugin());
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
//  companion object {
//    @JvmStatic
//    fun registerWith(registrar: Registrar) {
//      val channel = MethodChannel(registrar.messenger(), "country_codes")
//      channel.setMethodCallHandler(CountryCodesPlugin())
//    }
//  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {

    when (call.method) {
        "getLocale" -> result.success(listOf(Locale.getDefault().language, Locale.getDefault().country, getLocalizedCountryNames(call.arguments as String?)))
        "getRegion" -> result.success(Locale.getDefault().country)
        "getLanguage" -> result.success(Locale.getDefault().language)
        else -> result.notImplemented()
    }
  }


  private fun getLocalizedCountryNames(localeTag: String?) : HashMap<String, String> {
    var localizedCountries: HashMap<String,String> = HashMap()

    val deviceCountry: String = Locale.getDefault().toLanguageTag();

    for (countryCode in Locale.getISOCountries()) {
      val locale = Locale(localeTag ?: deviceCountry,countryCode)
      var countryName: String? = locale.getDisplayCountry(Locale.forLanguageTag(localeTag ?: deviceCountry))
      localizedCountries[countryCode.toUpperCase()] = countryName ?: "";
    }
    return localizedCountries
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
  }
}
