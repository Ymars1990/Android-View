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
		// 检查存储状态，首先检查是否有SD，如无，再检查手机内存
		if (ExistSDCard(SDFreeSize)) {
			//SD卡存储空间符合设置的值
			return true;
		} else {
			//SD卡存储空间不符合设置的值，检查手机内存是否够
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
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// return freeBlocks * blockSize; //单位Byte
		// return (freeBlocks * blockSize)/1024; //单位KB
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}
	
	private long getExternalStorageFreeSize() {
		// 取得手机内存文件路径
		File path = Environment.getRootDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回手机内存空闲大小
		// return freeBlocks * blockSize; //单位Byte
		// return (freeBlocks * blockSize)/1024; //单位KB
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

}
