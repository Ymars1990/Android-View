/*****************************************************************
大数运算库源文件：BigInt.cpp
作者：afanty@vip.sinA->com
版本：1.2 (2003.5.13)
说明：适用于MFC，1024位RSA运算
*****************************************************************/
//#include "stdafX->h"
//#include<string>
#include <stdlib.h>
#include <stdio.h>
#include "BigInt.h"

//小素数表
const static int PrimeTable[550]=
{   3,    5,    7,    11,   13,   17,   19,   23,   29,   31,
    37,   41,   43,   47,   53,   59,   61,   67,   71,   73,
    79,   83,   89,   97,   101,  103,  107,  109,  113,  127, 
    131,  137,  139,  149,  151,  157,  163,  167,  173,  179, 
    181,  191,  193,  197,  199,  211,  223,  227,  229,  233, 
    239,  241,  251,  257,  263,  269,  271,  277,  281,  283, 
    293,  307,  311,  313,  317,  331,  337,  347,  349,  353, 
    359,  367,  373,  379,  383,  389,  397,  401,  409,  419, 
    421,  431,  433,  439,  443,  449,  457,  461,  463,  467, 
    479,  487,  491,  499,  503,  509,  521,  523,  541,  547, 
    557,  563,  569,  571,  577,  587,  593,  599,  601,  607, 
    613,  617,  619,  631,  641,  643,  647,  653,  659,  661, 
    673,  677,  683,  691,  701,  709,  719,  727,  733,  739, 
    743,  751,  757,  761,  769,  773,  787,  797,  809,  811, 
    821,  823,  827,  829,  839,  853,  857,  859,  863,  877,
    881,  883,  887,  907,  911,  919,  929,  937,  941,  947, 
    953,  967,  971,  977,  983,  991,  997,  1009, 1013, 1019, 
    1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087,
    1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 
    1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 
    1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 
    1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381,
    1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 
    1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511, 1523,
    1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 
    1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637, 1657, 1663, 
    1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 
    1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811, 1823, 
    1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 
    1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987, 1993, 
    1997, 1999, 2003, 2011, 2017, 2027, 2029, 2039, 2053, 2063,
    2069, 2081, 2083, 2087, 2089, 2099, 2111, 2113, 2129, 2131, 
    2137, 2141, 2143, 2153, 2161, 2179, 2203, 2207, 2213, 2221, 
    2237, 2239, 2243, 2251, 2267, 2269, 2273, 2281, 2287, 2293,
    2297, 2309, 2311, 2333, 2339, 2341, 2347, 2351, 2357, 2371,
    2377, 2381, 2383, 2389, 2393, 2399, 2411, 2417, 2423, 2437, 
    2441, 2447, 2459, 2467, 2473, 2477, 2503, 2521, 2531, 2539, 
    2543, 2549, 2551, 2557, 2579, 2591, 2593, 2609, 2617, 2621, 
    2633, 2647, 2657, 2659, 2663, 2671, 2677, 2683, 2687, 2689, 
    2693, 2699, 2707, 2711, 2713, 2719, 2729, 2731, 2741, 2749, 
    2753, 2767, 2777, 2789, 2791, 2797, 2801, 2803, 2819, 2833, 
    2837, 2843, 2851, 2857, 2861, 2879, 2887, 2897, 2903, 2909,
    2917, 2927, 2939, 2953, 2957, 2963, 2969, 2971, 2999, 3001,
    3011, 3019, 3023, 3037, 3041, 3049, 3061, 3067, 3079, 3083,
    3089, 3109, 3119, 3121, 3137, 3163, 3167, 3169, 3181, 3187, 
    3191, 3203, 3209, 3217, 3221, 3229, 3251, 3253, 3257, 3259, 
    3271, 3299, 3301, 3307, 3313, 3319, 3323, 3329, 3331, 3343,
    3347, 3359, 3361, 3371, 3373, 3389, 3391, 3407, 3413, 3433, 
    3449, 3457, 3461, 3463, 3467, 3469, 3491, 3499, 3511, 3517, 
    3527, 3529, 3533, 3539, 3541, 3547, 3557, 3559, 3571, 3581,
    3583, 3593, 3607, 3613, 3617, 3623, 3631, 3637, 3643, 3659, 
    3671, 3673, 3677, 3691, 3697, 3701, 3709, 3719, 3727, 3733, 
    3739, 3761, 3767, 3769, 3779, 3793, 3797, 3803, 3821, 3823, 
    3833, 3847, 3851, 3853, 3863, 3877, 3881, 3889, 3907, 3911, 
    3917, 3919, 3923, 3929, 3931, 3943, 3947, 3967, 3989, 4001
};
//初始化结构体
void SBigIntInit(SBigInt *I)
{
	int i;
	I->m_nLength=1;
	for(i=0;i<BI_MAXLEN;i++)I->m_ulValue[i]=0;
}

