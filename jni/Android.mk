LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := rsapublickeyencrypt
LOCAL_SRC_FILES := BigInt.c des3.c
LOCAL_LDLIBS+= -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)
