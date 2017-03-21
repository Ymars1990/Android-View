/*****************************************************************
大数运算库头文件：BigInt.h
作者：afanty@vip.sina.com
版本：1.2 (2003.5.13)
说明：适用于MFC，1024位RSA运算
*****************************************************************/

//允许生成1120位（二进制）的中间结果
#ifndef BI_MAXLEN
#define BI_MAXLEN 68
#define DEC 10
#define HEX 16
struct SBigInt_S
{
	unsigned int m_nLength;
	unsigned int m_ulValue[BI_MAXLEN];
};
typedef struct SBigInt_S SBigInt;
	void SBigIntInit(SBigInt *I);
/*****************************************************************
基本操作与运算
Mov，赋值运算，可赋值为大数或普通整数，可重载为运算符“=”
Cmp，比较运算，可重载为运算符“==”、“!=”、“>=”、“<=”等
Add，加，求大数与大数或大数与普通整数的和，可重载为运算符“+”
Sub，减，求大数与大数或大数与普通整数的差，可重载为运算符“-”
Mul，乘，求大数与大数或大数与普通整数的积，可重载为运算符“*”
Div，除，求大数与大数或大数与普通整数的商，可重载为运算符“/”
Mod，模，求大数与大数或大数与普通整数的模，可重载为运算符“%”
*****************************************************************/
    void MovD(SBigInt *I, unsigned long long A);
    void Mov(SBigInt *I, SBigInt *A);
    SBigInt* Add(SBigInt *I, const SBigInt *A,SBigInt *O);
    SBigInt* Sub(SBigInt *I, const SBigInt *A,SBigInt *O);
    SBigInt* Mul(SBigInt *I, const SBigInt *A,SBigInt *O);
    SBigInt* Div(SBigInt *I, SBigInt *A,SBigInt *O);
    SBigInt* Mod(SBigInt *I, SBigInt *A,SBigInt *O);
    SBigInt* AddD(SBigInt *I, unsigned int A,SBigInt *O);
    SBigInt* SubD(SBigInt *I, unsigned int A,SBigInt *O);
    SBigInt* MulD(SBigInt *I, unsigned int A,SBigInt *O);
    SBigInt* DivD(SBigInt *I, unsigned int A,SBigInt *O);
    unsigned int ModD(SBigInt *I, unsigned int A,SBigInt *O); 
    int Cmp(SBigInt *I, const SBigInt *A); 

/*****************************************************************
输入输出
Get，从字符串按10进制或16进制格式输入到大数
Put，将大数按10进制或16进制格式输出到字符串
*****************************************************************/
    void Get(SBigInt *I,char c[512]);
    void Put(SBigInt *I, char *Out);

/*****************************************************************
RSA相关运算
Rab，拉宾米勒算法进行素数测试
Euc，欧几里德算法求解同余方程
RsaTrans，反复平方算法进行幂模运算
GetPrime，产生指定长度的随机大素数
*****************************************************************/
    int Rab(SBigInt *M);
    SBigInt* Euc(SBigInt* K,SBigInt* A,SBigInt *O);
    SBigInt* RsaTrans(SBigInt* I,const SBigInt* A, SBigInt* B,SBigInt *O);
    void GetPrime(SBigInt* M,int bits);
#endif

