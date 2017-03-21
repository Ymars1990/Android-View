#include <stdio.h>
#include <stdlib.h>
#include "des3.h"
#include <time.h>
#include <sys/time.h>
#include "BigInt.h"
# include "com_mars_marsview_utils_RSAEncrypt.h"
#define UCHAR   unsigned char
#define UINT    unsigned int
#define ULONG   unsigned long

#define uchar   unsigned char
#define uint    unsigned int
#define ulong   unsigned long

uchar res_codage[8];
uchar res_decodage[8];

unsigned char Key[64];
unsigned char input[64];
unsigned char output[64];
unsigned char Kn[16][48];

char LMK[16] = {
		"\x33\x34\x36\x32\x33\x33\x33\x33\x31\x39\x38\x33\x30\x36\x30\x35" };

/*----------------------------------------------------------------

 TABLES DE CHIFFREMENT

 -----------------------------------------------------------------*/

/* table 1 : permutation initiale */
const unsigned char T1[] = { 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27,
		19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7,
		56, 48, 40, 32, 24, 16, 8, 0, 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44,
		36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6 };

/* table 2 : permutation finale */
const unsigned char T2[] = { 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54,
		22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60,
		28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1,
		41, 9, 49, 17, 57, 25, 32, 0, 40, 8, 48, 16, 56, 24 };

/* table 3 : fonction d'expansion E  ( valeur - 1 ) */
const unsigned char T3[] = { 31, 0, 1, 2, 3, 4, 3, 4, 5, 6, 7, 8, 7, 8, 9, 10,
		11, 12, 11, 12, 13, 14, 15, 16, 15, 16, 17, 18, 19, 20, 19, 20, 21, 22,
		23, 24, 23, 24, 25, 26, 27, 28, 27, 28, 29, 30, 31, 0 };

/* table 5 : fonction de permutation P */
const unsigned char T5[] = { 15, 6, 19, 20, 28, 11, 27, 16, 0, 14, 22, 25, 4,
		17, 30, 9, 1, 7, 23, 13, 31, 26, 2, 8, 18, 12, 29, 5, 21, 10, 3, 24 };

/* table 7 : table de choix 1 */
const unsigned char T7_1_2[56] = { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33,
		25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35,

		62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52,
		44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };

/* table 8 : table de d‚calage */
//  code unsigned char T8[16] =
const unsigned char T8[] = { 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 };

/* table 9 : table de choix 2 */
const unsigned char T9[] = { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18,
		11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50,
		44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };

