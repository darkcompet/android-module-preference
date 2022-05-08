/*
 * Copyright (c) 2017-2020 DarkCompet. All rights reserved.
 */
package tool.compet.preference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import tool.compet.core.DkLogcats
import tool.compet.core.*
import tool.compet.json.DkJsons

/**
 * App-specific access. An app cannot access to preferences of other apps.
 *
 * This works as memory-cache (after first time of retriving data from system file).
 * For back compability, this stores all value as `String` or `Set of String` since if we store with
 * other types (int, double...) then we maybe get an exception when load them with other type.
 *
 * By default, it does support for storing Json object.
 */
@SuppressLint("ApplySharedPref")
open class DkSharedPreference {
	protected val prefs: SharedPreferences
	protected val editor: SharedPreferences.Editor

	constructor(context: Context, prefName: String) {
		this.prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
		this.editor = this.prefs.edit()
	}

	constructor(context: Context, prefName: String, prefMode: Int) {
		this.prefs = context.getSharedPreferences(prefName, prefMode)
		this.editor = this.prefs.edit()
	}

	operator fun contains(key: String): Boolean {
		return this.prefs.contains(key)
	}

	//
	// Integer
	//
	fun putInt(key: String, value: Int) : DkSharedPreference {
		this.editor.putString(key, value.toString())
		return this
	}

	fun getInt(key: String): Int {
		return getString(key).parseIntDk()
	}

	fun getInt(key: String, defautValue: Int): Int {
		return if (contains(key)) getString(key).parseIntDk() else defautValue
	}

	//
	// Float
	//
	fun putFloat(key: String, value: Float) : DkSharedPreference {
		this.editor.putString(key, value.toString())
		return this
	}

	fun getFloat(key: String): Float {
		return getString(key).parseFloatDk()
	}

	fun getFloatOrDefault(key: String, defaultValue: Float): Float {
		return if (contains(key)) getString(key).parseFloatDk() else defaultValue
	}

	//
	// Double
	//
	fun putDouble(key: String, value: Double) : DkSharedPreference {
		this.editor.putString(key, value.toString())
		return this
	}

	fun getDouble(key: String): Double {
		return getString(key).parseDoubleDk()
	}

	fun getDoubleOrDefault(key: String, defaultValue: Double): Double {
		return if (contains(key)) getString(key).parseDoubleDk() else defaultValue
	}

	//
	// Boolean
	//
	fun putBoolean(key: String, value: Boolean) : DkSharedPreference {
		this.editor.putString(key, value.toString())
		return this
	}

	fun getBoolean(key: String): Boolean {
		return getString(key).parseBooleanDk()
	}

	fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean {
		return if (contains(key)) getString(key).parseBooleanDk() else defaultValue
	}

	//
	// Long
	//
	fun putLong(key: String, value: Long) : DkSharedPreference {
		this.editor.putString(key, value.toString())
		return this
	}

	fun getLong(key: String): Long {
		return getString(key).parseLongDk()
	}

	fun getLongOrDefault(key: String, defaultValue: Long): Long {
		return if (contains(key)) getString(key).parseLongDk() else defaultValue
	}

	//
	// String
	//
	fun putString(key: String, value: String?) : DkSharedPreference {
		this.editor.putString(key, value)
		return this
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
	 * We perform try/catch to archive back-compability (load other types will cause exception).
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

	//
	// String set
	//
	fun putStringSet(key: String, values: Set<String?>?) : DkSharedPreference {
		this.editor.putStringSet(key, values)
		return this
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

	//
	// Json object
	//
	fun putJsonObject(key: String, value: Any?) : DkSharedPreference {
		return putString(key, DkJsons.obj2json(value))
	}

	fun <T> getJsonObject(key: String, resClass: Class<T>): T? {
		return DkJsons.json2obj(getString(key), resClass)
	}

	fun <T> getJsonObject(key: String, resClass: Class<T>, defaultValue: T?): T? {
		return if (contains(key)) DkJsons.json2obj(getString(key), resClass) else defaultValue
	}

	//
	// CRUD
	//

	// Remove entry at given key.
	fun removeKey(key: String) : DkSharedPreference {
		this.editor.remove(key)
		return this
	}

	// Remove all entries inside this pref.
	fun clear() : DkSharedPreference {
		this.editor.clear()
		return this
	}

	// Commit to disk at background thread.
	fun commitAsync() {
		this.editor.apply()
	}

	// Commit to disk synchronously.
	fun commitNow() {
		this.editor.commit()
	}
}
