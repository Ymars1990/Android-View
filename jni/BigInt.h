/*****************************************************************
���������ͷ�ļ���BigInt.h
���ߣ�afanty@vip.sina.com
�汾��1.2 (2003.5.13)
˵����������MFC��1024λRSA����
*****************************************************************/

//��������1120λ�������ƣ����м���
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
��������������
Mov����ֵ���㣬�ɸ�ֵΪ��������ͨ������������Ϊ�������=��
Cmp���Ƚ����㣬������Ϊ�������==������!=������>=������<=����
Add���ӣ��������������������ͨ�����ĺͣ�������Ϊ�������+��
Sub�������������������������ͨ�����Ĳ������Ϊ�������-��
Mul���ˣ��������������������ͨ�����Ļ���������Ϊ�������*��
Div�������������������������ͨ�������̣�������Ϊ�������/��
Mod��ģ���������������������ͨ������ģ��������Ϊ�������%��
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
�������
Get�����ַ�����10���ƻ�16���Ƹ�ʽ���뵽����
Put����������10���ƻ�16���Ƹ�ʽ������ַ���
*****************************************************************/
    void Get(SBigInt *I,char c[512]);
    void Put(SBigInt *I, char *Out);

/*****************************************************************
RSA�������
Rab�����������㷨������������
Euc��ŷ������㷨���ͬ�෽��
RsaTrans������ƽ���㷨������ģ����
GetPrime������ָ�����ȵ����������
*****************************************************************/
    int Rab(SBigInt *M);
    SBigInt* Euc(SBigInt* K,SBigInt* A,SBigInt *O);
    SBigInt* RsaTrans(SBigInt* I,const SBigInt* A, SBigInt* B,SBigInt *O);
    void GetPrime(SBigInt* M,int bits);
#endif