/* table 6 : s‚lection de fonctions S1 ?S8 */
//  code unsigned char T6[8][64] =
const unsigned char T6[][64] = {
/* S1 */
{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13,
		1, 10, 6, 12, 11, 9, 5, 3, 8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7,
		3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 },

/* S2 */
{ 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8,
		14, 12, 0, 1, 10, 6, 9, 11, 5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6,
		9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 },

/* S3 */
{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6,
		10, 2, 8, 5, 14, 12, 11, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12,
		5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 },

/* S4 */
{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0,
		3, 4, 7, 2, 12, 1, 10, 14, 9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14,
		5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 },

/* S5 */
{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13,
		1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6,
		3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 },

/* S6 */
{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9,
		5, 6, 1, 13, 14, 0, 11, 3, 8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1,
		13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 },

/* S7 */
{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1,
		10, 14, 3, 5, 12, 2, 15, 8, 6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8,
		0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 },

/* S8 */
{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7,
		4, 12, 5, 6, 11, 0, 14, 9, 2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13,
		15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };

/* Table pour eclatement des valeurs precedentes */
//code unsigned char TE[16][4] =
const unsigned char TE[][4] = { { 0, 0, 0, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 0 },
		{ 0, 0, 1, 1 }, { 0, 1, 0, 0 }, { 0, 1, 0, 1 }, { 0, 1, 1, 0 }, { 0, 1,
				1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 1 }, { 1, 0, 1, 0 }, { 1, 0,
				1, 1 }, { 1, 1, 0, 0 }, { 1, 1, 0, 1 }, { 1, 1, 1, 0 }, { 1, 1,
				1, 1 } };

/*---------------------------------------------------------------
 CLES
 ---------------------------------------------------------------*/
/*----------------------------------------------------------------

 Figure 3. Key Schedule Calculation.

 -----------------------------------------------------------------*/
void Ks(uchar *Key, uchar Kn[16][48]) {
	uchar cd[56];

	uchar zt[60];

	int n;
	unsigned char tmp11, tmp12, tmp21, tmp22;
	int i;
	unsigned char *Knn;

	/* choix 1 */
	for (i = 0; i < 56; i++) {
		cd[i] = Key[T7_1_2[i]];
	}

	for (n = 0; n < 16; n++) {
		/* rotation ?gauche du vecteur en fonction de l'indice */
		if (T8[n] == 0) {
			tmp11 = cd[0];
			tmp21 = cd[28];
			memcpy(zt, &cd[1], 55);
			memcpy(cd, zt, 55);
			//   memmove(&cd[0], &cd[1], 55); /* ce qui est en 1 va en 0 */
			cd[27] = tmp11;
			cd[55] = tmp21;
		} else {
			tmp11 = cd[0];
			tmp12 = cd[1];
			tmp21 = cd[28];
			tmp22 = cd[29];

			memcpy(zt, &cd[2], 54);
			memcpy(cd, zt, 54);

			//			memmove(&cd[0], &cd[2], 54); /* ce qui est en 2 va en 0 */

			cd[26] = tmp11;
			cd[27] = tmp12;
			cd[54] = tmp21;
			cd[55] = tmp22;
		}
		/* choix 2 */
		Knn = Kn[n];
		for (i = 0; i < 48; i++) {
			Knn[i] = cd[T9[i]];
		}
	}
}

/*----------------------------------------------------------------

 Figure 2. Calculation of f(R, K)

 -----------------------------------------------------------------*/
void fonction(uchar *Knn, uchar *r, uchar *s) {
	/* n est l'indice de 1 a 16 pour choisir la cle
	 r est R.
	 s est le r‚sultat. */

	unsigned char x[32];
	unsigned int *px;
	int i, l;
	unsigned char c;
	unsigned char t;

	/* fonction E *//*  + OU exclusif */
	/* S‚lection Sn */
	for (i = 0, l = 0, px = (unsigned int *) x; i < 8;) {
		c = 32 * (r[T3[l]] ^ Knn[l]);
		l++;
		c += 8 * (r[T3[l]] ^ Knn[l]);
		l++;
		c += 4 * (r[T3[l]] ^ Knn[l]);
		l++;
		c += 2 * (r[T3[l]] ^ Knn[l]);
		l++;
		c += 1 * (r[T3[l]] ^ Knn[l]);
		l++;
		c += 16 * (r[T3[l]] ^ Knn[l]);
		l++;
		/* extraction de la valeur */
		t = T6[i][c];
		i++;
		/* Eclatement de la valeur; 4 bits -> 4 bytes */
//		*px = *(long *)TE[t];
		memcpy(px, TE[t], sizeof(unsigned int));
		px++;
	}
	/* fonction P */
	for (i = 0; i < 32; i++) {
		s[i] = x[T5[i]];
	}
}

/*----------------------------------------------------------------
 Permutations initiale et finale
 -----------------------------------------------------------------*/
void permutation(uchar *org, const uchar *tab)
//unsigned char *org;
//unsigned char *tab;
{
	unsigned char tmp[64];
	int i;

	memcpy(tmp, org, 64);
	for (i = 0; i < 64; i++) {
		org[i] = tmp[tab[i]];
	}
}
/*----------------------------------------------------------------

 Figure 1. Enciphering Computation.

 -----------------------------------------------------------------*/
void chiffrement(uchar *xi, uchar *xo, unsigned char Kn[16][48])
//unsigned char *xi;
//unsigned char *xo;
//unsigned char Kn[16][48];
{
	unsigned char r[32], l[32];
	unsigned char rp[32], lp[32];

	int i;
	int n;

	memcpy(l, &xi[0], 32);
	memcpy(r, &xi[32], 32);

	for (n = 0; n < 16; n++) {
		memcpy(lp, r, 32);

		fonction(Kn[n], r, rp);
		for (i = 0; i < 32; i++) {
			r[i] = ((l[i]) ^ (rp[i]));
		}
		memcpy(l, lp, 32);
	}
	memcpy(&xo[0], r, 32);
	memcpy(&xo[32], l, 32);

}

/*----------------------------------------------------------------

 Deciphering Computation.

 -----------------------------------------------------------------*/

void dechiffrement(uchar *xi, uchar *xo, unsigned char Kn[16][48])
//unsigned char *xi;
//unsigned char *xo;
//unsigned char Kn[16][48];
{
	unsigned char r[32], l[32], rp[32], lp[32];

	int i;
	int n;

	memcpy(l, &xi[0], 32);
	memcpy(r, &xi[32], 32);

	for (n = 0; n < 16; n++) {
		memcpy(lp, r, 32);
		fonction(Kn[15 - n], r, rp);
		for (i = 0; i < 32; i++) {
			r[i] = ((l[i]) ^ (rp[i]));
		}
		memcpy(l, lp, 32);
	}

	memcpy(&xo[0], r, 32);
	memcpy(&xo[32], l, 32);
}

/*----------------------------------------------------------------
 Eclater 64 bits en 64 octets
 -----------------------------------------------------------------*/
void eclater(uchar *buf_bit, uchar *byte)
//unsigned char *buf_bit;
//unsigned char *byte;
{
	int i;
	unsigned char m;

	for (i = 0; i < 8; i++) {
		for (m = 0x80; m != 0;) // m >>= 1)
				{
			if ((buf_bit[i] & m) != 0)
				*byte = 1;
			else
				*byte = 0;
			byte++;
			m = m / 2;
		}
	}

}
/*----------------------------------------------------------------
 Compacter 64 octets en 64 bits
 -----------------------------------------------------------------*/
void compacter(uchar *byte, uchar *buf_bit)
//unsigned char *byte;
//unsigned char *buf_bit;
{
	int i;
	unsigned char m, n;

	for (i = 0; i < 8; i++) {
		n = 0;
		for (m = 0x80; m != 0;) //  m >>= 1)
				{
			if (*byte++)
				n = n | m;
			m = m / 2;

		}
		buf_bit[i] = n;
	}
}

/*----------------------------------------------------------------
 D E S
 -----------------------------------------------------------------*/

void des(uchar *binput, uchar *boutput, uchar *bkey) {

	eclater(binput, input);
	eclater(bkey, Key);
	Ks(Key, Kn);

	permutation(input, T1);
	chiffrement(input, output, Kn);

	permutation(output, T2);
	compacter(output, boutput);
}

void desm1(uchar *binput, uchar *boutput, uchar *bkey) {

	eclater(binput, input);
	eclater(bkey, Key);
	Ks(Key, Kn);

	permutation(input, T1);
	dechiffrement(input, output, Kn);
	permutation(output, T2);
	compacter(output, boutput);

}

/*----------------------------------------------------------------
 Fin
 -----------------------------------------------------------------*/

/* calcule le code chiffre selon DES :
 - in=pt sur nombre ?coder (8 octets =64 bits)
 - kk= pt sur clef d'encodage (8 octets=56 bits + parite)
 (Rq: parite viree pour le calcul donc calcul sur 56 bits et non 64)
 - resultat (nombre code) ds res_codage sur 8 octets */uchar * codage_des(
		uchar * in, uchar * kk) {
	des(in, res_codage, kk);
	return (res_codage);
}

/* calcule le code d‚chiffre selon DES :
 - in=pt sur nombre cod??d‚coder (8 octets =64 bits)
 - kk= pt sur clef d'encodage (8 octets=56 bits + parite)
 (Rq: parite viree pour le calcul donc calcul sur 56 bits et non 64)
 - resultat (nombre d‚code) ds pt res_decodage sur 8 octets */uchar * decodage_des(
		uchar * in, uchar * kk) {
	desm1(in, res_decodage, kk);
	return (res_decodage);
}

/* calcule le code chiffre selon EDE=(DES(Kn)(DES-1(Kn+1)(DES(Kn)))) :
 - in=pt sur nombre cod??d‚coder (8 octets =64 bits)
 - kk= pt sur clef d'encodage (16 octets=Kn(8) + Kn+1(8))
 (Rq: parite viree pour le calcul donc calcul sur 56 bits et non 64)
 - resultat (nombre d‚code) ds pt res_decodage sur 8 octets */uchar * codage_ede(
		uchar * in, uchar * kk) {
	uchar cleKn1[8], cleKn2[8], res_tmp[8];

	memcpy(cleKn1, kk, 8);
	des(in, res_codage, cleKn1);
	memcpy(cleKn2, &kk[8], 8);
	desm1(res_codage, res_tmp, cleKn2);
	memset(cleKn2, 0, 8);
	des(res_tmp, res_codage, cleKn1);
	memset(cleKn1, 0, 8);
	memset(res_tmp, 0, 8);
	return (res_codage);
}

uint minin(uint a, uint b) {
	if (a >= b)
		return (b);
	else
		return (a);
}

void macgen(uchar *in, uint len, uchar *key, uchar *out) {
	uchar lp, thismove;
	uint pos;

#if 0
	memcpy(out,in,len);
#else
	memset(out, 0, 8);
	for (pos = 0; pos < len; pos += (uint) thismove) {
		thismove = minin(len - pos, 8);
		for (lp = 0; lp < thismove; lp++)
			out[lp] ^= in[lp + pos];

		des(out, out, key);
	}
#endif
}
void DebugBuff(char *Title, unsigned char *Buff, int Len) {
	int i;
	printf("%s,Len=%i\n", Title, Len);
	for (i = 0; i < Len; i++) {
		printf("%0.2X", Buff[i]);
		if (i % 20 == 19)
			printf("\n");

	}
	printf("\n");

}

void TriEnDes(uchar *s, uchar*o, uchar *Key) {
	uchar TmpBuff[16];
	des(s, o, Key);
	desm1(o, TmpBuff, Key + 8);
	des(TmpBuff, o, Key);
}

void TriUnDes(uchar *s, uchar*o, uchar *Key) {
	uchar TmpBuff[16];
	desm1(s, o, Key);
	des(o, TmpBuff, Key + 8);
	desm1(TmpBuff, o, Key);
}
void TriDesEncrypt(uchar *Key, int KeyLen, uchar *s, int slen, uchar *o) {
	int i;
	uchar S[16];

	for (i = 0; i < slen / 8; i++) {

		memcpy(S, s + 8 * i, 8);
		//printf("%d\r\n",i);
		//DebugBuff("S",S,8);
		if (KeyLen == 16)
			TriEnDes(S, o + 8 * i, Key);
		else
			des(S, o + 8 * i, Key);

		//DebugBuff("out",o+8*i,8);
	}
}
void TriDesDecrypt(uchar *Key, int KeyLen, uchar *s, int slen, uchar*o) {
	int i;
	uchar S[16];

	for (i = 0; i < slen / 8; i++) {
		memcpy(S, s + 8 * i, 8);
		if (KeyLen == 16)
			TriUnDes(S, o + 8 * i, Key);
		else
			desm1(S, o + 8 * i, Key);
	}
}

void CBCDesEncrypt(uchar *Key, int KeyLen, uchar *s, int slen, uchar*o) {
	int i;
	uchar S[16];

	uchar In[8];
	uchar Out[8];
	memset(Out, 0, 8);

	for (i = 0; i < slen / 8; i++) {

		memcpy(S, s + 8 * i, 8);
		XOR(S, Out, In);
		//printf("%d\r\n",i);
		//DebugBuff("S",S,8);
		if (KeyLen == 16) {
			TriEnDes(In, Out, Key);
			memcpy(o + 8 * i, Out, 8);
		} else {
			des(In, Out, Key);
			memcpy(o + 8 * i, Out, 8);
		}

		//DebugBuff("out",o+8*i,8);
	}
}
void CBCDesDecrypt(uchar *Key, int KeyLen, uchar *s, int slen, uchar*o) {
	int i;
	uchar S[16];
	uchar In[8];
	uchar Out[8];
	memset(Out, 0, 8);

	for (i = 0; i < slen / 8; i++) {
		memcpy(S, s + 8 * i, 8);
		XOR(S, Out, In);
		if (KeyLen == 16) {
			TriUnDes(In, Out, Key);
			memcpy(o + 8 * i, Out, 8);
		} else {
			desm1(In, Out, Key);
			memcpy(o + 8 * i, Out, 8);
		}
	}
}

int EncryptMag2(uchar *MagData, uchar MagDataLen, uchar *Out, uchar *TheKey) {
	memcpy(Out, MagData, MagDataLen);
	TriEnDes(MagData + MagDataLen - 9, Out + MagDataLen - 9, TheKey);
	return SUCCESS;
}
int EnTarck2(uchar *MagData, uchar MagDataLen, uchar *Out, uchar *TheKey) {
	char indata[100];
	uchar inlen;

	memset(indata, 0, sizeof(indata));
	memcpy(indata, (char*) MagData, MagDataLen);
	inlen = (MagDataLen + 7) / 8 * 8;

	Out[0] = ((inlen / 10) << 4) + (inlen % 10);
	//printf("inlen:%d Out[0]:%x\r\n",inlen,Out[0]);
	TriDesEncrypt(TheKey, 16, indata, inlen, Out + 1);
	return SUCCESS;
}
int UnTarck2(uchar *MagData, uchar MagDataLen, uchar *Out, uchar *TheKey) {
	uchar inlen;
	inlen = ((MagData[0] >> 4) * 10) + (MagData[0] & 0x0f);
	TriDesDecrypt(TheKey, 16, MagData + 1, inlen, Out);
	return SUCCESS;
}
uchar *str2hex(uchar *Ptd, int Ld, uchar *Pts) {
	int i;
	int len;
	uchar value;

	len = 0;
	while (Pts[len] != '\0')
		len++;

	memset(Ptd, 0, Ld);
	for (i = 0; (i < len) && (i < Ld * 2); i++) {
		if ((Pts[len - 1 - i] >= '0') && (Pts[len - 1 - i] <= '9')) {
			value = Pts[len - 1 - i] - '0';
		} else if ((Pts[len - 1 - i] >= 'a') && (Pts[len - 1 - i] <= 'f')) {
			value = Pts[len - 1 - i] - 'a' + 10;
		} else if ((Pts[len - 1 - i] >= 'A') && (Pts[len - 1 - i] <= 'F')) {
			value = Pts[len - 1 - i] - 'A' + 10;
		} else {
			value = 0;
		}

		if (i % 2) //high
				{
			Ptd[Ld - 1 - i / 2] |= (uchar) (value << 4);
		} else //low
		{
			Ptd[Ld - 1 - i / 2] |= (uchar) (value & 0x0f);
		}
	}

	return Ptd;

}

uchar value2char(uchar val) {
	if (val >= 0 && val <= 9) {
		return (val + '0');
	}
	if (val >= 0x0a && val <= 0x0f) {
		return (val + 'A' - 0x0a);
	}
	return 0;
}

uchar *hex2str(uchar *Ptd, uchar *Pts, int PtdLen) {
	int i, j;
	uchar value;

	j = 0;
	if (PtdLen % 2) {
		Ptd[j++] = value2char(Pts[0] & 0x0f);
	} else {
		Ptd[j++] = value2char(Pts[0] >> 4);
		Ptd[j++] = value2char(Pts[0] & 0x0f);
	}
	for (i = 1; i < (PtdLen / 2); i++) {
		Ptd[j++] = value2char(Pts[i] >> 4);
		Ptd[j++] = value2char(Pts[i] & 0x0f);
	}

	Ptd[j++] = 0x00;

	return Ptd;
}

void XOR(uchar *Data1, uchar *Data2, uchar *Out) {
	int i;
	for (i = 0; i < 8; i++)
		Out[i] = Data1[i] ^ Data2[i];
}

int EnANSIX98(uchar *TheKey, char *CardNo, int CardNoLen, char *Pin,
		char PinLen, uchar *EnCryData) {
	char AccInfo[20];
	uchar HexAccInfo[8];
	char PinInfo[20];
	uchar HexPinInfo[8];
	uchar Tmp1[8];

	if (CardNoLen > 19)
		return FAILURE;
	if (PinLen > 12)
		return FAILURE;
	memset(AccInfo, 0, sizeof(AccInfo));
	CardNoLen = strlen(CardNo);
	strcpy(AccInfo, "0000");
	memcpy(AccInfo + 4, CardNo + CardNoLen - 13, 12);
	str2hex(HexAccInfo, 8, (uchar*) AccInfo);

	memset(HexPinInfo, 0xFF, 8);
	HexPinInfo[0] = PinLen;
	str2hex(HexPinInfo + 1, (PinLen + 1) / 2, (uchar*) Pin);
	//DebugBuff("HexPinInfo",HexPinInfo,8);
	XOR(HexAccInfo, HexPinInfo, Tmp1);
	//DebugBuff("PIN data",Tmp1,8);
	TriEnDes(Tmp1, EnCryData, TheKey);
	return SUCCESS;
}

int UnANSIX98(uchar *TheKey, char *CardNo, int CardNoLen, char *Pin,
		char *PinLen, uchar *EnCryData) {
	char AccInfo[20];
	uchar HexAccInfo[8];
	char PinInfo[20];
	uchar HexPinInfo[8];
	uchar Tmp1[8];

	if (CardNoLen > 19)
		return FAILURE;
	*PinLen = 0;
	memset(AccInfo, 0, sizeof(AccInfo));
	CardNoLen = strlen(CardNo);
	strcpy(AccInfo, "0000");
	memcpy(AccInfo + 4, CardNo + CardNoLen - 13, 12);
	str2hex(HexAccInfo, 8, (uchar*) AccInfo);

	TriUnDes(EnCryData, Tmp1, TheKey);

	XOR(HexAccInfo, Tmp1, HexPinInfo);
	*PinLen = HexPinInfo[0];
	hex2str(Pin, HexPinInfo + 1, *PinLen);
	return SUCCESS;
}

int YL_MAC(uchar *MACKey, uchar *InData, int InDataLen, uchar *MAC) {
	int i, b;
	uchar Tmp1[20];
	uchar Tmp2[20];
	uchar Tmp3[20];
	uchar Tmp4[20];

	b = (InDataLen + 7) / 8;

	memset(InData + InDataLen, 0, b * 8 - InDataLen); //²¹0

	memcpy(Tmp1, InData, 8);
	for (i = 1; i < b; i++) {
		XOR(Tmp1, InData + i * 8, Tmp1);
		//DebugBuff("tmp1",Tmp1,8);
	}
	hex2str(Tmp2, Tmp1, 8);
	//DebugBuff("tmp2",Tmp2,8);
	hex2str(Tmp3, Tmp1 + 4, 8);
	//DebugBuff("tmp3",Tmp3,8);
	//TriEnDes(Tmp2, Tmp2, MACKey);
	des(Tmp2, Tmp2, MACKey);
	//DebugBuff("tmp2",Tmp2,8);
	XOR(Tmp2, Tmp3, Tmp4);
	//DebugBuff("tmp4",Tmp4,8);
	//TriEnDes(Tmp4, Tmp4, MACKey);
	des(Tmp4, Tmp4, MACKey);
	//DebugBuff("tmp4",Tmp4,8);
	hex2str(MAC, Tmp4, 8);
	//DebugBuff("MAC",MAC,8);
	return SUCCESS;
}

int ANSI_X919(uchar *MACKey, uchar *InData, int InDataLen, uchar *MAC) {
	int i, b;
	uchar Tmp1[20];
	uchar Tmp2[20];
	uchar Tmp3[20];
	uchar HexMacKey[16];
	uchar HexData[4096];
	uchar OutData[4096];
	int HexDataLen;
	memset(HexData, 0, sizeof(HexData));
	str2hex(HexMacKey, 16, MACKey);
	//HexDataLen = InDataLen/2;
	//str2hex(HexData,HexDataLen,InData);
	HexDataLen = InDataLen;
	memcpy(HexData, InData, HexDataLen);

	HexDataLen = (HexDataLen + 7) / 8 * 8;

	CBCDesEncrypt(HexMacKey, 8, HexData, HexDataLen, OutData);

	memcpy(Tmp1, OutData + HexDataLen - 8, 8);
	TriDesDecrypt(HexMacKey + 8, 8, Tmp1, 8, Tmp2);

	TriDesEncrypt(HexMacKey, 8, Tmp2, 8, Tmp3);

	hex2str(MAC, Tmp3, 16);

	return SUCCESS;
}

void GetRand(char *r) {
	struct timeval tpstart;
	int i = 0;
	for (i = 0; i < 16; i++) {
		gettimeofday(&tpstart, NULL);
		srand(tpstart.tv_usec);
		*(r + i) = rand() % 255;
	}

}
void GetTMK(char *TK, char *TmkTkKey, char *TmkKey) {
	char Tmk[16];
	char HexTK[17];
	char HexTmkTkKey[17];
	memset(Tmk, 0, 16);
	GetRand(Tmk);
	str2hex(HexTK, 16, TK);
	TriDesEncrypt(HexTK, 16, Tmk, 16, HexTmkTkKey);
	hex2str(TmkTkKey, HexTmkTkKey, 32);
	//printf("TmkTkKey:%s\r\n",TmkTkKey);
	hex2str(TmkKey, Tmk, 32);
	//printf("TmkKey:%s\r\n",TmkKey);
}
void GetPIK(char *Tmk, char *PikTmkKey, char *PikKey, char *CheckValue) {
	char Pik[16];
	char HexTmk[17];
	char HexPikTmkKey[17];
	char HexCheckValue[8];
	GetRand(Pik);
	str2hex(HexTmk, 16, Tmk);
	TriDesEncrypt(HexTmk, 16, Pik, 16, HexPikTmkKey);
	hex2str(PikTmkKey, HexPikTmkKey, 32);
	//printf("PikTmkKey:%s\r\n",PikTmkKey);
	hex2str(PikKey, Pik, 32);
	//printf("PikKey:%s\r\n",PikKey);
	TriDesEncrypt(Pik, 16, "\x00\x00\x00\x00\x00\x00\x00\x00", 8,
			HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}
void GetMAK(char *Tmk, char *MakTmkKey, char *MakKey, char *CheckValue) {
	char Mak[16];
	char HexTmk[17];
	char HexMakTmkKey[17];
	char HexCheckValue[8];
	GetRand(Mak);
	memset(Mak + 8, 0, 8);
	str2hex(HexTmk, 16, Tmk);
	memset(HexMakTmkKey, 0, 17);
	TriDesEncrypt(HexTmk, 16, Mak, 8, HexMakTmkKey);
	hex2str(MakTmkKey, HexMakTmkKey, 32);
	//printf("MakTmkKey:%s\r\n",MakTmkKey);
	hex2str(MakKey, Mak, 32);
	//printf("MakKey:%s\r\n",MakKey);
	TriDesEncrypt(Mak, 8, "\x00\x00\x00\x00\x00\x00\x00\x00", 8, HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}

void GetTRK(char *Tmk, char *TrkTmkKey, char *TrkKey, char *CheckValue) {
	char Trk[16];
	char HexTmk[17];
	char HexTrkTmkKey[17];
	char HexCheckValue[8];
	GetRand(Trk);
	str2hex(HexTmk, 16, Tmk);
	TriDesEncrypt(HexTmk, 16, Trk, 16, HexTrkTmkKey);
	hex2str(TrkTmkKey, HexTrkTmkKey, 32);
	//printf("TrkTmkKey:%s\r\n",TrkTmkKey);
	hex2str(TrkKey, Trk, 32);
	//printf("TrkKey:%s\r\n",TrkKey);
	TriDesEncrypt(Trk, 16, "\x00\x00\x00\x00\x00\x00\x00\x00", 8,
			HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}
void UnPIK(char *Zmk, char *PikZmkKey, char *PikKey, char *CheckValue) {
	char Pik[16];
	char HexZmk[17];
	char HexPikZmkKey[17];
	char HexCheckValue[8];
	str2hex(HexZmk, 16, Zmk);
	str2hex(HexPikZmkKey, 16, PikZmkKey);
	TriDesDecrypt(HexZmk, 16, HexPikZmkKey, 16, Pik);
	hex2str(PikKey, Pik, 32);
	//printf("PikKey:%s\r\n",PikKey);
	TriDesEncrypt(Pik, 16, "\x00\x00\x00\x00\x00\x00\x00\x00", 8,
			HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}
void UnMAK(char *Zmk, char *MakZmkKey, char *MakKey, char *CheckValue) {
	char Mak[16];
	char HexZmk[17];
	char HexMakZmkKey[17];
	char HexCheckValue[8];
	str2hex(HexZmk, 16, Zmk);
	str2hex(HexMakZmkKey, 8, MakZmkKey);
	TriDesDecrypt(HexZmk, 16, HexMakZmkKey, 8, Mak);
	hex2str(MakKey, Mak, 16);
	//printf("MakKey:%s\r\n",MakKey);
	TriDesEncrypt(Mak, 8, "\x00\x00\x00\x00\x00\x00\x00\x00", 8, HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}
void UnMAK16(char *Zmk, char *MakZmkKey, char *MakKey, char *CheckValue) {
	char Mak[16];
	char HexZmk[17];
	char HexMakZmkKey[17];
	char HexCheckValue[8];
	str2hex(HexZmk, 16, Zmk);
	str2hex(HexMakZmkKey, 16, MakZmkKey);
	TriDesDecrypt(HexZmk, 16, HexMakZmkKey, 16, Mak);
	hex2str(MakKey, Mak, 32);
	//printf("MakKey:%s\r\n",MakKey);
	TriDesEncrypt(Mak, 16, "\x00\x00\x00\x00\x00\x00\x00\x00", 16,
			HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}

void UnTRK(char *Zmk, char *TrkZmkKey, char *TrkKey, char *CheckValue) {
	char Trk[16];
	char HexZmk[17];
	char HexTrkZmkKey[17];
	char HexCheckValue[8];
	str2hex(HexZmk, 16, Zmk);
	str2hex(HexTrkZmkKey, 16, TrkZmkKey);
	TriDesDecrypt(HexZmk, 16, HexTrkZmkKey, 16, Trk);
	hex2str(TrkKey, Trk, 32);
	//printf("TrkKey:%s\r\n",TrkKey);
	TriDesEncrypt(Trk, 16, "\x00\x00\x00\x00\x00\x00\x00\x00", 8,
			HexCheckValue);
	hex2str(CheckValue, HexCheckValue, 8);
	//printf("CheckValue:%s\r\n",CheckValue);
}

//char pp[257]="DBA81E5E6F905DF6FBE2D79560F72800426A2D9311902FDCD9344F754C9632200FA5EF28437B38F55517C6F009600997C4580FE3189FB3D8942A086CCB4307F1";
//char qq[257]="B92C3C74CE4E624FBF8EDDD3A832A452A80030F3EDB9A5507FD5434E4A7F093E71D2AA35D054B4A7D1B1FC4873BB4F2696A056018A6CC8ECDAC0D52854D3D135";

char ee[20] = "10001";
char nn[257] =
		"9EE272B7172AEFAC0D55636D6B3FBB20E3A158C7F297E05B9B6CD976396EB2BF388E4326C1C95271E03F883E83C16A18F34EE37DF185B8B8B699BA9C9B314424F475E123BF6CF81BE67A5CB1AE6E3A9772900D25C4F1CEDC9E1CAE6015477E0E65D00BF004F7AB18ED13873C06001F204F023F2BE0F2ED8958C6D9FA6DFF65E5";
char dd[257] =
		"4B46AA670878F538A4D091DCE3753C6966AFA8EA828B6666C08A9FB51D9CCEDF3E2258BA6211CA17DDD0FBF1F2668EA7E78EB6C592CA722C4187301CC6825EF00B7CF1AFEED1BCAE28475F4A51D2CC137815B257D2F9B4984FB7AFDB4306F90570BFE5AE337D4A8EFBF1330721B29233127620155AC47524BD05D6DD003E6F81";

//char nn[257]="C37B01694E9EC7012ABDDEF9263038B92BD9F11D5B288D77B1EEEDD95F0B228324062551F394FAE9207A014FBFDC0C0466B9F495807002240BA418B9865E4326ED24E6E7286F0E9181D40E049367BACDBA9300E57494773B315F7A840C495EC9403BA3852AA2D4835ADBA0037BD25082DE5A9865D50D51B3FF6419836D86BE59";
//char dd[257]="57A155B1A287F50825E3F607D4C1BB3BEF57850A36EA47F8C7702779D16C896D268206CAE519FD1CF2ED1E976497BD5F5FB9AED6323C84092A9AC666ACC0A6F30C6E0FB131B5120028480DB5344BE46960F7F7040FE92240D27D85D540AD0006FCD80E73D98AB67B850511EF3B0FA8392F789B5C2EF7DEAB48FBF897F26AFA1D";

char* Jstring2CStr(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "GB2312");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
			strencode); // String .getByte("GB2312");
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1); //new   char[alen+1]; "\0"
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0); //ÊÍ·ÅÄÚ´æ

	return rtn;
}
jstring CharTojstring(JNIEnv* env, char* str) {
	jsize len = strlen(str);
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "GB2312");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "<init>",
			"([BLjava/lang/String;)V");
	jbyteArray barr = (*env)->NewByteArray(env, len);
	(*env)->SetByteArrayRegion(env, barr, 0, len, (jbyte*) str);
	return (jstring)(*env)->NewObject(env, clsstring, mid, barr, strencode);
}

JNIEXPORT jstring JNICALL Java_com_mars_marsview_utils_RSAEncrypt_RSAPublickeyEncrypt(
		JNIEnv *env, jclass jc, jstring data, jstring pkey) {
	return CharTojstring(env,RSAPublicEncrypt(Jstring2CStr(env, data),Jstring2CStr(env, pkey)));
}

char* RSAPublicEncrypt(char *InData, char *pkey) {
	SBigInt P, Q, N, D, E, mi, ci, oi, T, TT, TTT, TTTT;
	char *OutData = (char*)malloc(513);
	SBigIntInit(&P);
	SBigIntInit(&Q);
	SBigIntInit(&N);
	SBigIntInit(&D);
	SBigIntInit(&E);
	SBigIntInit(&mi);
	SBigIntInit(&ci);
	SBigIntInit(&oi);
	char stout[512];

	/*Get(&P,p);
	 Put(&P);
	 Get(&Q,q);
	 Put(&Q);
	 Mov(&N,Mul(&P,&Q,&T));
	 Put(&N);*/
	memset(OutData,0,513);
	Get(&N, pkey);
	Put(&N, stout);
	Get(&E, ee);
	Put(&E, stout);

	//Mov(&D,Euc(&E,Mul((SubD(&P,1,&T)),(SubD(&Q,1,&TT)),&TTT),&TTTT));
	//Get(&D,dd);
	//Put(&D,stout);

	Get(&mi, InData);
	Put(&mi, stout);

	Mov(&ci, RsaTrans(&mi, &E, &N, &T));
	Put(&ci, OutData);

	//Mov(&oi,RsaTrans(&ci,&D,&N,&T));
	//Put(&oi,OutData);
	return OutData;
}

void RSAPrivateDecrypte(char *InData, char *OutData) {
	SBigInt P, Q, N, D, E, mi, ci, oi, T, TT, TTT, TTTT;
	SBigIntInit(&P);
	SBigIntInit(&Q);
	SBigIntInit(&N);
	SBigIntInit(&D);
	SBigIntInit(&E);
	SBigIntInit(&mi);
	SBigIntInit(&ci);
	SBigIntInit(&oi);
	char stout[512];

	/*Get(&P,p);
	 Put(&P);
	 Get(&Q,q);
	 Put(&Q);
	 Mov(&N,Mul(&P,&Q,&T));
	 Put(&N);*/

	Get(&N, nn);
	Put(&N, stout);
	//Get(&E,ee);
	//Put(&E,stout);

	//Mov(&D,Euc(&E,Mul((SubD(&P,1,&T)),(SubD(&Q,1,&TT)),&TTT),&TTTT));
	Get(&D, dd);
	Put(&D, stout);

	//Get(&mi,InData);
	//Put(&mi,stout);

	Get(&ci, InData);
	Put(&ci, stout);

	Mov(&oi, RsaTrans(&ci, &D, &N, &T));
	Put(&oi, OutData);

}