/****************************************************************************************
大数比较
调用方式：N.Cmp(A)
返回值：若N<A返回-1；若N=A返回0；若N>A返回1
****************************************************************************************/
int Cmp(SBigInt *I, const SBigInt *A)
{
	int i;

    if(I->m_nLength>A->m_nLength)return 1;
    if(I->m_nLength<A->m_nLength)return -1;
    for(i=I->m_nLength-1;i>=0;i--)
    {
        if(I->m_ulValue[i]>A->m_ulValue[i])return 1;
        if(I->m_ulValue[i]<A->m_ulValue[i])return -1;
    }
    return 0;
}

/****************************************************************************************
大数赋值
调用方式：N.Mov(A)
返回值：无，N被赋值为A
****************************************************************************************/
void Mov(SBigInt *I, SBigInt *A)
{
	int i;
    I->m_nLength=A->m_nLength;
    for(i=0;i<BI_MAXLEN;i++)I->m_ulValue[i]=A->m_ulValue[i];
}

void MovD(SBigInt *I, unsigned long long A)
{
	int i;
    if(A>0xffffffff)
    {
        I->m_nLength=2;
        I->m_ulValue[1]=(unsigned int)(A>>32);
        I->m_ulValue[0]=(unsigned int)A;
    }
    else
    {
        I->m_nLength=1;
        I->m_ulValue[0]=(unsigned int)A;
    }
    for(i=I->m_nLength;i<BI_MAXLEN;i++)I->m_ulValue[i]=0;
}

/****************************************************************************************
大数相加
调用形式：N.Add(A)
返回值：N+A
****************************************************************************************/
SBigInt* Add(SBigInt *I, const SBigInt *A,SBigInt *O)
{
	unsigned i;
	SBigInt* X=O;
	SBigIntInit(X);
    Mov(X,I);
    unsigned carry=0;
    unsigned long long sum=0;
    if(X->m_nLength<A->m_nLength)X->m_nLength=A->m_nLength;
    for(i=0;i<X->m_nLength;i++)
    {
        sum=A->m_ulValue[i];
		sum=sum+X->m_ulValue[i]+carry;
        X->m_ulValue[i]=(unsigned int)sum;
        carry=(unsigned)(sum>>32);
    }
    X->m_ulValue[X->m_nLength]=carry;
    X->m_nLength+=carry;
    return X;
}

SBigInt* AddD(SBigInt *I, unsigned int A,SBigInt *O)
{
    SBigInt *X=O;
	SBigIntInit(X);
    Mov(X,I);
    unsigned long long sum;
    sum=X->m_ulValue[0];
	sum+=A;
    X->m_ulValue[0]=(unsigned int)sum;
    if(sum>0xffffffff)
    {
        unsigned i=1;
        while(X->m_ulValue[i]==0xffffffff){X->m_ulValue[i]=0;i++;}
        X->m_ulValue[i]++;
        if(I->m_nLength==i)I->m_nLength++;
    }
    return X;
}

