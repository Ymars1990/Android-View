//
//  JLJBIGEnCoder.h
//  JLPay
//
//  Created by jielian on 16/7/26.
//  Copyright © 2016年 ShenzhenJielian. All rights reserved.
//

#ifndef JLJBIGEnCoder_h
#define JLJBIGEnCoder_h

#include <stdio.h>


/*
 bitmap 图片进行 jbig 编码(先转bmp->pbm); 并输出编码后的字符串
 @param bitmapStr: bitmap 图片数据(不包含头部信息)
 @param width: 像素的宽度
 @param height: 像素的高度
 @param totalSize: totalSize 数据长度
 @param encodedLen: 编码后的总长度(输出参数)
 
 @注意: 在使用完编码后的字符串后,要 free 掉
 */

unsigned char* JLJBIGEncode(unsigned char* bitmapStr, size_t width, size_t height, size_t totalSize, size_t* encodedLen);



#endif /* JLJBIGEnCoder_h */
