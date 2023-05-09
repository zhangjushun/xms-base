package com.xms.common.utils.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    /**
     * 按指定高度 等比例缩放图片
     * 
     * @param imageFile
     * @param newPath
     * @param newWidth 新图的宽度
     * @throws IOException
     */
    public static boolean zoomImageScale(File imageFile, String newPath, int newWidth) throws IOException {
         if(!imageFile.canRead())
             return false;
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        if (null == bufferedImage) 
            return false;
        
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        double scale = (double)originalWidth / (double)newWidth;    // 缩放的比例
        
        int newHeight =  (int)(originalHeight / scale);

        return zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
    }
    
    private static boolean zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
            throws IOException{
        
         String suffix = imageFile.getName().substring(imageFile.getName().lastIndexOf(".")+1);
        
         // 处理 png 背景变黑的问题
        if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
            Graphics2D g2d = to.createGraphics(); 
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
            g2d.dispose(); 
            
            g2d = to.createGraphics(); 
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING); 
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose(); 
            
            return ImageIO.write(to, suffix, new File(newPath));
        }else{
            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            return ImageIO.write(newImage, suffix, new File(newPath));
        }
    }
    
    public static void main(String[] args) {
    	File f = new File("E:/sucai/Lighthouse.jpg");
		try {
			System.err.println(ImageUtil.zoomImageScale(f, "E:/sucai/thumb.jpg", 300));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
