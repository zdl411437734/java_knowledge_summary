package com.zdltech.javaexercise.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理
 */
public class FileManager {
    public static void main(String[] args) {
//        FileBean rootFileBean = files("/Users/jason/Documents/opt/java_workspace/java_knowledge_summary");//
//        System.out.println(rootFileBean);
        List<FileBean> list = new ArrayList<>();
//        filesLog("/Users/jason/Documents/opt/java_workspace/java_knowledge_summary",list);
        filesLog("/",list);
        System.out.println(list.size());
    }

    //创建方法
    public static FileBean files(String path){
        File file = new File(path);
        FileBean fileBean = new FileBean();
        fileBean.name =  file.getName();
        fileBean.path = file.getAbsolutePath();
        if (file.isDirectory()){
            File[] files = file.listFiles();
            List<FileBean> fileBeanList = new ArrayList<>();
            for (File item :files) {
                FileBean fileBean1 = new FileBean();
                fileBean1.name = item.getName();
                fileBean1.path = item.getAbsolutePath();
                if (item.isDirectory()){
                    FileBean childRoot = files(item.getAbsolutePath());
                    fileBeanList.add(childRoot);
                }else{
                    fileBeanList.add(fileBean1);
                }

            }
            fileBean.child = fileBeanList;//设置子文件夹
        }
        return fileBean;
    }

    public static class FileBean{
        public String name;
        public String path;
        public List<FileBean> child;

        @Override
        public String toString() {
            return "name="+name+",child="+(child!=null?child.toString():"") ;
        }
    }

    //创建方法
    public static void filesLog(String path,List<FileBean> logs){
        File file = new File(path);
        if (file.isDirectory()&&file.listFiles()!=null){
            System.out.println(file.getAbsolutePath());
            File[] files = file.listFiles();
            for (File item :files) {
                if (item.isDirectory()){
                   filesLog(item.getAbsolutePath(),logs);
                }else{
                    String fileName = item.getName();
                    if (fileName.endsWith("log.txt")||fileName.endsWith(".log")){
                        FileBean fileBean = new FileBean();
                        fileBean.name =  file.getName();
                        fileBean.path = file.getAbsolutePath();
                        logs.add(fileBean);
                    }
                }
            }
        }else{
            //
            String fileName = file.getName();
            if (fileName.endsWith("log.txt")||fileName.endsWith(".log")){
                FileBean fileBean = new FileBean();
                fileBean.name =  file.getName();
                fileBean.path = file.getAbsolutePath();
                logs.add(fileBean);
            }
        }
    }

}