/****************************************************************************************
大数相减
调用形式：N.Sub(A)
返回值：N-A
****************************************************************************************/
SBigInt* Sub(SBigInt *I, const SBigInt *A,SBigInt *O)
{
    SBigInt *X=O;
	SBigIntInit(X);
    Mov(X,I);
    if(Cmp(X,A)<=0){MovD(X,0);return X;}
    unsigned carry=0;
    unsigned long long num;
	unsigned i;
    for(i=0;i<I->m_nLength;i++)
    {
        if((I->m_ulValue[i]>A->m_ulValue[i])||((I->m_ulValue[i]==A->m_ulValue[i])&&(carry==0)))
        {
            X->m_ulValue[i]=I->m_ulValue[i]-carry-A->m_ulValue[i];
            carry=0;
        }
        else
        {
            num=0x100000000+I->m_ulValue[i];
            X->m_ulValue[i]=(unsigned int)(num-carry-A->m_ulValue[i]);
            carry=1;
        }
    }
    while(X->m_ulValue[X->m_nLength-1]==0)X->m_nLength--;
    return X;
}

SBigInt* SubD(SBigInt *I, unsigned int A,SBigInt *O)
{
    SBigInt *X=O;
	SBigIntInit(X);
    Mov(X,I);
    if(X->m_ulValue[0]>=A){X->m_ulValue[0]-=A;return X;}
    if(X->m_nLength==1){MovD(X,0);return X;}
    unsigned long long num=0x100000000+X->m_ulValue[0];
    X->m_ulValue[0]=(unsigned int)(num-A);
    int i=1;
    while(X->m_ulValue[i]==0){X->m_ulValue[i]=0xffffffff;i++;}
    X->m_ulValue[i]--;
    if(X->m_ulValue[i]==0)X->m_nLength--;
    return X;
}

/****************************************************************************************
大数相乘
调用形式：N.Mul(A)
返回值：N*A
****************************************************************************************/
SBigInt* Mul(SBigInt *I, const SBigInt *A,SBigInt *O)
{
    if(A->m_nLength==1)return MulD(I,A->m_ulValue[0],O);
	SBigInt *X=O;
	SBigIntInit(X);
	unsigned long long sum,mul=0,carry=0;
	unsigned i,j;
	X->m_nLength=I->m_nLength+A->m_nLength-1;
    for(i=0;i<X->m_nLength;i++)
	{
		sum=carry;
		carry=0;
		for(j=0;j<A->m_nLength;j++)
		{
            if(((i-j)>=0)&&((i-j)<I->m_nLength))
			{
				mul=I->m_ulValue[i-j];
				mul*=A->m_ulValue[j];
			    carry+=mul>>32;
				mul=mul&0xffffffff;
				sum+=mul;
			}
        }
		carry+=sum>>32;
		X->m_ulValue[i]=(unsigned int)sum;
	}
	if(carry){X->m_nLength++;X->m_ulValue[X->m_nLength-1]=(unsigned int)carry;}
    return X;
}

SBigInt* MulD(SBigInt *I, unsigned int A,SBigInt *O)
{
	unsigned i;

    SBigInt *X=O;
	SBigIntInit(X);
    Mov(X,I);
    unsigned long long mul;
    unsigned int carry=0;
    
    for(i=0;i<I->m_nLength;i++)
    {
        mul=I->m_ulValue[i];
        mul=mul*A+carry;
        X->m_ulValue[i]=(unsigned int)mul;
        carry=(unsigned int)(mul>>32);
    }
    if(carry){X->m_nLength++;X->m_ulValue[X->m_nLength-1]=carry;}
    return X;
}

