#ifndef __DES_H_
#define __DES_H_

#define UCHAR   unsigned char
#define UINT    unsigned int
#define ULONG   unsigned long

#define uchar   unsigned char
#define uint    unsigned int
#define ulong   unsigned long

#define SUCCESS 0
#define FAILURE -1
void DebugBuff(char *Title,unsigned char *Buff,int Len);
void des(uchar *binput, uchar *boutput, uchar *bkey);
void desm1(uchar *binput, uchar *boutput, uchar *bkey);
int EnTarck2(uchar *MagData,uchar MagDataLen,uchar *Out,uchar *TheKey);
int UnTarck2(uchar *MagData,uchar MagDataLen,uchar *Out,uchar *TheKey);
int EnANSIX98(uchar *TheKey,char *CardNo,int CardNoLen,char *Pin,char PinLen,uchar *EnCryData);
int UnANSIX98(uchar *TheKey,char *CardNo,int CardNoLen,char *Pin,char *PinLen,uchar *EnCryData);
void GetTMK(char *TK,char *ZmkTkKey,char *ZmkLmkKey);
void GetPIK(char *TmkLmk,char *PikTmkKey,char *PikLmkKey,char *CheckValue);
void GetMAK(char *Tmk,char *MakTmkKey,char *MakKey,char *CheckValue);
void UnMAK16(char *Zmk,char *MakZmkKey,char *MakKey,char *CheckValue);
int ANSI_X919(uchar *MACKey,uchar *InData,int InDataLen,uchar *MAC);
void CBCDesEncrypt(uchar *Key,int KeyLen,uchar *s,int slen,uchar*o);
void CBCDesDecrypt(uchar *Key,int KeyLen,uchar *s,int slen,uchar*o);
char* RSAPublicEncrypt(char *InData,char *OutData);
void RSAPrivateDecrypte(char *InData,char *OutData);
#endif
