package com.tangzy.tzymvp.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * Created by tangzy on 2016/8/11.
 */
public class FileUtils {

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(delFile);
            else
                return deleteDirectory(delFile);
        }
    }

    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 确定指定文件是否存在，如果不存在，则创建空文件
     *
     * @param fileName
     */
    public static void makesureFileExist(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        // file path
        int index = fileName.lastIndexOf("/");
        File file = null;
        if (index != -1) {
            String filePath = fileName.substring(0, index);
            file = new File(filePath);
            if (!file.exists()) {
                boolean ret = file.mkdirs();
            }
        }
        file = new File(fileName);
        if (!file.exists()) {// 确保文件存在
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }
    /**
     * 将输入的byte 节流写入文件
     *
     * @param by
     * @param path
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void writeToFile(byte[] by, String path) throws IOException {
        makesureFileExist(path);
        File f = new File(path);
        if (f.exists()) {
            f.delete();
            f.createNewFile();
        }
        FileOutputStream fou = new FileOutputStream(f);
        fou.write(by);
    }

    public static byte[] readFile(String path){
        byte[] buffer = null;
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            int len = -1;
            FileInputStream fin = new FileInputStream(f);
            byte[] bytes = new byte[1024];
            buffer = new byte[(int) f.length()];
            int i=0;
            while ((len = fin.read(bytes)) != -1) {
                System.arraycopy(bytes, 0, buffer, 1024*i, len);
                i++;

            }
        } catch (Exception e) {
            return null;
        }
        return buffer;
    }


    /**
     * 使用RandomAccessFile实现文件的追加，其中：fileName表示文件名；content表示要追加的内容
     *
     * @param content
     *            追加的内容
     * @param fileName
     *            文件名
     * @throws Exception
     *             异常
     */
    public static void appendFileMethod(byte[] content, String fileName)
            throws Exception {
        try {
            // 按读写方式创建一个随机访问文件流
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            long fileLength = raf.length();// 获取文件的长度即字节数
            // 将写文件指针移到文件尾。
            raf.seek(fileLength);
            // 按字节的形式将内容写到随机访问文件流中
            // raf.writeBytes(content);
            raf.write(content);
            // 关闭流
            raf.close();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 获取文件MD5 值
     *
     * @param filePath
     *            文件路径
     * @return
     */
    @SuppressWarnings("resource")
    public static String MD5File(String filePath) {
        byte[] buf = new byte[4096]; // 这个byte[]的长度可以是任意的。
        MessageDigest md;
        boolean fileIsNull = true;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            int len = 0;
            md = MessageDigest.getInstance("MD5");
            len = fis.read(buf);
            if (len > 0) {
                fileIsNull = false;
                while (len > 0) {
                    md.update(buf, 0, len);
                    len = fis.read(buf);
                }
            }
        } catch (Exception e) {
            return null;
        }

        if (fileIsNull) {
            return null;
        } else {
            return toHexString(md.digest());
        }
    }

    public static String toHexString(byte[] bytes) {
        final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 随机读取文件
     *
     * @param src
     *            文件对象
     * @param start
     *            起始位置
     * @param length
     *            读取长度
     * @return base64编码的字符串
     * @throws Exception
     */
    public static byte[] readFlie(File src, int start, int length)
            throws Exception {
        byte[] buf = null;
        try {
            RandomAccessFile in = new RandomAccessFile(src, "r"); // 读取文件

            int buf_size = length;
            buf = new byte[buf_size];

            in.seek(start); // 指向源文件的开始位置
            in.read(buf, 0, length); // 把文件内容读入缓冲区
            in.close();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return buf;
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath
     *            文件路径
     * @param sizeType
     *            获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     * @throws Exception
     */
    public static double getFileOrFilesSize(String filePath, int sizeType)
            throws Exception {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    @SuppressWarnings("unused")
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 将文件转换为 byte 数组
     *
     * @param filePath
     *            文件路径
     * @return byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {


        }
        return buffer;
    }

//	@SuppressWarnings("resource")
//	public static String readMapMemeryBuffer(String file) {
//		String errmes;
//		try {
//
//			File source = new File(file);
//			FileChannel in = null;
//			in = new FileInputStream(source).getChannel();
//			long size = in.size();
//			MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_WRITE, 0,
//					size);
//			byte[] array = buf.array();
//			String encryptByte = SHA256.EncryptByte(array, "MD5");
//			in.close();
//			source.delete();// 文件复制完成后，删除源文件
//			return encryptByte;
//
//		} catch (Exception e) {
//			errmes = "readMapMemeryBuffer";
//		}
//		return errmes;
//
//	}

    public static void deleteFile(String path){
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

    }






}
