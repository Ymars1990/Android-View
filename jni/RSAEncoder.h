//
//  RSAEncoder.h
//  JLPay
//
//  Created by jielian on 16/8/10.
//  Copyright © 2016年 ShenzhenJielian. All rights reserved.
//

#import <Foundation/Foundation.h>




@interface RSAEncoder : NSObject

+ (NSString*) encodingPubkey:(NSString*)pubkey;


+ (NSString*) pinSourceOfPubdata;



@end