/****************************************************************************************
大数相除
调用形式：N.Div(A)
返回值：N/A
****************************************************************************************/
SBigInt* Div(SBigInt *I, SBigInt *A,SBigInt *O)
{
    if(A->m_nLength==1)return DivD(I,A->m_ulValue[0],O);
    SBigInt *X=O;
	SBigIntInit(X);
	SBigInt Y;
	SBigIntInit(&Y);
	Mov(&Y,I);

	SBigInt Z;
	SBigIntInit(&Z);
	SBigInt T,P;
    unsigned i,len;
    unsigned long long num,div;
    while(Cmp(&Y,A)>=0)
    {       
		div=Y.m_ulValue[Y.m_nLength-1];
		num=A->m_ulValue[A->m_nLength-1];
		len=Y.m_nLength-A->m_nLength;
		if((div==num)&&(len==0)){Mov(X,AddD(X,1,&T));break;}
		if((div<=num)&&len){len--;div=(div<<32)+Y.m_ulValue[Y.m_nLength-2];}
		div=div/(num+1);
		MovD(&Z,div);
		if(len)
		{
			Z.m_nLength+=len;
			for(i=Z.m_nLength-1;i>=len;i--)Z.m_ulValue[i]=Z.m_ulValue[i-len];
			for(i=0;i<len;i++)Z.m_ulValue[i]=0;
		}
		Mov(X,Add(X,&Z,&T));
        Mov(&Y,Sub(&Y,Mul(A,&Z,&T),&P));
    }
    return X;
}

SBigInt* DivD(SBigInt *I, unsigned int A,SBigInt *O)
{
	int i;
    SBigInt *X=O;
	SBigIntInit(X);
    Mov(X,I);
    if(X->m_nLength==1){X->m_ulValue[0]=X->m_ulValue[0]/A;return X;}
    unsigned long long div,mul;
    unsigned int carry=0;
    for(i=X->m_nLength-1;i>=0;i--)
    {
        div=carry;
        div=(div<<32)+X->m_ulValue[i];
        X->m_ulValue[i]=(unsigned int)(div/A);
        mul=(div/A)*A;
        carry=(unsigned int)(div-mul);
    }
    if(X->m_ulValue[X->m_nLength-1]==0)X->m_nLength--;
    return X;
}

/****************************************************************************************
大数求模
调用形式：N.Mod(A)
返回值：N%A
****************************************************************************************/
SBigInt* Mod(SBigInt *I, SBigInt *A,SBigInt *O)
{
    SBigInt *X=O;
	SBigIntInit(X);
    Mov(X,I);
	SBigInt Y;
	SBigIntInit(&Y);
	SBigInt T;
	unsigned long long div,num;
    unsigned int carry=0;
	unsigned i,len;
    while(Cmp(X,A)>=0)
    {
		div=X->m_ulValue[X->m_nLength-1];
		num=A->m_ulValue[A->m_nLength-1];
		len=X->m_nLength-A->m_nLength;
		if((div==num)&&(len==0)){Mov(X,Sub(X,A,&T));break;}
		if((div<=num)&&len){len--;div=(div<<32)+X->m_ulValue[X->m_nLength-2];}
		div=div/(num+1);
		MovD(&Y,div);
		Mov(&Y,Mul(A,&Y,&T));
		if(len)
		{
			Y.m_nLength+=len;
			for(i=Y.m_nLength-1;i>=len;i--)Y.m_ulValue[i]=Y.m_ulValue[i-len];
			for(i=0;i<len;i++)Y.m_ulValue[i]=0;
		}
        Mov(X,Sub(X,&Y,&T));
    }
    return X;
}

unsigned int ModD(SBigInt *I, unsigned int A,SBigInt *O)
{
	int i;
    if(I->m_nLength==1)return(I->m_ulValue[0]%A);
    unsigned long long div;
    unsigned int carry=0;
    for(i=I->m_nLength-1;i>=0;i--)
    {
        div=I->m_ulValue[i];
		div+=carry*0x100000000;
        carry=(unsigned int)(div%A);
    }
    return carry;
}

/****************************************************************************************
从字符串按10进制或16进制格式输入到大数
调用格式：N.Get(str,sys)
返回值：N被赋值为相应大数
sys暂时只能为10或16
****************************************************************************************/
void Get(SBigInt *I,char c[512])
{
	unsigned int system = HEX;
    int k;
    int i,j;
    MovD(I,0);
	SBigInt T;
	for( j=0;c[j]!='\0';j++);
    for( i=0;i<j;i++)
    {
       Mov(I,MulD(I,system,&T));
       if((c[i]>='0')&&(c[i]<='9'))k=c[i]-48;
       else if((c[i]>='A')&&(c[i]<='F'))k=c[i]-55;
       else if((c[i]>='a')&&(c[i]<='f'))k=c[i]-87;
       else k=0;
       Mov(I,AddD(I,k,&T));
    }
}

