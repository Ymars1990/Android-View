package com.mars.marsview.systemmanager;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

public class CreateFolderOnSDCardOrExternalStorage {

	public boolean creatFolder(String foldFileName, Context mContext,
			long SDFreeSize) {
		if(checkSDStorageState(SDFreeSize)){
			
		}else{
			
		}
		return true;
	}

	private boolean checkSDStorageState(long SDFreeSize) {
		// ���洢״̬�����ȼ���Ƿ���SD�����ޣ��ټ���ֻ��ڴ�
		if (ExistSDCard(SDFreeSize)) {
			//SD���洢�ռ�������õ�ֵ
			return true;
		} else {
			//SD���洢�ռ䲻�������õ�ֵ������ֻ��ڴ��Ƿ�
			if(getExternalStorageFreeSize()>=SDFreeSize){
				return true;
			}else{
				return false;
			}
		}
	}

	private boolean ExistSDCard(long SDFreeSize) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)
				&& getSDFreeSize() >= SDFreeSize) {
			return true;
		} else
			return false;
	}

	private long getSDFreeSize() {
		// ȡ��SD���ļ�·��
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// ��ȡ�������ݿ�Ĵ�С(Byte)
		long blockSize = sf.getBlockSize();
		// ���е����ݿ������
		long freeBlocks = sf.getAvailableBlocks();
		// ����SD�����д�С
		// return freeBlocks * blockSize; //��λByte
		// return (freeBlocks * blockSize)/1024; //��λKB
		return (freeBlocks * blockSize) / 1024 / 1024; // ��λMB
	}
	
	private long getExternalStorageFreeSize() {
		// ȡ���ֻ��ڴ��ļ�·��
		File path = Environment.getRootDirectory();
		StatFs sf = new StatFs(path.getPath());
		// ��ȡ�������ݿ�Ĵ�С(Byte)
		long blockSize = sf.getBlockSize();
		// ���е����ݿ������
		long freeBlocks = sf.getAvailableBlocks();
		// �����ֻ��ڴ���д�С
		// return freeBlocks * blockSize; //��λByte
		// return (freeBlocks * blockSize)/1024; //��λKB
		return (freeBlocks * blockSize) / 1024 / 1024; // ��λMB
	}

}
