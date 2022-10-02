package tool.compet.preference

import android.content.SharedPreferences
import tool.compet.json.DkJsons

class DkPreferenceEditor(private var prefs: SharedPreferences) {
	private val editor = this.prefs.edit()

	fun putBoolean(key: String, value: Boolean): DkPreferenceEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putInt(key: String, value: Int): DkPreferenceEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putLong(key: String, value: Long): DkPreferenceEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putFloat(key: String, value: Float): DkPreferenceEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putDouble(key: String, value: Double): DkPreferenceEditor {
		this.editor.putString(key, value.toString())
		return this
	}

	fun putString(key: String, value: String?): DkPreferenceEditor {
		this.editor.putString(key, value)
		return this
	}

	fun putStringSet(key: String, values: Set<String?>?): DkPreferenceEditor {
		this.editor.putStringSet(key, values)
		return this
	}

	fun putJsonObject(key: String, value: Any?): DkPreferenceEditor {
		return putString(key, DkJsons.toJson(value))
	}

	/**
	 * Remove entry at given key.
	 */
	fun remove(key: String): DkPreferenceEditor {
		this.editor.remove(key)
		return this
	}

	/**
	 * Remove all entries inside this pref.
 	 */
	fun clear(): DkPreferenceEditor {
		this.editor.clear()
		return this
	}

	/**
	 * Asynchronously commit to disk (at background thread).
	 */
	fun commitAsync() {
		this.editor.apply()
	}

	/**
	 * Synchronously commit to disk (at current thread).
	 */
	fun commitNow() {
		this.editor.commit()
	}
}
