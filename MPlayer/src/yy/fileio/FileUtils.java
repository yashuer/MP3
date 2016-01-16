package yy.fileio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import yy.songmodel.SongInfo;
import android.os.Environment;

public class FileUtils {
	private String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}
	public FileUtils() {
		//�õ���ǰ�ⲿ�洢�豸��Ŀ¼
		// /SDCARD
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}
	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @throws IOException
	 */
	public File creatSDFile(String fileName,String dir) throws IOException {
		File file = new File(SDPATH + dir + File.separator + fileName);
		System.out.println(SDPATH + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 */
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName + File.separator);
		dir.mkdir();
		return dir;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 */
	public boolean isFileExist(String fileName,String path){
		File file = new File(SDPATH + path +File.separator + fileName);
		return file.exists();
	}
	
	/**
	 * ��һ��InputStream���������д�뵽SD����
	 */
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
			creatSDDir(path);
			file = creatSDFile(fileName,path);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			int temp;
			while((temp = input.read(buffer)) != -1){
				output.write(buffer,0,temp);
			}
			output.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public List<SongInfo> getSongFiles(String path){
		List<SongInfo> infos = new ArrayList<SongInfo>(); 
		File file = new File(SDPATH + File.separator + path);
		File [] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].getName().endsWith(".mp3")){
			SongInfo info = new SongInfo();
			info.setName(files[i].getName());
			System.out.println(info.getLrcName());
			info.setSize(files[i].length() + "");
			infos.add(info);
			}
		}
		return infos;
		
	}

}