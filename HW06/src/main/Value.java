package main;


public interface Value<T>
{
	/**
	 * Used to extract value from an object
	 * @return value extracted
	 */
	public T getValue();
	/**
	 * Used to set a value into an object
	 * @param value new value for object
	 */
	public void setValue(T value);
	/**
	 * Type of object as a string
	 * @return label of the object possible labels are currently
	 * IDENTIFIER and NUMBER
	 */
	public String getType();
}