/****************************************************************************************
将大数按10进制或16进制格式输出为字符串
调用格式：N.Put(sys)
返回值：无，参数str被赋值为N的sys进制字符串
sys暂时只能为10或16
****************************************************************************************/
void Put(SBigInt *I, char *Out)
{
	unsigned int system = HEX;

//    if((m_nLength==1)&&(m_ulValue[0]==0)){str="0";return;}
    char t[100]="0123456789abcdef";
    int a,i=0,j=0;
    char ch,c[512],d[512];
	memset(c,0,512);
	memset(d,0,512);
    SBigInt X;
	SBigIntInit(&X);
    Mov(&X,I);
	SBigInt T;
    while(X.m_ulValue[X.m_nLength-1]>0)
    {
        a=ModD(&X,system,&T);
        ch=t[a];
        c[i] = ch;
		i++;
        Mov(&X,DivD(&X,system,&T));
    }
	for(j=0;j<i;j++)d[j]=c[i-j-1];
	strcpy(Out,d);
}

/****************************************************************************************
求不定方程ax-by=1的最小整数解
调用方式：N.Euc(A)
返回值：X,满足：NX mod A=1
****************************************************************************************/
SBigInt* Euc(SBigInt* K,SBigInt* A,SBigInt *O)
{
	SBigInt M;
	SBigIntInit(&M);
	Mov(&M,A);
	SBigInt E;
	SBigIntInit(&E);
    Mov(&E,K);
	SBigInt *X=O;
	SBigIntInit(X);
	MovD(X,0);
	SBigInt Y;
	SBigIntInit(&Y);
	MovD(&Y,1);
	SBigInt I;
	SBigIntInit(&I);
	SBigInt J;
	SBigIntInit(&J);
	SBigInt T;
    int x,y;
	x=y=1;
	while((E.m_nLength!=1)||(E.m_ulValue[0]!=0))
	{
		Mov(&I,Div(&M,&E,&T));
		Mov(&J,Mod(&M,&E,&T));
		Mov(&M,&E);
		Mov(&E,&J);
		Mov(&J,&Y);
		Mov(&Y,Mul(&Y,&I,&T));
		if(x==y)
		{
		    if(Cmp(X,&Y)>=0)Mov(&Y,Sub(X,&Y,&T));
			else{Mov(&Y,Sub(&Y,X,&T));y=0;}
		}
		else{Mov(&Y,Add(X,&Y,&T));x=1-x;y=1-y;}
		Mov(X,&J);
	}
	if(x==0)Mov(X,Sub(A,X,&T));
	return X;
}

/****************************************************************************************
求乘方的模
调用方式：N.RsaTrans(A,B)
返回值：X=N^A MOD B
****************************************************************************************/
SBigInt* RsaTrans(SBigInt* I,const SBigInt* A, SBigInt* B,SBigInt *O)
{
	SBigInt *X=O;
	SBigIntInit(X);
	Mov(X,I);
	SBigInt Y;
	SBigIntInit(&Y);
	SBigInt T,P;
	int i,j,k;
	unsigned n;
	unsigned int num;
	k=A->m_nLength*32-32;
	num=A->m_ulValue[A->m_nLength-1];
	while(num){num=num>>1;k++;}
	for(i=k-2;i>=0;i--)
	{
		Mov(&Y,MulD(X,X->m_ulValue[X->m_nLength-1],&T));
		Mov(&Y,Mod(&Y,B,&T));
        for(n=1;n<X->m_nLength;n++)
		{          
			for(j=Y.m_nLength;j>0;j--)Y.m_ulValue[j]=Y.m_ulValue[j-1];
			Y.m_ulValue[0]=0;
			Y.m_nLength++;
			Mov(&Y,Add(&Y,MulD(X,X->m_ulValue[X->m_nLength-n-1],&T),&P));
			Mov(&Y,Mod(&Y,B,&T));
		}
		Mov(X,&Y);
		if((A->m_ulValue[i>>5]>>(i&31))&1)
		{
		    Mov(&Y,MulD(I,X->m_ulValue[X->m_nLength-1],&T));
		    Mov(&Y,Mod(&Y,B,&T));
            for(n=1;n<X->m_nLength;n++)
			{          
			    for(j=Y.m_nLength;j>0;j--)Y.m_ulValue[j]=Y.m_ulValue[j-1];
			    Y.m_ulValue[0]=0;
			    Y.m_nLength++;
			    Mov(&Y,Add(&Y,MulD(I,X->m_ulValue[X->m_nLength-n-1],&T),&P));
			    Mov(&Y,Mod(&Y,B,&T));
			}
		    Mov(X,&Y);
		}
	}
    return X;
}

