package com.mars.marsview.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesMannager {
	public static SharedPreferences mySharedPreferences;
	public static SharedPreferences.Editor mySharedPreferencesEditor;
	public static void SharedPreferences(Context mContext){
		 mySharedPreferences = mContext.getSharedPreferences("imp", 0);
		 mySharedPreferencesEditor = mySharedPreferences.edit();
	}
	/*
	 * key:¼üÖµ
	 * ValueType:	1 boolean
	 * 				2 int
	 * 				3 String
	 * 				4 float
	 * 				5 Long
	 */
	public static Object getSpValue(String key,int ValueType){
		switch (ValueType) {
		case 1:
			return mySharedPreferences.getBoolean(key, false);
		case 2:
			return mySharedPreferences.getInt(key, Integer.MIN_VALUE);
		case 3:
			return mySharedPreferences.getString(key,null);
		case 4:
			return mySharedPreferences.getFloat(key,Float.MIN_VALUE);
		case 5:
			return mySharedPreferences.getLong(key,Long.MIN_VALUE);
		default:
			return mySharedPreferences.getAll();
		}
	} 
	public static void putSpValue(String key,Object value,int ValueType){
		switch (ValueType) {
		case 1:
			mySharedPreferencesEditor.putBoolean(key,(boolean)value);
			break;
		case 2:
			mySharedPreferencesEditor.putInt(key,(int)value);
			break;
		case 3:
			mySharedPreferencesEditor.putString(key,(String)value);
			break;
		case 4:
			mySharedPreferencesEditor.putFloat(key,(float)value);
			break;
		case 5:
			mySharedPreferencesEditor.putLong(key,(long)value);
			break;
		}
		mySharedPreferencesEditor.commit();
	}
}	
