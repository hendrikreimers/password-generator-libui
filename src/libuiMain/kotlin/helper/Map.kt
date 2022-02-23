package helper

/**
 * Returns a key found by a value
 * you can use it like
 *      aMap.getKey("value")
 *   or
 *      aMap getKey "value" ('cause of infix)
 */
infix fun <K, V> Map<K, V>.getKey(filterVal: V): K = this.filter { filterVal == it.value }.keys.first()
