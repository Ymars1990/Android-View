//
//  JLJBIGEnCoder.c
//  JLPay
//
//  Created by jielian on 16/7/26.
//  Copyright © 2016年 ShenzhenJielian. All rights reserved.
//

#include "JLJBIGEnCoder.h"

#include "jbig.h"
# include "com_mars_marsview_utils_Bmp2JbigMannger.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <jni.h>
#include <android/log.h>
#define LOG_TAG "cqEmbed"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
/* used for determining output file length */
unsigned long total_length = 0;
unsigned char* pbmJbigEncoded = NULL;

unsigned char* pbmTransferFromBmp(unsigned char* bitmapStr, size_t width, size_t height, size_t totalSize);
void jbigEncode(unsigned char* pbmStr, size_t width, size_t height);

JNIEXPORT jbyteArray JNICALL Java_com_mars_marsview_utils_Bmp2JbigMannger_getByteDataFromc(
		JNIEnv *env, jclass jclass, jbyteArray jba, jint jiw, jint jih,
		jintArray jll) {
	char* data = (char*) (*env)->GetByteArrayElements(env, jba, 0);

	LOGI("function exe 1");
	int bytekeylen = (*env)->GetArrayLength(env, jba);
	LOGI("function exe 2");
	LOGI("bytekeylen :%d", bytekeylen);
	LOGI("jih :%d", jih);
	LOGI("jiw :%d", jiw);
	long encodelen;
	jint carr[1];
//	carr = (*env)->GetIntArrayElements(env,jll,JNI_TRUE);
	LOGI("function exe 3");
	jbyte* dataByte = JLJBIGEncode(data, jiw, jih, bytekeylen, &encodelen);
	LOGI("function exe 4");
	jbyteArray jarrRV = (*env)->NewByteArray(env, encodelen);
	LOGI("function exe 5");
	(*env)->SetByteArrayRegion(env, jarrRV, 0, encodelen, dataByte);
	LOGI("function exe 6");
	carr[0] = encodelen;
	LOGI("encodelen :%d", encodelen);
//	(*env)->SetIntArrayElements(env, jll, carr, 0);
//	(*env)->SetIntArrayRegion(env, jll, 0, 1, carr);
	LOGI("function exe 7");
	return jarrRV;
}

/* 对 bmp 进行 jbig 编码
 1. bmp 转为 pbm
 2. 对 pbm 进行编码
 */
unsigned char* JLJBIGEncode(unsigned char* bitmapStr, size_t width,
		size_t height, size_t totalSize, size_t *encodedLen) {

	total_length = 0;
	pbmJbigEncoded = NULL;
	LOGI("JLJBIGEncode exe 1");
	unsigned char* pbmStr = pbmTransferFromBmp(bitmapStr, width, height,
			totalSize);
	LOGI("JLJBIGEncode exe 2");
	jbigEncode(pbmStr, width, height);
	LOGI("JLJBIGEncode exe 3");
	*encodedLen = total_length;
	LOGI("JLJBIGEncode exe 4");

	return pbmJbigEncoded;
}

static void data_out(unsigned char *start, size_t len, void *file) {
	total_length += len;
	if (pbmJbigEncoded == NULL) {
		pbmJbigEncoded = malloc(total_length);
	} else {
		pbmJbigEncoded = realloc(pbmJbigEncoded, total_length);
	}
	memcpy(pbmJbigEncoded + total_length - len, start, len);
	return;
}

/* bmp 转为 pbm */
unsigned char* pbmTransferFromBmp(unsigned char* bitmapStr, size_t width,
		size_t height, size_t totalSize) {
	LOGI("totalSize :%d", totalSize);
	LOGI("jih :%d", height);
	LOGI("jiw :%d", width);
	/* 保存转换后的 pbm 串 */
	unsigned char* pbmStr = (unsigned char*) malloc(totalSize / 4 / 8);
	LOGI("pbmTransferFromBmp exe 1");
	memset(pbmStr, 0x00, totalSize / 4 / 8);
	LOGI("pbmTransferFromBmp exe 2");
	/* 已转换的字节数 */
	long countTransed = 0;
	int h;
	int w;
	int i;
	for (h = 0; h < (int) height; h++) {
		unsigned char pbmChar = 0;
		// 每 4*8 个字节一取,转为 pbm 的一个字节
		for (w = 0; w < width * 4; w += 4 * 8) {
			pbmChar = 0;
			int start = w;
			// 将 8 个字节转换为 0|1 ，并填充到 pbm 的单字节
			for (i = 0; i < 8; i++) {
				unsigned char curBmpBitChar = *(bitmapStr + h * width * 4
						+ start + i * 4);
				int tmp =
						(curBmpBitChar & 0xc0/*0xc0~0xff*/) ?
								(0/*0xff*/) : (1/*0x00*/);
				if (tmp) {
					pbmChar = pbmChar | (0x80 >> i);
				}
			}
			memcpy(pbmStr + countTransed, &pbmChar, 1);
			countTransed++;
		}
	}
	LOGI("pbmTransferFromBmp exe 3");
	return pbmStr;
}


void jbigEncode(unsigned char* pbmStr, size_t width, size_t height) {
	struct jbg_enc_state s;
	LOGI("jbigEncode exe 1");
	jbg_enc_init(&s, width, height, 1, &pbmStr, data_out, NULL);
	LOGI("jbigEncode exe 2");
	jbg_enc_lrlmax(&s, 600, 300);
	LOGI("jbigEncode exe 3");
	jbg_enc_lrange(&s, -1, -1);
	LOGI("jbigEncode exe 4");
	int options = JBG_TPDON | JBG_TPBON | JBG_DPON;
	int order = JBG_ILEAVE | JBG_SMID;
	jbg_enc_options(&s, order, options, 0, -1, -1);
	LOGI("jbigEncode exe 5");
	jbg_enc_out(&s);
	LOGI("jbigEncode exe 6");
	jbg_enc_free(&s);
	LOGI("jbigEncode exe 7");
	free(pbmStr);
	LOGI("jbigEncode exe 8");
	return;
}
