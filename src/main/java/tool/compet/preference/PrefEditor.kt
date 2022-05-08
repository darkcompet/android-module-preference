package tool.compet.preference

import android.content.SharedPreferences
import tool.compet.json.DkJsons

class PrefEditor(private var prefs: SharedPreferences) {
	private val editor = this.prefs.edit()

	fun putBoolean(key: String, value: Boolean): PrefEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putInt(key: String, value: Int): PrefEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putLong(key: String, value: Long): PrefEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putFloat(key: String, value: Float): PrefEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putDouble(key: String, value: Double): PrefEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putString(key: String, value: String?): PrefEditor {
		this.editor.putString(key, value)
		return this
	}

	fun putStringSet(key: String, values: Set<String?>?): PrefEditor {
		this.editor.putStringSet(key, values)
		return this
	}

	fun putJsonObject(key: String, value: Any?): PrefEditor {
		return putString(key, DkJsons.obj2json(value))
	}

	//
	// CRUD
	//

	// Remove entry at given key.
	fun removeKey(key: String): PrefEditor {
		this.editor.remove(key)
		return this
	}

	// Remove all entries inside this pref.
	fun clear(): PrefEditor {
		this.editor.clear()
		return this
	}

	// Commit to disk at background thread.
	fun apply() {
		this.editor.apply()
	}

	// Commit to disk synchronously.
	fun commit() {
		this.editor.commit()
	}
}
