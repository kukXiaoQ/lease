package com.situ.ws.util;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.bean.entries.Vehicles;
import com.situ.ws.config.LeaseWebConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件处理
 */
public class FileUtils {
    private static final String UP_LOAD_DIR = LeaseWebConfigurer.PATH;
    public static final Page page = new Page();
    public static final String sheet = "第一页";

    static {
        page.setPage(0);
        page.setSize(5);
    }

     static {
         File file = new File(UP_LOAD_DIR);
         if (!file.exists()){
             // 创建目录
             if (!file.mkdirs()){
                 System.out.println("创建失败.........");
                 throw new RuntimeException("创建"+UP_LOAD_DIR+"目录失败.........");
             }
         }else {
             System.out.println("目录已存在..............");
         }
     }

     public static String createFileName(String type){
         return LeaseWebConfigurer.FILE + type + "\\" +UUID.randomUUID().toString() + ".xlsx";
     }


     public static String uploadFile(MultipartFile file){

         // 生成一个新的文件名
        String fileName = file.getOriginalFilename();

         String lastName = null;
         if (fileName != null) {
             lastName = fileName.substring(fileName.lastIndexOf("."));
         }

         String newFileName = UUID.randomUUID() + lastName;
         // 将file文件保存到自定义路径下
         try {
             file.transferTo(new File(UP_LOAD_DIR + newFileName));
         } catch (IOException e) {
             // 自定义异常
             throw new RuntimeException(e);
         }

         // 返回文件地址，已经文件名
         return newFileName;
     }


    /**
     * 设置图片新地址
     * @param obj
     * @return
     */
    public static Object getLastName(Object obj){

        if (obj == null)
            return null;

        if (obj instanceof Users){
            Users users = (Users)obj;
            String url = users.getUrl();
            if (url == null || url.isEmpty())
                // 设置默认值
                users.setUrl("/img/43c19519ce460dcedd37c798746bd8db_29487_800_872.jpg");
            else {
                if (url.startsWith("/img"))
                    users.setUrl(null);
                else {
                    System.out.println("开始截取....................");
                    url = url.substring(3);
                    users.setUrl(url);
                }
            }
        } else if (obj instanceof Vehicles) {
            Vehicles vehicles = (Vehicles)obj;
            String url = vehicles.getUrl();
            if (url == null || url.isEmpty())
                // 如果不存在，或为空串设置默认值
                vehicles.setUrl("/img/a8d7f53f-4a5d-497f-aa40-b3dd3e549094.jpg");
            else{
                // 如果以/img开头，不截取
                if (url.startsWith("/img"))
                    vehicles.setUrl(null);
                else {
                    System.out.println("开始截取....................");
                    url = url.substring(3);
                    vehicles.setUrl(url);
                }
            }
        } else if (obj instanceof Brand) {
            Brand brand = (Brand)obj;
            String url = brand.getUrl();
            if (url == null || url.isEmpty())
                // 如果不存在，或为空串设置默认值
                brand.setUrl("/img/a8d7f53f-4a5d-497f-aa40-b3dd3e549094.jpg");
            else{
                // 如果以/img开头，不截取
                if (url.startsWith("/img"))
                    brand.setUrl(null);
                else {
                    System.out.println("开始截取....................");
                    url = url.substring(3);
                    brand.setUrl(url);
                }
            }
        }
        return obj;
    }
}
