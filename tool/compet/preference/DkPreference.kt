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
	protected val prefs: SharedPreferences

	constructor(context: Context, prefName: String) {
		this.prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
	}

	constructor(context: Context, prefName: String, prefMode: Int) {
		this.prefs = context.getSharedPreferences(prefName, prefMode)
	}

	operator fun contains(key: String): Boolean {
		return this.prefs.contains(key)
	}

	/**
	 * Call this to CRUD preference.
	 * Do NOT forget commit after call this.
	 */
	fun edit(): DkPreferenceEditor {
		return DkPreferenceEditor(this.prefs)
	}

	fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
		return if (contains(key)) getString(key).parseBooleanDk() else defaultValue
	}

	fun getInt(key: String, defautValue: Int = 0): Int {
		return if (contains(key)) getString(key).parseIntDk() else defautValue
	}

	fun getLong(key: String, defaultValue: Long = 0L): Long {
		return if (contains(key)) getString(key).parseLongDk() else defaultValue
	}

	fun getFloat(key: String, defaultValue: Float = 0f): Float {
		return if (contains(key)) getString(key).parseFloatDk() else defaultValue
	}

	fun getDouble(key: String, defaultValue: Double = 0.0): Double {
		return if (contains(key)) getString(key).parseDoubleDk() else defaultValue
	}

	/**
	 * We perform try/catch to archive back-compability (load other types will cause exception).
	 */
	fun getString(key: String, defaultValue: String? = null): String? {
		try {
			return prefs.getString(key, defaultValue)
		}
		catch (e: Exception) {
			DkLogcats.error(this, e)
		}
		return null
	}

	/**
	 * We perform try/catch to archive back-compability (load other types will cause exception).
	 */
	fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String>? {
		try {
			return prefs.getStringSet(key, defaultValue)
		}
		catch (e: Exception) {
			DkLogcats.error(this, e)
		}
		return defaultValue
	}

	fun <T> getJsonObject(key: String, resClass: Class<T>, defaultValue: T? = null): T? {
		return if (contains(key)) DkJsons.toObj(getString(key), resClass) else defaultValue
	}
}
