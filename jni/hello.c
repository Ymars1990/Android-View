# include<stdio.h>
# include<stdlib.h>
#include "com_mars_marsview_utils_NdkTest.h"
JNIEXPORT jstring JNICALL Java_com_mars_marsview_utils_NdkTest_getStringFromc
  (JNIEnv *env, jclass jclazz){

	return (*env)->NewStringUTF(env,"NDK Test");
}
