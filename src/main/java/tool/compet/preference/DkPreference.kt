/*
 * Copyright (c) 2017-2020 DarkCompet. All rights reserved.
 */
package tool.compet.preference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import tool.compet.core.DkLogcats
import tool.compet.core.parseBooleanDk
import tool.compet.core.parseDoubleDk
import tool.compet.core.parseFloatDk
import tool.compet.core.parseIntDk
import tool.compet.core.parseLongDk
import tool.compet.json.DkJsons

/**
 * App-specific access. An app cannot access to preferences of other apps.
 *
 * This works as memory-cache (after first time of retriving data from system file).
 * For back compability, this stores all value as `String` or `Set of String` since if we store with
 * other types (int, double...) then we maybe get an exception when load them with other type.
 *
 * It also support for storing Json object.
 */
@SuppressLint("ApplySharedPref")
open class DkPreference {
	val prefs: SharedPreferences

	constructor(context: Context, prefName: String) {
		this.prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
	}

	constructor(context: Context, prefName: String, prefMode: Int) {
		this.prefs = context.getSharedPreferences(prefName, prefMode)
	}

	operator fun contains(key: String): Boolean {
		return this.prefs.contains(key)
	}

	fun edit(): DkPreferenceEditor {
		return DkPreferenceEditor(this.prefs)
	}

	fun getBoolean(key: String): Boolean {
		return getString(key).parseBooleanDk()
	}

	fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean {
		return if (contains(key)) getString(key).parseBooleanDk() else defaultValue
	}

	fun getInt(key: String): Int {
		return getString(key).parseIntDk()
	}

	fun getIntOrDefault(key: String, defautValue: Int): Int {
		return if (contains(key)) getString(key).parseIntDk() else defautValue
	}

	fun getLong(key: String): Long {
		return getString(key).parseLongDk()
	}

	fun getLongOrDefault(key: String, defaultValue: Long): Long {
		return if (contains(key)) getString(key).parseLongDk() else defaultValue
	}

	fun getFloat(key: String): Float {
		return getString(key).parseFloatDk()
	}

	fun getFloatOrDefault(key: String, defaultValue: Float): Float {
		return if (contains(key)) getString(key).parseFloatDk() else defaultValue
	}

	fun getDouble(key: String): Double {
		return getString(key).parseDoubleDk()
	}

	fun getDoubleOrDefault(key: String, defaultValue: Double): Double {
		return if (contains(key)) getString(key).parseDoubleDk() else defaultValue
	}

	/**
	 * We perform try/catch to archive back-compability (load other types will cause exception).
	 */
	fun getString(key: String): String? {
		try {
			return prefs.getString(key, null)
		}
		catch (e: Exception) {
			DkLogcats.error(this, e)
		}
		return null
	}

	/**
	 * We perform try/catch to archive back-compability (since get another types will cause exception).
	 */
	fun getStringOrDefault(key: String, defaultValue: String?): String? {
		try {
			return prefs.getString(key, defaultValue)
		}
		catch (e: Exception) {
			DkLogcats.error(this, e)
		}
		return defaultValue
	}

	/**
	 * We perform try/catch to archive back-compability (load other types will cause exception).
	 */
	fun getStringSet(key: String): Set<String>? {
		try {
			return prefs.getStringSet(key, null)
		}
		catch (e: Exception) {
			DkLogcats.error(this, e)
		}
		return null
	}

	/**
	 * We perform try/catch to archive back-compability (load other types will cause exception).
	 */
	fun getStringSetOrDefault(key: String, defaultValue: Set<String>?): Set<String>? {
		try {
			return prefs.getStringSet(key, defaultValue)
		}
		catch (e: Exception) {
			DkLogcats.error(this, e)
		}
		return defaultValue
	}

	fun <T> getJsonObject(key: String, resClass: Class<T>): T? {
		return DkJsons.json2obj(getString(key), resClass)
	}

	fun <T> getJsonObjectOrDefault(key: String, resClass: Class<T>, defaultValue: T?): T? {
		return if (contains(key)) DkJsons.json2obj(getString(key), resClass) else defaultValue
	}
}
