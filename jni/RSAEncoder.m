//
//  RSAEncoder.m
//  JLPay
//
//  Created by jielian on 16/8/10.
//  Copyright © 2016年 ShenzhenJielian. All rights reserved.
//

#import "RSAEncoder.h"
#import "ISOHelper.h"
#include "BigInt.h"


static NSString* const pubKeyData = @"00023667E139EF47B44773481B7636E53C2A2B767DCF28BCED78F6AF7F66DF213356FB9B97229154FC779DA69D144794A8F11B41EBA06366AADBE74BE9FD2693446A2F7E3FF1672848F245956FCB5C17C3BC73C9648358B44DA475128A9A04646500301C0410EF2AE9F834BFCDD5260B974A70AD1A4A0408082A89D69EC7E692";


@implementation RSAEncoder

+ (NSString *)encodingPubkey:(NSString *)pubkey {
    
    return [self encryptedStringByPubKey:pubkey];
}

+ (NSString *)pinSourceOfPubdata {
    NSString* temp = pubKeyData;
    /* 00 */
    temp = [temp substringFromIndex:2];
    /* BT-2位 */
    temp = [temp substringFromIndex:2];
    /* PS-随机位 */
    NSRange zeroRange = [temp rangeOfString:@"00"];
    while (zeroRange.location % 2 != 0) {
        zeroRange.location += 1;
        temp = [temp substringFromIndex:zeroRange.location];
        zeroRange = [temp rangeOfString:@"00"];
    }
    temp = [temp substringFromIndex:zeroRange.location];
    /* 00 */
    temp = [temp substringFromIndex:2];
    /* D-30+totalLen+04+len+pinSource+0408+IV */
    temp = [temp substringFromIndex:6];
    NSString* len = [temp substringToIndex:2];
    temp = [temp substringFromIndex:2];
    return [temp substringToIndex:[ISOHelper lenOfTwoBytesHexString:len] * 2];
}




+ (NSString*) encryptedStringByPubKey:(NSString*)pubkey {
    char* inData = (char*)[pubKeyData UTF8String];
    char ee[20]="10001";
    char* outData = (char*)malloc(512 + 1);
    memset(outData, 0x00, 512 + 1);
    
    SBigInt P,Q,N,D,E,mi,ci,oi,T;
    SBigIntInit(&P);
    SBigIntInit(&Q);
    SBigIntInit(&N);
    SBigIntInit(&D);
    SBigIntInit(&E);
    SBigIntInit(&mi);
    SBigIntInit(&ci);
    SBigIntInit(&oi);
    char stout[512];
    
    
    Get(&N, (char*)[pubkey UTF8String]);
    Put(&N,stout);
    Get(&E,ee);
    Put(&E,stout);
    
    Get(&mi,inData);
    Put(&mi,stout);
    
    Mov(&ci,RsaTrans(&mi,&E,&N,&T));
    Put(&ci,outData);

    
    NSString* encryptedString = [NSString stringWithUTF8String:outData];
    free(outData);
    return encryptedString;
}






@end