/****************************************************************************************
拉宾米勒算法测试素数
调用方式：N.Rab()
返回值：若N为素数，返回1，否则返回0
****************************************************************************************/
int Rab(SBigInt *M)
{
	SBigInt T;
    unsigned i,j,pass;
    for(i=0;i<550;i++){if(ModD(M,PrimeTable[i],&T)==0)return 0;}
	SBigInt S;
	SBigIntInit(&S);
	SBigInt A;
	SBigIntInit(&A);
	SBigInt I;
	SBigIntInit(&I);
	Mov(&I,M);
	SBigInt K;
	SBigIntInit(&K);
	K.m_ulValue[0]--;
    for(i=0;i<5;i++)
    {
        pass=0;
        MovD(&A,rand()*rand());
		Mov(&S,&K);
        while((S.m_ulValue[0]&1)==0)
		{
            for(j=0;j<S.m_nLength;j++)
			{
			    S.m_ulValue[j]=S.m_ulValue[j]>>1;
			    if(S.m_ulValue[j+1]&1)S.m_ulValue[j]=S.m_ulValue[j]|0x80000000;
			}
		    if(S.m_ulValue[S.m_nLength-1]==0)S.m_nLength--;
			Mov(&I,RsaTrans(&A,&S,M,&T));
			if(Cmp(&I,&K)==0){pass=1;break;}
		}
		if((I.m_nLength==1)&&(I.m_ulValue[0]==1))pass=1;
		if(pass==0)return 0;
	}
    return 1;
}

/****************************************************************************************
产生随机素数
调用方法：N.GetPrime(bits)
返回值：N被赋值为一个bits位（0x100000000进制长度）的素数
****************************************************************************************/
void GetPrime(SBigInt* M,int bits)
{
    unsigned i;
	SBigInt T;
    M->m_nLength=bits;
begin:
	for(i=0;i<M->m_nLength;i++)M->m_ulValue[i]=rand()*0x10000+rand();
	M->m_ulValue[0]=M->m_ulValue[0]|1;
	for(i=M->m_nLength-1;i>0;i--)
	{
		M->m_ulValue[i]=M->m_ulValue[i]<<1;
		if(M->m_ulValue[i-1]&0x80000000)M->m_ulValue[i]++;
	}
	M->m_ulValue[0]=M->m_ulValue[0]<<1;
	M->m_ulValue[0]++;
    for(i=0;i<550;i++){if(ModD(M,PrimeTable[i],&T)==0)goto begin;}

	SBigInt S;
	SBigIntInit(&S);
	SBigInt A;
	SBigIntInit(&A);
	SBigInt I;
	SBigIntInit(&I);
	SBigInt K;
	SBigIntInit(&K);
    Mov(&K,M);
	K.m_ulValue[0]--;
    for(i=0;i<5;i++)
	{
        MovD(&A,123456);
	    Mov(&S,DivD(&K,2,&T));
	    Mov(&I,RsaTrans(&A,&S,M,&T));
	    if(((I.m_nLength!=1)||(I.m_ulValue[0]!=1))&&(Cmp(&I,&K)!=0))goto begin;
	}
}

