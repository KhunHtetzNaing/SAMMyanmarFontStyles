package com.htetznaing.samfontstyles;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

/**
 * Created by HtetzNaing on 1/6/2018.
 */

public class FuckYou {
    public ArrayList<String> getFontName(){
        ArrayList<String> lol = new ArrayList<>();
        String l = "dancing.apk\n" +
                "darcy.apk\n" +
                "eainmat.apk\n" +
                "ghost.apk\n" +
                "heart.apk\n" +
                "htinshu.apk\n" +
                "izawgyi.apk\n" +
                "jojar.apk\n" +
                "kason.apk\n" +
                "khninsi.apk\n" +
                "love.apk\n" +
                "matrix.apk\n" +
                "nattaw.apk\n" +
                "nayon.apk\n" +
                "ngaye.apk\n" +
                "notosans.apk\n" +
                "notosansmix.apk\n" +
                "ooredoo.apk\n" +
                "padauk.apk\n" +
                "paoh.apk\n" +
                "pyatho.apk\n" +
                "sagar.apk\n" +
                "szg.apk\n" +
                "tabaung.apk\n" +
                "tabodwe.apk\n" +
                "tabodwemx.apk\n" +
                "tagu.apk\n" +
                "tdg.apk\n" +
                "tran.apk\n" +
                "tsm.apk\n" +
                "ttl.apk\n" +
                "ubuntu.apk\n" +
                "warso.apk\n" +
                "wg.apk\n" +
                "yoeyar.apk\n" +
                "yuzana.apk\n" +
                "z2.apk\n" +
                "zo.apk\n" +
                "zy.apk";

        String [] l2 = l.split("\n");
        for (int i=0;i<l2.length;i++){
            lol.add(l2[i]);
        }

        return lol;
    }

    public ArrayList<String> getName(){
        ArrayList<String> lol = new ArrayList<>();
        String l = "Zawgyi Dancing\n" +
                "Darcy\n" +
                "EainMat\n" +
                "Myanmar Ghost\n" +
                "Myanmar Heart\n" +
                "Htin Shu\n" +
                "iPhone Zawgyi\n" +
                "Jojar\n" +
                "Kason\n" +
                "Khin Hninsi\n" +
                "Myanmar Love\n" +
                "Myanmar Matrix\n" +
                "Nattaw\n" +
                "Nayon\n" +
                "Ngaye\n" +
                "NotoSans Zawgyi\n" +
                "NotoSans Zawgyi Mix\n" +
                "Ooredoo Burmese\n" +
                "Padauk Zawgyi\n" +
                "PaOh Zawgyi\n" +
                "Pyatho\n" +
                "Zawgyi Sagar\n" +
                "Smart Zawgyi\n" +
                "Tabaung\n" +
                "Tabodwe\n" +
                "Tabodwe Mix\n" +
                "Tagu\n" +
                "Thadingyut\n" +
                "Transformet\n" +
                "Tansaungmone\n" +
                "Tawthalin\n" +
                "Ubuntu Zawgyi\n" +
                "Warso\n" +
                "Wargaung\n" +
                "YoeYar Zawgyi\n" +
                "Yuzana\n" +
                "Zawgyi 2\n" +
                "Zawgyi One\n" +
                "Zawgyi Yang";

        String [] l2 = l.split("\n");
        for (int i=0;i<l2.length;i++){
            lol.add(l2[i]);
        }

        return lol;
    }

    public void unZip(String zip,String pass,String output){
        File n = new File(output);
        if (!n.exists()){
            n.mkdir();
        }

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zip);
            if(zipFile.isEncrypted()){
                zipFile.setPassword(pass);
            }
            zipFile.extractAll(output);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public boolean assets2SD(Context context, String inputFileName, String OutputDir, String OutputFileName) {
        boolean lol = false;
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            try {
                in = assetManager.open(inputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out = new FileOutputStream(OutputDir + OutputFileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(OutputDir+OutputFileName);
        if (file.exists()!=false){
            lol=true;
        }else{
            lol=false;
        }
        return lol;
    }

    public boolean htetzUnzip(String zipFile, String targetPath)
    {
        if ((zipFile == null) || (zipFile.equals(""))) {
            System.out.println("Invalid source file");
            return false;
        }
        System.out.println("Zip file extracted!");
        return unzip(zipFile, targetPath);
    }

    private static boolean unzip(String zipFile, String targetPath){
        try
        {
            File fSourceZip = new File(zipFile);
            File temp = new File(targetPath);
            temp.mkdir();
            System.out.println(targetPath + " created");
            java.util.zip.ZipFile zFile = new java.util.zip.ZipFile(fSourceZip);
            Enumeration e = zFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)e.nextElement();
                File destinationFilePath = new File(targetPath, entry.getName());
                destinationFilePath.getParentFile().mkdirs();
                if (!entry.isDirectory())
                {
                    System.out.println("Extracting " + destinationFilePath);
                    BufferedInputStream bis = new BufferedInputStream(zFile.getInputStream(entry));

                    byte[] buffer = new byte['Ѐ'];
                    FileOutputStream fos = new FileOutputStream(destinationFilePath);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
                    int b;
                    while ((b = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, b);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                }
            }
        }
        catch (IOException ioe) {
            System.out.println("IOError :" + ioe);
            return false;
        }
        return true;
    }

    public boolean copy(String fromPath, String toPath) {
        File file = getFile(fromPath);
        if (!file.isFile()) {
            return false;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(file);
            outStream = new FileOutputStream(new File(toPath));
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            Log.e("UnZip", "Failed copy", e);
            return false;
        } finally {
            closeSilently(inStream);
            closeSilently(outStream);
        }
        return true;
    }

    public File getFile(String path) {
        return new File(path);
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public boolean deleteFile(String path){
        boolean b = false;
        File file = new File(path);
        if (file.exists()) {
            b = file.delete();
        }else{
            b = true;
        }

        return b;
    }

    public boolean deleteDirectory(String path) {
        return deleteDirectoryImpl(path);
    }

    private boolean deleteDirectoryImpl(String path) {
        File directory = new File(path);

        // If the directory exists then delete
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files == null) {
                return true;
            }
            // Run on all sub files and folders and delete them
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectoryImpl(files[i].getAbsolutePath());
                } else {
                    files[i].delete();
                }
            }
        }
        return directory.delete();
    }

    public void writeTextFile(String path, String texts){
        try{
            FileWriter writer = new FileWriter(path);
            writer.append(texts);
            writer.flush();
            writer.close();

        }catch (Exception e){
        }
    }

    public String readTextFile(String path) {
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
        }

        return text.toString();
    }
}
